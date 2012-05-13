package cz.vutbr.fit.gja.gjaddr.importexport;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import cz.vutbr.fit.gja.gjaddr.gui.StatusBar;
import cz.vutbr.fit.gja.gjaddr.importexport.exception.FacebookImportException;
import cz.vutbr.fit.gja.gjaddr.importexport.util.Progress;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.TypesEnum;
import cz.vutbr.fit.gja.gjaddr.util.LoggerUtil;
import java.util.ArrayList;
import java.util.List;
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
     * Import progress.
     */
    private Progress progress;

	/**
	 * Constructor.
	 */
	public FacebookImport() {
        this.progress = new Progress();
		this.database = Database.getInstance();
		this.token = this.database.getToken(ServicesEnum.FACEBOOK);
		if (this.token != null) {
			this.client = new DefaultFacebookClient(this.token.getToken());
		} else {
			this.client = null;
		}
	}

    /**
     * Get the import progress.
     * 
     * @return 
     */
    public Progress getProgress() {
        return progress;
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
			contact.setDateOfBirth(user.getBirthdayAsDate());
		}

		// custom fields
        if (user.getHometownName() != null && !user.getHometownName().isEmpty()) {
            customFields.add(new Custom("hometown", user.getHometownName()));
        }
        if (user.getLocation() != null && user.getLocation().getName() != null) {
            customFields.add(new Custom("location", user.getLocation().getName()));
        }
		contact.setCustoms(customFields);

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
		Connection<User> userFriends = this.client.fetchConnection("me/friends", User.class);
		// create list with contacts to import
		List<Contact> contactsToImport = new ArrayList<Contact>();
        // count how many contacts are there to import
        int total = 0;
        for (List<User> userList : userFriends) {
            total += userList.size();
        }
        this.progress.setAll(total);
        StatusBar.setProgressBounds(0, total);
        StatusBar.setMessage("Importing contacts...");
        // facebook returns the list of users in parts
		for (List<User> userList : userFriends) {
			for (User user : userList) {
                this.progress.incProcessed();
				try {
					contactsToImport.add(this.fetchContact(user.getId()));
                    this.progress.incSuccessful();
                    StatusBar.setProgressValue(this.progress.getSuccessful());
				} catch (Exception e) {
                    LoggerFactory.getLogger(this.getClass()).error("Import of contact failed: {}", LoggerUtil.getStackTrace(e));
					continue;
				}
			}
		}
		return contactsToImport;
	}

	/**
	 * Import contacts from Facebook to selected group.
	 * 
	 * @param group
	 */
	public int importContacts(Progress progress, String group) throws FacebookImportException {
        // set progress
        this.progress = progress;
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
        
        StatusBar.setMessage("Ready");

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
        Progress p = fb.getProgress();
        System.out.println(p.getAll());
        System.out.println(p.getProcessed());
        System.out.println(p.getSuccessful());
	}
}
