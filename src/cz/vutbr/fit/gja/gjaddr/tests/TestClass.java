package cz.vutbr.fit.gja.gjaddr.tests;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.TestDatabase;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.tables.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.tables.Group;
import java.io.File;
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
		
		boolean dbFileExists = (new File("gja.db")).exists();		
			
		// get database instance
		Database db = Database.getInstance();

		// fill the testing data
		if (!dbFileExists) {
			TestDatabase.fillTestingData(db);
		}
		
		testAddNewGroup(db);
		testRemoveGroup(db);		
		testUpdateGroup(db);

//		// get all contacts
//		List<Contact> contacts = db.getAllContacts();
//		
//		for (Contact c: contacts) {
//			System.out.println(c.getFirstName() + ", " + c.getSurName() + ", " + c.getNickName());	
//		}		
//
//		
//		// getAllContactsFromGroup
//		List<Contact> contacts2 = db.getAllContactsFromGroup(2);
//
//		for (Contact c: contacts2) {
//			System.out.println(c.getFirstName() + ", " + c.getSurName() + ", " + c.getNickName());
//		}
//
//		System.out.println();
//		
//		// get specific contacts
//		List<Integer> ids = new ArrayList<Integer>();
//		ids.add(1);
//		ids.add(2);
//		
//		List<Contact> specContact = db.getSpecificContacts(ids);		
//		
//		for (Contact c: specContact) {
//			System.out.println(c.getFirstName() + ", " + c.getSurName() + ", " + c.getNickName());		
//		}
//		
//		System.out.println();		
//		
		
// get all groups    
////		List<Group> groups = db.getAllGroups(); 
////		
////		for (Group g: groups) {
////			System.out.println(g.getName());	
////		}		
		
  }
	
	private static void testRemoveGroup(Database db) { 
		
		System.out.println();			
		System.out.print("BEFORE: ");		
		List<Group> groupsBefore = db.getAllGroups(); 		

		for (Group g: groupsBefore) {
			System.out.println(g.getName());	
		}		
		
		System.out.println();	
		System.out.print("AFTER REMOVE: ");		
		
		List<Integer> groupsToDelete = new ArrayList<Integer>();
		groupsToDelete.add(3);	
	
		List<Group> newGroups = db.removeGroups(groupsToDelete);
		
		for (Group g: newGroups) {
			System.out.println(g.getName());	
		}			
		
		System.out.println();			
		System.out.print("NEW QUERY: ");				
		
		List<Group> groupsAfter = db.getAllGroups(); 
		
		for (Group g: groupsAfter) {
			System.out.println(g.getName());	
		}				
	}
	
	private static void testAddNewGroup(Database db) { 
		
		System.out.println();			
		System.out.print("BEFORE: ");		
		List<Group> groupsBefore = db.getAllGroups(); 		

		for (Group g: groupsBefore) {
			System.out.println(g.getName());	
		}		
		
		System.out.println();			
		System.out.print("AFTER ADDED: ");	
		
		List<Group> newGroups = db.addNewGroup("nova skupina");
		
		for (Group g: newGroups) {
			System.out.println(g.getName());	
		}			
		
		System.out.println();			
		System.out.print("NEW QUERY: ");
		
		List<Group> groupsAfter = db.getAllGroups(); 
		
		for (Group g: groupsAfter) {
			System.out.println(g.getName());	
		}				
	}	

	private static void testUpdateGroup(Database db) { 
		
		System.out.println();			
		System.out.print("BEFORE: ");		
		List<Group> groupsBefore = db.getAllGroups(); 		

		for (Group g: groupsBefore) {
			System.out.println(g.getName());	
		}		
		
		System.out.println();	
		System.out.print("AFTER UPDATE: ");		
		
		Group group = new Group(1, "XXX");
		List<Group> newGroups = db.updateGroup(group);
		
		for (Group g: newGroups) {
			System.out.println(g.getName());	
		}			
		
		System.out.println();	
		System.out.print("NEW QUERY: ");				
		List<Group> groupsAfter = db.getAllGroups(); 
		
		for (Group g: groupsAfter) {
			System.out.println(g.getName());	
		}				
	}	
}
