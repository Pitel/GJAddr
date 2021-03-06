package cz.vutbr.fit.gja.gjaddr.importexport;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.Website;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PostalAddress;
import com.google.gdata.util.ServiceException;
import cz.vutbr.fit.gja.gjaddr.gui.StatusBar;
import cz.vutbr.fit.gja.gjaddr.importexport.exception.GoogleImportException;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.MessengersEnum;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.TypesEnum;
import cz.vutbr.fit.gja.gjaddr.util.LoggerUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import org.slf4j.LoggerFactory;

/**
 * Class for importing Google contacts.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 * @see <a href="https://code.google.com/apis/contacts">Google Contacts Data API</a>
 * @see <a href="https://code.google.com/p/gdata-java-client">Google Data Java Client Library</a>
 */
public class GoogleImport {

    /**
     * Local database.
     */
    private Database database;
    /**
     * Authentication token for Google Contacts.
     */
    private AuthToken token;
    /**
     * Google Contacts service for fetching contacts.
     */
    private ContactsService service;
    /**
     * How many contacts were imported so far.
     */
    private Integer processed = 0;

    /**
     * Constructor.
     */
    public GoogleImport() {
        this.database = Database.getInstance();
        this.token = this.database.getToken(ServicesEnum.GOOGLE);
        this.service = new ContactsService("GJAddr");
    }

    /**
     * Get group by it's name.
     *
     * @param groupName
     * @return
     */
    private Group getGroupByName(String groupName) {
        for (Group g : this.database.getAllGroups()) {
            if (g.getName().equals(groupName)) {
                return g;
            }
        }
        return null;
    }

    /**
     * Build contact from one entry.
     *
     * @param entry
     * @return
     */
    private Contact fetchContact(ContactEntry entry) {
        String firstName = null;
        String surname = null;
        String nickname = null;
        String note = null;

        // get name
        if (entry.hasName()) {
            Name name = entry.getName();
            if (name.hasGivenName()) {
                firstName = name.getGivenName().getValue();
            }
            if (name.hasFamilyName()) {
                surname = name.getFamilyName().getValue();
            }
        }

        // get emails
        List<cz.vutbr.fit.gja.gjaddr.persistancelayer.Email> emails =
                new ArrayList<cz.vutbr.fit.gja.gjaddr.persistancelayer.Email>();
        for (Email email : entry.getEmailAddresses()) {
            emails.add(new cz.vutbr.fit.gja.gjaddr.persistancelayer.Email(TypesEnum.HOME, email.getAddress()));
        }

        // get messengers
        List<Messenger> messengers = new ArrayList<Messenger>();
        for (Im im : entry.getImAddresses()) {
            MessengersEnum type = MessengersEnum.OTHER;
            if (im.getProtocol().contains("ICQ")) {
                type = MessengersEnum.ICQ;
            }
            if (im.getProtocol().contains("JABBER") || im.getProtocol().contains("GOOGLE_TALK")) {
                type = MessengersEnum.JABBER;
            }
            if (im.getProtocol().contains("SKYPE")) {
                type = MessengersEnum.SKYPE;
            }
            messengers.add(new Messenger(type, im.getAddress()));
        }

        // phone numbers
        List<PhoneNumber> phones = new ArrayList<PhoneNumber>();
        for (com.google.gdata.data.extensions.PhoneNumber phone : entry.getPhoneNumbers()) {
            phones.add(new PhoneNumber(TypesEnum.HOME, phone.getPhoneNumber()));
        }

        // links
        List<Url> urls = new ArrayList<Url>();
        for (Website website : entry.getWebsites()) {
            urls.add(new Url(TypesEnum.HOME, website.getHref()));
        }

        // addresses
        List<Address> addresses = new ArrayList<Address>();
        for (PostalAddress pa : entry.getPostalAddresses()) {
            String addr = pa.getValue().replaceAll("(\\r|\\n)", ", ");
            addresses.add(new Address(TypesEnum.HOME, addr));
        }

        // photo
        ImageIcon icon = null;
        if (entry.getContactPhotoLink() != null) {
            Link photoLink = entry.getContactPhotoLink();
            try {
                String photoUrl = photoLink.getHref();
                URL url = new URL(photoUrl + "?access_token=" + this.database.getToken(ServicesEnum.GOOGLE));
                InputStream rd = url.openConnection().getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int read;
                while (true) {
                    if ((read = rd.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    } else {
                        break;
                    }
                }
                icon = new ImageIcon(out.toByteArray());
            } catch (IOException ex) {
                // photo simply doesn't exist -- no need to log anything
            }
        }

        // nickname
        if (entry.hasNickname()) {
            nickname = entry.getNickname().getValue();
        }

        // build contact
        Contact contact = new Contact(firstName == null ? "" : firstName, surname == null ? "" : surname, nickname, note);
        contact.setEmails(emails);
        contact.setMessenger(messengers);
        contact.setPhoneNumbers(phones);
        contact.setUrls(urls);
        contact.setAdresses(addresses);
        if (icon != null) {
            contact.setPhoto(icon);
        }

        LoggerFactory.getLogger(this.getClass()).debug("Adding contact : " + firstName + " " + surname);

        return contact;
    }

    /**
     * Fetch list of contacts from Google.
     *
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws ServiceException
     */
    private List<Contact> fetchContacts() throws GoogleImportException {
        // set progress
        this.processed = 0;
        // check if token exists
        if (this.token == null) {
            throw new GoogleImportException("You need to connect to Google before importing.\n"
                    + "Please go to Preferences and setup connection to Google.");
        }

        // fetch list of contacts
        List<Contact> contacts = new ArrayList<Contact>();

        // request contact feed
        URL feedUrl;
        try {
            feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full?access_token="
                    + this.token.getToken());
        } catch (MalformedURLException ex) {
            LoggerFactory.getLogger(this.getClass()).error(LoggerUtil.getStackTrace(ex));
            throw new GoogleImportException("Import was unsuccessful due to application error. Please try again.");
        }

        boolean end = false;

        // get contacts
        while (feedUrl != null) {
            // if thread was interrupted --> finish
            if (GoogleImportThread.isThreadInterrupted()) {
                break;
            }

            LoggerFactory.getLogger(this.getClass()).info("Sending request to : " + feedUrl.toString());
            ContactFeed resultFeed;

            // try to fetch the feed
            try {
                resultFeed = this.service.getFeed(feedUrl, ContactFeed.class);
            } catch (ServiceException e) {
                LoggerFactory.getLogger(this.getClass()).error(LoggerUtil.getStackTrace(e));
                break;
            } catch (Exception e) {
                LoggerFactory.getLogger(this.getClass()).error(LoggerUtil.getStackTrace(e));
                throw new GoogleImportException("You need to connect to Google before importing.\n"
                        + "Please go to Preferences and setup connection to Google.");
            }

            StatusBar.setProgressBounds(0, resultFeed.getTotalResults());
            StatusBar.setMessage("Importing contacts...");

            // add all contacts to list
            for (ContactEntry entry : resultFeed.getEntries()) {
                // if thread was interrupted --> finish
                if (GoogleImportThread.isThreadInterrupted()) {
                    end = true;
                    break;
                }
                contacts.add(this.fetchContact(entry));
                this.processed++;
                StatusBar.setProgressValue(this.processed);
            }

            if (end) {
                break;
            }

            // get link to next page with contacts
            if (resultFeed.getNextLink() != null) {
                String nextLink = resultFeed.getNextLink().getHref();
                try {
                    feedUrl = new URL(nextLink + "&access_token=" + this.token.getToken());
                } catch (MalformedURLException ex) {
                    LoggerFactory.getLogger(this.getClass()).error(LoggerUtil.getStackTrace(ex));
                    feedUrl = null;
                }
            } else {
                feedUrl = null;
            }
        }

        return contacts;
    }

    /**
     * Import contacts from Google to selected group.
     *
     * @param group
     * @throws MalformedURLException
     * @throws IOException
     * @throws ServiceException
     */
    public int importContacts(String group) throws GoogleImportException {
        // fetch contacts from facebook
        List<Contact> contacts = this.fetchContacts();

        // save contacts in database
        if (group == null || group.isEmpty()) {
            LoggerFactory.getLogger(this.getClass()).debug("Group is empty.");
        } else {
            LoggerFactory.getLogger(this.getClass()).debug("Adding contacts to : " + group);
        }

        // first add contacts to database
        this.database.addNewContacts(contacts);

        // then add to group
        if (group != null) {
            this.database.addNewGroup(group);
            Group dbGroup = this.getGroupByName(group);
            if (dbGroup != null) {
                this.database.addContactsToGroup(dbGroup, contacts);
            }
        }

        StatusBar.setProgressFinished();

        return contacts.size();
    }
}
