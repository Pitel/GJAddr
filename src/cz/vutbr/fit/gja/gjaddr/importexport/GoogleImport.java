package cz.vutbr.fit.gja.gjaddr.importexport;

import com.google.gdata.client.contacts.*;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.util.ServiceException;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.AuthToken;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Messenger;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Class for importing Google contacts
 *
 * TODO:
 *  - solve NULL values
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit.vutbr.cz>
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
	 * Constructor.
	 */
	public GoogleImport() {
		this.database = new Database();
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
			emails.add(new cz.vutbr.fit.gja.gjaddr.persistancelayer.Email(1, email.getAddress()));
		}

		// get messengers
		List<Messenger> messengers = new ArrayList<Messenger>();
		for (Im im : entry.getImAddresses()) {
			messengers.add(new Messenger(1, im.getAddress()));
		}

		// build contact
		Contact contact = new Contact(firstName, surname, nickname, note);
		contact.setEmails(emails);
		contact.setMessenger(messengers);

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
	private List<Contact> fetchContacts() throws MalformedURLException, IOException, ServiceException {
		// fetch list of contacts
		List<Contact> contacts = new ArrayList<Contact>();
		
		// request contact feed
		URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full?access_token="
				+ this.token.getToken());
		LoggerFactory.getLogger(this.getClass()).info("Sending request to : " + feedUrl.toString());
		ContactFeed resultFeed = this.service.getFeed(feedUrl, ContactFeed.class);

		// add all contacts to list
		for (ContactEntry entry : resultFeed.getEntries()) {
			contacts.add(this.fetchContact(entry));
		}

		return contacts;
	}

	/**
	 * Import contacts from Google.
	 *
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public void importContacts() throws MalformedURLException, IOException, ServiceException {
		this.importContacts(null);
	}
	
	/**
	 * Import contacts from Google to selected group.
	 *
	 * @param group
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public void importContacts(String group) throws MalformedURLException, IOException, ServiceException {
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
	}
}
