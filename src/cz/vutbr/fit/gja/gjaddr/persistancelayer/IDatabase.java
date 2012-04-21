package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
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
	List<Contact> addContactsToGroup(Group group, List<Contact> contactsToAdd);
	List<Contact> removeContactsFromGroup(Group group, List<Contact> contactsToRemove);	
  
	List<Group> getAllGroupsForContact(Contact contact);	  
  List<Contact> getAllContactsFromGroup(Group group);   
					
	// GROUPS
	Group getGroupByName(String name);
	List<Group> getAllGroups();	
	List<Group> addNewGroup(String name);	
	List<Group> updateGroup(Group group);	
	List<Group> renameGroup(Group group, String newName);
	List<Group> removeGroups(List<Group> groupsToRemove);

	// AUTH
	AuthToken getToken(ServicesEnum service);
	AuthToken getToken(Integer service);
	AuthToken addToken(AuthToken token);
	void removeToken(Integer service);
	void removeToken(ServicesEnum service);

	// BDAY
	List<Contact> getContactsWithBirtday();
}
