package cz.vutbr.fit.gja.gjaddr.importexport;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;
import cz.vutbr.fit.gja.gjaddr.gui.StatusBar;
import cz.vutbr.fit.gja.gjaddr.importexport.exception.FacebookImportException;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.EventsEnum;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.slf4j.LoggerFactory;

/**
 * Class for fetching Facebook contacts.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 * @see <a href="http://restfb.com">RestFB</a>
 * @see <a href="https://developers.facebook.com/docs/reference/api/user">Facebook Graph API</a>
 */
public class FacebookImport {

	/**
	 * Local database.
	 */
	private Database database;

	/**
	 * Authentication token for Facebook.
	 */
	private AuthToken token;

	/**
	 * Facebook client for reading contacts.
	 */
	private FacebookClient client;

    /**
     * How many contacts were imported so far.
     */
    private Integer processed = 0;

	/**
	 * Constructor.
	 */
	public FacebookImport() {
		this.database = Database.getInstance();
		this.token = this.database.getToken(ServicesEnum.FACEBOOK);
		if (this.token != null) {
			this.client = new DefaultFacebookClient(this.token.getToken());
		} else {
			this.client = null;
		}
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
	 * Fetch contact information from Facebook and save it in Contact class.
	 * 
	 * @param id
	 * @return
	 */
	private Contact fetchContact(String id) {
		User user = this.client.fetchObject(id, User.class);

		List<Email> emails = new ArrayList<Email>();
		List<Url> urls = new ArrayList<Url>();
		List<Custom> customFields = new ArrayList<Custom>();
        List<Event> events = new ArrayList<Event>();

		Contact contact = new Contact(user.getFirstName(), user.getLastName(), user.getUsername(), user.getAbout());

		// contact emails
		if (user.getEmail() != null && !user.getEmail().isEmpty() && !user.getEmail().equalsIgnoreCase("null")) {
			emails.add(new Email(TypesEnum.HOME, user.getEmail()));
			contact.setEmails(emails);
		}

		// contact URLs
		if (user.getLink() != null && !user.getLink().isEmpty() && !user.getLink().equalsIgnoreCase("null")) {
			urls.add(new Url(TypesEnum.HOME, user.getLink()));
		}
		if (user.getWebsite() != null && !user.getWebsite().isEmpty() && !user.getWebsite().equalsIgnoreCase("null")) {
			urls.add(new Url(TypesEnum.HOME, user.getWebsite()));
		}
		if (!urls.isEmpty()) {
			contact.setUrls(urls);
		}

		// set birthday
		if (user.getBirthdayAsDate() != null) {
            events.add(new Event(EventsEnum.BIRTHDAY, user.getBirthdayAsDate()));
            contact.setDates(events);
		}
        
        // download photo
        ImageIcon icon = null;
        try {
            URL iconUrl = new URL("http://graph.facebook.com/" + id + "/picture");
            InputStream rd = iconUrl.openConnection().getInputStream();
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
        } catch (MalformedURLException ex) {
            LoggerFactory.getLogger(this.getClass()).error(LoggerUtil.getStackTrace(ex));
        } catch (IOException ex) {
            LoggerFactory.getLogger(this.getClass()).error(LoggerUtil.getStackTrace(ex));
        }
        
        // set photo
        if (icon != null) {
            contact.setPhoto(icon);
        }

		// custom fields
        if (user.getHometownName() != null && !user.getHometownName().isEmpty()) {
            customFields.add(new Custom("hometown", user.getHometownName()));
        }
		contact.setCustoms(customFields);
        
        // address
        List<Address> addresses = new ArrayList<Address>();
        if (user.getLocation() != null && user.getLocation().getName() != null) {
            addresses.add(new Address(TypesEnum.HOME, user.getLocation().getName()));
            contact.setAdresses(addresses);
        }

		return contact;
	}

	/**
	 * Fetch user contacts.
	 * 
	 * @return
	 */
	private List<Contact> fetchContacts() throws FacebookImportException {
		if (this.client == null) {
			throw new FacebookImportException("You need to connect to Facebook before importing.\n"
					+ "Please go to Preferences and setup connection to Facebook.");
		}

		// fetch user friends
        Connection<User> userFriends;
        try {
            userFriends = this.client.fetchConnection("me/friends", User.class);
        } catch (FacebookOAuthException ex) {
            throw new FacebookImportException("You need to connect to Facebook before importing.\n"
					+ "Please go to Preferences and setup connection to Facebook.");
        }
        
		// create list with contacts to import
		List<Contact> contactsToImport = new ArrayList<Contact>();
        // count how many contacts are there to import
        int total = 0;
        for (List<User> userList : userFriends) {
            total += userList.size();
        }
        StatusBar.setProgressBounds(0, total);
        StatusBar.setMessage("Importing contacts...");
        boolean end = false;
        // facebook returns the list of users in parts
		for (List<User> userList : userFriends) {
			for (User user : userList) {
                if (FacebookImportThread.isThreadInterrupted()) {
                    end = true;
                    break;
                }
				try {
					contactsToImport.add(this.fetchContact(user.getId()));
                    this.processed++;
                    StatusBar.setProgressValue(this.processed);
				} catch (Exception e) {
                    LoggerFactory.getLogger(this.getClass()).error("Import of contact failed: {}", LoggerUtil.getStackTrace(e));
					continue;
				}
			}
            if (end) {
                break;
            }
		}
		return contactsToImport;
	}

	/**
	 * Import contacts from Facebook to selected group.
	 * 
	 * @param group
	 */
	public int importContacts(String group) throws FacebookImportException {
        // set progress
        this.processed = 0;
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

	/**
	 * Test the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws FacebookImportException {
		FacebookImport fb = new FacebookImport();
		List<Contact> cs = fb.fetchContacts();
		for (Contact c : cs) {
			System.out.println(c.getFullName() + " " + c.getAllEmails());
		}
	}
}
