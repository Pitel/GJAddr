package cz.vutbr.fit.gja.gjaddr.importexport;

import a_vcard.android.syncml.pim.PropertyNode;
import a_vcard.android.syncml.pim.VDataBuilder;
import a_vcard.android.syncml.pim.VNode;
import a_vcard.android.syncml.pim.vcard.VCardException;
import a_vcard.android.syncml.pim.vcard.VCardParser;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Email;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.PhoneNumber;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Url;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Class for importing and exporting vCards
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit.vutbr.cz>,
 *         Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class VCard {

	/**
	 * Read all contents of file as string.
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private String readFileAsString(String filePath) throws IOException {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		FileInputStream f = new FileInputStream(filePath);
		f.read(buffer);
		return new String(buffer);
	}
	
	/**
	 * Import vCard file
	 *
	 * @param file File to import
	 * @exception FileNotFoundException vCard file not found
	 * @exception IOException vCard file could not be read
	 * @exception VCardException vCard could not be parsed
	 */
	public void importContacts(File file) throws VCardException {
		this.importContactsToGroup(file, null);
	}

	public void importContactsToGroup(File file, String group) throws VCardException {
		final VCardParser parser = new VCardParser();
		final VDataBuilder builder = new VDataBuilder();

		// read the file to one string and parse this string
		try {
			if (!parser.parse(this.readFileAsString(file.getAbsolutePath()), "UTF-8", builder)) {
				throw new VCardException("Could not parse vCard file [" + file + "].");
			}
		} catch (IOException ex) {
			throw new VCardException("Could not open vCard file [" + file + "]: " + ex.toString());
		}

		// list of contacts to be imported to database
		List<Contact> contacts = new ArrayList<Contact>();

		// cycle through all nodes of the vcard and build contacts
		for (VNode contact : builder.vNodeList) {
			ArrayList<PropertyNode> props = contact.propList;

			String firstName = null;
			String surname = null;
			String nickname = null;
			String note = null;
			List<PhoneNumber> phones = new ArrayList<PhoneNumber>();
			List<Email> emails = new ArrayList<Email>();
			List<Url> urls = new ArrayList<Url>();

			// TODO add all parameters
            for (PropertyNode prop : props) {
                if ("N".equals(prop.propName)) {
                    String[] nameParts = prop.propValue.split(";");
					if (nameParts.length >= 2) {
						surname = nameParts[0];
						firstName = nameParts[1];
					}
                } else if ("NICKNAME".equals(prop.propName)) {
					nickname = prop.propValue;
				} else if ("NOTE".equals(prop.propName)) {
					note = prop.propValue;
				} else if ("TEL".equals(prop.propName)) {
					phones.add(new PhoneNumber(0, prop.propValue));
				} else if ("EMAIL".equals(prop.propName)) {
					emails.add(new Email(0, prop.propValue));
				} else if ("URL".equals(prop.propName)) {
					urls.add(new Url(0, prop.propValue));
				}
            }

			// build new contact
			Contact dbContact = new Contact(firstName, surname, nickname, note);
			dbContact.setEmails(emails);
			dbContact.setPhoneNumbers(phones);
			dbContact.setUrls(urls);
			contacts.add(dbContact);
			LoggerFactory.getLogger(this.getClass()).debug("Adding contact [" + firstName + " " + surname + "].");
		}

		LoggerFactory.getLogger(this.getClass()).debug("Im here.");

		// save contacts in database
		Database db = new Database();
		if (group == null || group.isEmpty()) {
			LoggerFactory.getLogger(this.getClass()).debug("Group is empty.");
		} else {
			LoggerFactory.getLogger(this.getClass()).debug("Adding all to : " + group);
		}
		if (group != null) {
			db.addNewGroup(group);
			for (Group g : db.getAllGroups()) {
				LoggerFactory.getLogger(this.getClass()).debug(g.getName());
				if (g.getName().equals(group)) {
					LoggerFactory.getLogger(this.getClass()).debug("Now adding to " + g.getName());
					db.addContactsToGroup(g, contacts);
					return;
				}
			}
		} else {
			db.addNewContacts(contacts);
		}

		return;
	}

	/**
	 * Export to vCard file
	 */
	public void ex() {
		return;
	}

	/**
	 * Test the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			VCard vc = new VCard();
			vc.importContacts(new File("/Users/damirah/Downloads/contacts.vcf"));
		} catch (VCardException ex) {
			LoggerFactory.getLogger(VCard.class).error(ex.toString());
		}
	}
}
