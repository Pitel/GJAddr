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
	List<Contact> getSpecificContacts(List<Contact> requiredContacts);		
	List<Contact> addNewContacts(List<Contact> newContacts);
	List<Contact> updateContact(Contact contact);
	List<Contact> removeContacts(List<Contact> contactsToRemove);
	
	// GROUP <=> CONTACTS
	List<Contact> getAllContactsFromGroup(List<Group> requiredGroups);
	List<Group> getAllGroupsForContact(Contact contact);		
	List<Contact> addContactsToGroup(Group group, List<Contact> contactsToAdd);
	List<Contact> removeContactsFromGroup(Group group, List<Contact> contactsToRemove);	
					
	// GROUPS
	List<Group> getAllGroups();	
	List<Group> addNewGroup(String name);	
	List<Group> updateGroup(Group group);	
	List<Group> removeGroups(List<Group> groupsToRemove);
}
