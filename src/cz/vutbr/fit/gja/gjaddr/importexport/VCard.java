package cz.vutbr.fit.gja.gjaddr.importexport;

import a_vcard.android.syncml.pim.PropertyNode;
import a_vcard.android.syncml.pim.VDataBuilder;
import a_vcard.android.syncml.pim.VNode;
import a_vcard.android.syncml.pim.vcard.VCardException;
import a_vcard.android.syncml.pim.vcard.VCardParser;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Address;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Email;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.PhoneNumber;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Url;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.cardme.io.VCardWriter;
import net.sourceforge.cardme.vcard.VCardImpl;
import net.sourceforge.cardme.vcard.VCardVersion;
import net.sourceforge.cardme.vcard.features.AddressFeature;
import net.sourceforge.cardme.vcard.features.EmailFeature;
import net.sourceforge.cardme.vcard.features.NoteFeature;
import net.sourceforge.cardme.vcard.features.TelephoneFeature;
import net.sourceforge.cardme.vcard.types.AddressType;
import net.sourceforge.cardme.vcard.types.EmailType;
import net.sourceforge.cardme.vcard.types.FormattedNameType;
import net.sourceforge.cardme.vcard.types.NameType;
import net.sourceforge.cardme.vcard.types.NoteType;
import net.sourceforge.cardme.vcard.types.TelephoneType;
import net.sourceforge.cardme.vcard.types.URLType;
import net.sourceforge.cardme.vcard.types.VersionType;
import org.slf4j.LoggerFactory;

/**
 * Class for importing and exporting vCards
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit.vutbr.cz>,
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
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
	 * Import vCard file.
	 * 
	 * @param file
	 * @throws VCardException
	 */
	public void importContacts(File file) throws VCardException {
		this.importContactsToGroup(file, null);
	}

	/**
	 * Import contacts from vCard file to group (specified by it's name).
	 *
	 * @param file
	 * @param group Name of group to import to.
	 * @throws VCardException
	 */
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
	public void exportContacts(File file, List<Contact> contacts) 
			throws FileNotFoundException, IOException {

		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);

			VCardWriter writer = new VCardWriter();

			// create one card for each contact
			for (Contact c : contacts) {
				net.sourceforge.cardme.vcard.VCard vcard = new VCardImpl();
				vcard.setVersion(new VersionType(VCardVersion.V3_0));

				// set contact name
				NameType name = new NameType();
				name.setFamilyName(c.getSurName());
				name.setGivenName(c.getFirstName());
				vcard.setName(name);
				vcard.setFormattedName(new FormattedNameType(c.getFullName()));

				// set contact addresses
				for (Address address : c.getAdresses()) {
					AddressFeature af = new AddressType();
					af.setCountryName(address.getCountry());
					af.setLocality(address.getCity());
					af.setStreetAddress(address.getStreet() + " " + address.getNumber());
					af.setPostalCode(address.getPostCodeAsString());
					// TODO set type
					vcard.addAddress(af);
				}

				// set contact emails
				for (Email email : c.getEmails()) {
					EmailFeature ef = new EmailType();
					ef.setEmail(email.getEmail());
					// TODO set type
					vcard.addEmail(ef);
				}

				// set contact note
				if (c.getNote() != null && !c.getNote().isEmpty()) {
					NoteFeature note = new NoteType();
					note.setNote(c.getNote());
					vcard.addNote(note);
				}

				// set contact phones
				for (PhoneNumber phone : c.getPhoneNumbers()) {
					TelephoneFeature tf = new TelephoneType();
					tf.setTelephone(phone.getNumber());
					vcard.addTelephoneNumber(tf);
				}

				// set contact URLs
				for (Url url : c.getUrls()) {
					vcard.addURL(new URLType(url.getValue()));
				}

				//create vCard representation
				writer.setVCard(vcard);
				String vcardString = writer.buildVCardString();

				//write vCard to the output stream
				osw.write(vcardString);
				osw.write("\n"); //add empty lines between contacts
			}
		} finally {
			try {
				osw.close();
			} finally {
				fos.close();
			}
		}
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
