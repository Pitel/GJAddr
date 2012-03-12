package cz.vutbr.fit.gja.gjaddr.tests;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.TestDatabase;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.tables.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.tables.Group;
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
		// step 1 - get database instance
		Database db = Database.getInstance();

		// fill the testing data
		// TestDatabase.fillTestingData(db);

		// get all contacts
		List<Contact> contacts = db.getAllContacts();
		
		for (Contact c: contacts) {
			System.out.println(c.getFirstName() + ", " + c.getSurName() + ", " + c.getNickName());	
		}		

		System.out.println();		

		// get all groups    
		List<Group> groups = db.getAllGroups(); 
		
		for (Group g: groups) {
			System.out.println(g.getName());	
		}
		
		System.out.println();
		
		// getAllContactsFromGroup
		List<Contact> contacts2 = db.getAllContactsFromGroup(2);

		for (Contact c: contacts2) {
			System.out.println(c.getFirstName() + ", " + c.getSurName() + ", " + c.getNickName());
		}

		System.out.println();
		
		// get specific contacts
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Contact> specContact = db.getSpecificContacts(ids);		
		
		for (Contact c: specContact) {
			System.out.println(c.getFirstName() + ", " + c.getSurName() + ", " + c.getNickName());		
		}
		
		System.out.println();			
  }
}
