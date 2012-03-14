package cz.vutbr.fit.gja.gjaddr.tests;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.TestDatabase;
import java.util.AbstractList;
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
		
		//printGroups(db.getAllGroups());
		//printContacts(db.getAllContacts());		
		
//		List<Integer> spec = new ArrayList<Integer>();
//		spec.add(2);
//		spec.add(12);		
//		spec.add(3);		
//		spec.add(55);		
//		printContacts(db.getSpecificContacts(spec));
		
		testAddNewGroup(db);
		testUpdateGroup(db);
		testRemoveGroup(db);		
		
		db.commitChanges();
  }
	
	private static void printGroups(List<Group> groups) {		
		for (Group group: groups) {
			System.out.println(group.getId() + ", " + group.getName());	
		}		
	}
	
	private static void printContacts(List<Contact> contacts) {		
		for (Contact contact: contacts) {
			System.out.println(contact.getId() + ", " + contact.getFirstName() + ", " + contact.getSurName() + ", " + contact.getNickName());	
		}		
	}	
	
	private static void testRemoveGroup(Database db) { 
		
		List<Integer> groupsToDelete = new ArrayList<Integer>();
		groupsToDelete.add(4);			
		groupsToDelete.add(6);			
		groupsToDelete.add(120);			
		
		System.out.println();			
		System.out.println("CURRENT STATE: ");		
		
		List<Group> groupsBefore = db.getAllGroups(); 		
		printGroups(groupsBefore);
		
		System.out.println();	
		System.out.println("AFTER REMOVE GROUP id=4,6");		
	
		List<Group> groupsAfter = db.removeGroups(groupsToDelete);		
		printGroups(groupsAfter);	
	}
	
	private static void testAddNewGroup(Database db) { 
		
		System.out.println();			
		System.out.println("CURRENT STATE: ");		
		
		List<Group> groupsBefore = db.getAllGroups(); 		
		printGroups(groupsBefore);
		
		System.out.println();			
		System.out.println("AFTER ADDED GROUP name=nova skupina");		
		
		List<Group> groupsAfter = db.addNewGroup("nova skupina");		
		printGroups(groupsAfter);		
	}	

	private static void testUpdateGroup(Database db) { 
		
		Group group = new Group(6, "XXX");
		
		System.out.println();			
		System.out.println("CURRENT STATE: ");		
		
		List<Group> groupsBefore = db.getAllGroups(); 		
		printGroups(groupsBefore);
		
		System.out.println();	
		System.out.println("AFTER UPDATE GROUP id=" + group.getId());		
			
		List<Group> groupsAfter = db.updateGroup(group);
		printGroups(groupsAfter);				
	}	
}
