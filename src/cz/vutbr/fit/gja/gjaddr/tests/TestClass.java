package cz.vutbr.fit.gja.gjaddr.tests;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ragaj
 */
public class TestClass {
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
				
		Database db = new Database();

		//TestDatabase.fillTestingData(db);
		
		printGroups("All groups", db.getAllGroups());
		printContacts("All contacts", db.getAllContacts());		
	
		printContacts("Contacts in group 1", db.getAllContactsFromGroup(1));
		printContacts("Contacts in group 2", db.getAllContactsFromGroup(2));
		printContacts("Contacts in group 3", db.getAllContactsFromGroup(3));		
		printContacts("Contacts in group 4", db.getAllContactsFromGroup(4));
		
		printGroups("Groups assign to contact 1", db.getAllGroupsForContact(1));
		printGroups("Groups assign to contact 2", db.getAllGroupsForContact(2));	
		printGroups("Groups assign to contact 3", db.getAllGroupsForContact(3));				
		
//		List<Integer> spec = new ArrayList<Integer>();
//		spec.add(2);
//		spec.add(12);		
//		spec.add(3);		
//		spec.add(55);		
//		printContacts(db.getSpecificContacts(spec));
		
		//testAddNewGroup(db);
		//testUpdateGroup(db);
		//testRemoveGroup(db);		
		
		db.commitChanges();
  }
	
	private static void printGroups(String header, List<Group> groups) {		
		
		System.out.println();			
		System.out.println(header);	
		System.out.println("----");			
		
		for (Group group: groups) {
			System.out.println(group.getId() + ", " + group.getName());	
		}		
	}
	
	private static void printContacts(String header, List<Contact> contacts) {		
		
		System.out.println();			
		System.out.println(header);	
		System.out.println("----");	
		
		for (Contact contact: contacts) {
			System.out.println(contact.getId() + ", " + contact.getFirstName() + ", " + contact.getSurName() + ", " + contact.getNickName());	
		}		
	}	
	
	private static void testRemoveGroup(Database db) { 
		
		List<Integer> groupsToDelete = new ArrayList<Integer>();
		groupsToDelete.add(4);			
		groupsToDelete.add(6);			
		groupsToDelete.add(120);			

		List<Group> groupsBefore = db.getAllGroups(); 		
		printGroups("CURRENT STATE", groupsBefore);
	
		List<Group> groupsAfter = db.removeGroups(groupsToDelete);		
		printGroups("AFTER REMOVE GROUP id=4,6", groupsAfter);	
	}
	
	private static void testAddNewGroup(Database db) { 
			
		List<Group> groupsBefore = db.getAllGroups(); 		
		printGroups("CURRENT STATE", groupsBefore);
		
		List<Group> groupsAfter = db.addNewGroup("nova skupina");		
		printGroups("AFTER ADDED GROUP name=nova skupina", groupsAfter);		
	}	

	private static void testUpdateGroup(Database db) { 
		
		Group group = new Group(6, "XXX");
		
		List<Group> groupsBefore = db.getAllGroups(); 		
		printGroups("CURRENT STATE", groupsBefore);
			
		List<Group> groupsAfter = db.updateGroup(group);
		printGroups("AFTER UPDATE GROUP id=" + group.getId(), groupsAfter);				
	}	
}
