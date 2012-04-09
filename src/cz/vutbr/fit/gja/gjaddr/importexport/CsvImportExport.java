
package cz.vutbr.fit.gja.gjaddr.importexport;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Email;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.PhoneNumber;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Class for importing and exporting contacts from/to CSV.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 * @see <http://opencsv.sourceforge.net/>
 */
public class CsvImportExport {

	/**
	 * Application database.
	 */
	private Database database = Database.getInstance();

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
	 * Import contacts from CSV file.
	 * 
	 * @param file
	 */
	public void importContacts(File file) throws IOException {
		this.importContacts(file, null);
	}

	/**
	 * Import contacts from CSV file to specific group.
	 * 
	 * @param file
	 * @param group
	 */
	public void importContacts(File file, String group) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(file));
		List<Contact> contacts = new ArrayList<Contact>();
		String [] nextLine;

		while ((nextLine = reader.readNext()) != null) {
			// each line must have 7 parts
			if (nextLine.length < 7) {
				continue;
			}

			// create new contact
			Contact contact = new Contact(nextLine[0], nextLine[1], nextLine[2], nextLine[3]);

			// set contact emails
			String[] emails = nextLine[4].split(",");
			List<Email> emailsList = new ArrayList<Email>();
			for (String email : emails) {
				emailsList.add(new Email(1, email));
			}
			contact.setEmails(emailsList);

			// set contact phones
			String[] phones = nextLine[4].split(", ");
			List<PhoneNumber> phonesList = new ArrayList<PhoneNumber>();
			for (String phone : phones) {
				phonesList.add(new PhoneNumber(1, phone));
			}
			contact.setPhoneNumbers(phonesList);

			// TODO addresses

			// add contact to list to be imported
			contacts.add(contact);
			LoggerFactory.getLogger(this.getClass()).debug("Adding contact ["
					+ nextLine[1] + " " + nextLine[0] + "].");
		}

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

	/**
	 * Export contacts to CSV file.
	 *
	 * @param file
	 * @param contacts
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void exportContacts(File file, List<Contact> contacts)
			throws FileNotFoundException, IOException {

		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);

			CSVWriter writer = new CSVWriter(new FileWriter(file));
			// TODO correct format

			for (Contact c : contacts) {
				List<String> entries = new ArrayList<String>();
				entries.add(c.getSurName());
				entries.add(c.getFirstName());
				entries.add(c.getNickName());
				entries.add(c.getNote());
				entries.add(c.getAllEmails());
				entries.add(c.getAllPhones());
				entries.add(c.getAllAddresses());
				writer.writeNext(entries.toArray(new String[entries.size()]));
			}

			writer.close();
		} finally {
			try {
				osw.close();
			} finally {
				fos.close();
			}
		}
	}
}
