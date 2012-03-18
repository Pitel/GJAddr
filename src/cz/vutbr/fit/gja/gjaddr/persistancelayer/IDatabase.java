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
	List<Contact> getSpecificContacts(List<Integer> id);		
	List<Contact> addNewContacts(List<Contact> contacts);
	List<Contact> updateContact(Contact contact);
	List<Contact> removeContacts(List<Integer> contactsId);
	
	// GROUP <=> CONTACTS
	List<Contact> getAllContactsFromGroup(int groupId);
	List<Group> getAllGroupsForContact(int contactId);		
	List<Contact> addContactsToGroup(int groupId, List<Integer> contactsIdToAdd);
	List<Contact> removeContactsFromGroup(int groupId, List<Integer> contactsIdToRemove);	
					
	// GROUPS
	List<Group> getAllGroups();	
	List<Group> addNewGroup(String name);	
	List<Group> updateGroup(Group group);	
	List<Group> removeGroups(List<Integer> groupId);
}
