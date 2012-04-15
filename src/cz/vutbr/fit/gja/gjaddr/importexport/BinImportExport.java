
package cz.vutbr.fit.gja.gjaddr.importexport;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Serialization;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class for importing and exporting contacts from/to own BIN format.
 *
 * @author Bc. Radek Gajdusek Herrmannova <xgajdu07@stud.fit.vutbr.cz>
 */
public class BinImportExport {

	private Database database = Database.getInstance();	
	
	private Group getGroupByName(String groupName) {
		for (Group g : this.database.getAllGroups()) {
			if (g.getName().equals(groupName)) {
				return g;
			}
		}
		return null;
	}	
	
	/**
	 * Import contacts from BIN file.
	 * 
	 * @param file
	 */
	public int importContacts(File file) throws IOException {
		return this.importContacts(file, null);
	}	
	
	/**
	 * Import contacts from BIN file to specific group.
	 * 
	 * @param file
	 * @param group
	 */
	public int importContacts(File file, String group) {
	
		List<Contact> contacts = Serialization.load(file.getAbsolutePath());
				
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

		return contacts.size();
	}

	/**
	 * Export contacts to BIN file.
	 *
	 * @param file
	 * @param contacts
	 */
	public void exportContacts(File file, List<Contact> contacts) {
		Serialization.save(file.getAbsolutePath(), contacts);
	}
}
