package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.util.List;

/**
 * Interface for persistence layer.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public interface IDatabase {
	// CONTACTS
	List<Contact> getAllContacts();  	
	
	//List<Contact> getAllContactsFromGroup(int id);
	//List<Contact> getSpecificContacts(List<Integer> id);	
	//void saveContacts(List<Contact> contacts);	

	// GROUPS
	List<Group> getAllGroups();   
}
