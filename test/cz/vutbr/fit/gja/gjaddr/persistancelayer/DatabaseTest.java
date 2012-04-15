package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Ragaj
 */
public class DatabaseTest {
	
	public DatabaseTest() {
		this.database = Database.getInstance();
		this.database.clearAllData();	
	}

	private Database database = null;
	
	@BeforeClass
	public static void runBeforeClass() {
		System.out.println("PERSISTANCE LAYER TEST");		
		System.out.println("-----------------------");	
	}	
	
	@Before
	public void setUp() {
		TestData.fillTestingData(database);		
	}
	
	@After
	public void tearDown() {
		this.database.clearAllData();			
	}

	/**
	 * Test of getAllContacts method, of class Database.
	 */
	@Test
	public void testGetAllContacts() {
		System.out.println("testing GetAllContacts method ...");

		List<Contact> result = database.getAllContacts();
		
		int expNumberOfResults = 3;
		int numberOfResults = result.size();
		
		assertEquals(expNumberOfResults, numberOfResults);				
	}

	/**
	 * Test of getSpecificContacts method, of class Database.
	 */
	@Test
	public void testGetSpecificContacts() {
		
		System.out.println("testing getSpecificContacts method ...");			

		// no specific contact
		List<Contact> expectedResult = this.database.getAllContacts();
		List<Contact> result = this.database.getSpecificContacts(new ArrayList<Contact>());		
		assertEquals(0, result.size());		
		
		// all contacts
		result = this.database.getSpecificContacts(expectedResult);	
		assertEquals(expectedResult, result);		
		
		// some selection
		expectedResult.remove(1);		
		result = this.database.getSpecificContacts(expectedResult);	
		
		assertEquals(expectedResult, result);
	}

	/**
	 * Test of getAllContactsFromGroup method, of class Database.
	 */
	@Test
	public void testGetAllContactsFromGroup() {
		System.out.println("testing getAllContactsFromGroup method ...");
		
		List<Contact> contacts = this.database.getAllContacts();
		
		List<Contact> contactsGroup = new ArrayList<Contact>();
		
		contactsGroup.add(contacts.get(0));
		contactsGroup.add(contacts.get(1));					
		
		List<Group> groups = this.database.getAllGroups();
		
		List<Contact> expectedResult = new ArrayList<Contact>();
		List<Group> requiredGroups = new ArrayList<Group>();

		// empty required groups list
		List<Contact> result = this.database.getAllContactsFromGroup(requiredGroups);	
		assertEquals(expectedResult, result);		
		
		// get all groups - ony one group with id = -1
		expectedResult = this.database.getAllContacts();		
		requiredGroups.add(new Group(-1, "aaaa"));
		result = this.database.getAllContactsFromGroup(requiredGroups);		
		assertEquals(expectedResult, result);	
		
		// more groups with one group id = -1
		requiredGroups.add(groups.get(0));
		requiredGroups.add(groups.get(1));		
		result = this.database.getAllContactsFromGroup(requiredGroups);		
		assertEquals(expectedResult, result);	

		// get contacts from all groups
		expectedResult = this.database.getAllContacts();	
		result = this.database.getAllContactsFromGroup(groups);				
		assertEquals(expectedResult, result);		
		
		requiredGroups.clear();
		
		// get contact from one group
		expectedResult = contactsGroup;
		requiredGroups.add(groups.get(0));
		result = this.database.getAllContactsFromGroup(requiredGroups);				
		assertEquals(expectedResult, result);		
		
		// get contacts from more groups
		requiredGroups.add(groups.get(3));				
		expectedResult.add(contacts.get(2));
		result = this.database.getAllContactsFromGroup(requiredGroups);				
		assertEquals(expectedResult, result);			
		
		expectedResult.clear();
		requiredGroups.clear();
		
		// get contacts from non-existing group
		requiredGroups.add(new Group(10000, "test"));
		result = this.database.getAllContactsFromGroup(requiredGroups);
		assertEquals(new ArrayList<Contact>(), result);			
	}

	/**
	 * Test of getAllGroups method, of class Database.
	 */
	@Test
	public void testGetAllGroups() {
		System.out.println("testing getAllGroups method...");
		
		List<Group> expResult = new ArrayList<Group>();
		
		expResult.add(new Group(1, "Lahvators"));
		expResult.add(new Group(2, "Fit"));
		expResult.add(new Group(3, "DPMB"));
		expResult.add(new Group(4, "Test"));		
		
		List<Group> result = this.database.getAllGroups();		
		assertEquals(expResult, result);		
	}

	/**
	 * Test of addNewGroup method, of class Database.
	 */
	@Test
	public void testAddNewGroup() {
		System.out.println("testing addNewGroup method...");
		
		String name = "nova skupina";	
		Group newGroup = new Group(5, name);
		
		int expCount = this.database.getAllGroups().size();
		
		List<Group> result = database.addNewGroup(name);		
		int count = result.size();
					
		int newGroupIndex = result.indexOf(newGroup);
		
		assertEquals(expCount + 1, result.size());				
		assertEquals(newGroup, result.get(newGroupIndex));
		
		// add existing group
		result = this.database.addNewGroup("Lahvators");	
		List<Group> expectedResult = null;
		assertEquals(expectedResult, result);						
	}

	/**
	 * Test of updateGroup method, of class Database.
	 */
	@Test
	public void testUpdateGroup() {
		System.out.println("testing addNewGroup method...");
		
		int groupIndex = 0;
		
		List<Group> groupsBeforeUpdate = this.database.getAllGroups();

		Group groupToUpdate = groupsBeforeUpdate.get(groupIndex);
		groupToUpdate.setName("XYZ");
				
		List<Group> result = this.database.updateGroup(groupToUpdate);
		assertEquals(groupsBeforeUpdate, result);	

		Group groupNotInDb = new Group(100, "FAILED");
		groupsBeforeUpdate = this.database.getAllGroups();		
		result = this.database.updateGroup(groupNotInDb);
		
		assertEquals(groupsBeforeUpdate, result);
	}

	/**
	 * Test of removeGroups method, of class Database.
	 */
	@Test
	public void testRemoveGroups() {
		System.out.println("testing removeGroups method ....");
		
		// remove no groups
		List<Group> expectedGroups = this.database.getAllGroups();		
		List<Group> result = this.database.removeGroups(new ArrayList<Group>());		
		assertEquals(expectedGroups, result);			
		
		// remove specific groups
		List<Group> groupsToRemove = new ArrayList<Group>(expectedGroups);
		
		groupsToRemove.remove(0);
		groupsToRemove.remove(1);
		
		for (Group group: groupsToRemove) {
			expectedGroups.remove(group);
		}
		
		result = this.database.removeGroups(groupsToRemove);		
		assertEquals(expectedGroups, result);				
		
		// remove all groups
		expectedGroups = this.database.getAllGroups();		
		result = this.database.removeGroups(expectedGroups);
		expectedGroups.clear();
		assertEquals(expectedGroups, result);		
	}

	/**
	 * Test of addNewContacts method, of class Database.
	 */
	@Test
	public void testAddNewContacts() {
		System.out.println("testing addNewContacts method ...");
		
		List<Contact> expectedResult = this.database.getAllContacts();
		
		// call add new contacts with empty list
		List<Contact> newContacts = new ArrayList<Contact>();
		List<Contact> result = this.database.addNewContacts(newContacts);
		assertEquals(expectedResult, result);				
		
		// add one contact
		Contact contact1 = new Contact("aa", "aa", "aa", "aa");
		newContacts.add(contact1);		
		result = this.database.addNewContacts(newContacts);	
		expectedResult.add(contact1);
		assertEquals(expectedResult, result);	
		
		newContacts.clear();
		
		// add multiple contacts
		Contact contact2 = new Contact("bb", "bb", "bb", "bb");
		Contact contact3 = new Contact("cc", "cc", "cc", "cc");	
		newContacts.add(contact2);		
		newContacts.add(contact3);	
		result = this.database.addNewContacts(newContacts);	
		expectedResult.add(contact2);
		expectedResult.add(contact3);		
		assertEquals(expectedResult, result);					
	}

	/**
	 * Test of updateContact method, of class Database.
	 */
	@Test
	public void testUpdateContact() {
		System.out.println("testing updateContact method ...");

		List<Contact> expectedResult = this.database.getAllContacts();

		// update existing contact
		Contact contact = expectedResult.get(0);
		contact.setFirstName("PETR");
		contact.setNote("ŠRÁMEK");		
		List<Contact> result = this.database.updateContact(contact);
		assertEquals(expectedResult, result);			
		
		// update non-existing contact
		Contact newContact = new Contact("aa", "bb", "cc", "ddd");
		result = this.database.updateContact(newContact);
		assertEquals(expectedResult, result);						
	}

	/**
	 * Test of removeContacts method, of class Database.
	 */
	@Test
	public void testRemoveContacts() {
		System.out.println("testing removeContacts method ...");
				
		// remove empty contacts list
		List<Contact> expectedResult = this.database.getAllContacts();
		List<Contact> result = this.database.removeContacts(new ArrayList<Contact>());
		assertEquals(expectedResult, result);		
		
		// remove non-existing contacts
		Contact newContact = new Contact("aa", "bb", "cc", "ddd");
		List<Contact> contactsToRemove = new ArrayList<Contact>();
		contactsToRemove.add(newContact);
		result = this.database.removeContacts(new ArrayList<Contact>());
		assertEquals(expectedResult, result);	
		
		contactsToRemove.clear();
		
		// remove one contact
		Contact contactToRemove = expectedResult.remove(1);
		contactsToRemove.add(contactToRemove);		
		result = this.database.removeContacts(contactsToRemove);
		assertEquals(expectedResult, result);			
		
		// remove multiple contacts
		result = this.database.removeContacts(expectedResult);
		expectedResult.clear();
		assertEquals(expectedResult, result);					
	}

	/**
	 * Test of addContactsToGroup method, of class Database.
	 */
	@Test
	public void testAddContactsToGroup() {
		System.out.println("testing addContactsToGroup method ...");
		
		List<Contact> contacts = this.database.getAllContacts();
		
		// new group and assign all contacts
		List<Group> groups = this.database.addNewGroup("NOVA");		
		List<Contact> result = this.database.addContactsToGroup(groups.get(4), contacts);
		assertEquals(contacts, result);	
		
		// new group and assign one contact		
		groups = this.database.addNewGroup("NOVA2");	
		contacts.remove(1);
		contacts.remove(1);
		result = this.database.addContactsToGroup(groups.get(5), contacts);
		assertEquals(contacts, result);
		
		// assign existing contact with non-existing group		
		contacts.clear();
		result = this.database.addContactsToGroup(new Group(25, "badGroup"), contacts);
		assertEquals(contacts, result);
		
		// assign non-existing contact with existing group			
		Contact contact = new Contact("aa", "bb", "cc", "ddd");
		contacts.add(contact);
		result = this.database.addContactsToGroup(groups.get(2), contacts);
		contacts.clear();
		assertEquals(contacts, result);		
	}

	/**
	 * Test of removeContactsFromGroup method, of class Database.
	 */
	@Test
	public void testRemoveContactsFromGroup() {
		System.out.println("testing removeContactsFromGroup method ...");

		List<Contact> contactsToRemove = new ArrayList<Contact>();		
		List<Contact> expectedResult = new ArrayList<Contact>();
		List<Group> requiredGroups = new ArrayList<Group>();		
		
		// remove existing contact from non-existing group
		Contact contact = this.database.getAllContacts().get(1);
		contactsToRemove.add(contact);
		List<Contact> result = this.database.removeContactsFromGroup(new Group(0, "111"), contactsToRemove);
		assertEquals(expectedResult, result);
		
		contactsToRemove.clear();
		
		// remove non-existing contact from existing group						
		Group group = this.database.getAllGroups().get(0);
		contactsToRemove.add(new Contact("aa", "bb", "cc", "ddd"));		
		requiredGroups.add(group);
		expectedResult = this.database.getAllContactsFromGroup(requiredGroups);
		result = this.database.removeContactsFromGroup(group, contactsToRemove);
		assertEquals(expectedResult, result);
		
		contactsToRemove.clear();
		
		// remove one contact from group - Group Lahvators
		expectedResult = this.database.getAllContactsFromGroup(requiredGroups);
		contact = expectedResult.remove(0);
		contactsToRemove.add(contact);			
		result = this.database.removeContactsFromGroup(group, contactsToRemove);
		assertEquals(expectedResult, result);		
		
		contactsToRemove.clear();
		
		// remove one existing and one non-existing contact from group
		contactsToRemove = this.database.getAllContactsFromGroup(requiredGroups);
		contactsToRemove.add(new Contact("aa", "bb", "cc", "ddd"));
		result = this.database.removeContactsFromGroup(group, contactsToRemove);	
		expectedResult.clear();
		assertEquals(expectedResult, result);				
		
		contactsToRemove.clear();	
		requiredGroups.clear();				
		
		// remove all contact from group - Group Fit
		Group group2 = this.database.getAllGroups().get(1);	
		requiredGroups.add(group2);				
		contactsToRemove = this.database.getAllContactsFromGroup(requiredGroups);			
		result = this.database.removeContactsFromGroup(group, contactsToRemove);
		expectedResult.clear();
		assertEquals(expectedResult, result);		
	}

	/**
	 * Test of getAllGroupsForContact method, of class Database.
	 */
	@Test
	public void testGetAllGroupsForContact() {
		System.out.println("testing getAllGroupsForContact method ...");

		List<Group> expectedGroups = new ArrayList<Group>();		
		List<Contact> contacts = this.database.getAllContacts();
		List<Group> groups = this.database.getAllGroups();
		
		expectedGroups.add(groups.get(0));		
		List<Group> result = this.database.getAllGroupsForContact(contacts.get(0));	
		assertEquals(expectedGroups, result);					
		
		expectedGroups.clear();
		
		expectedGroups.add(groups.get(0));	
		expectedGroups.add(groups.get(1));			
		result = this.database.getAllGroupsForContact(contacts.get(1));	
		assertEquals(expectedGroups, result);					
		
		expectedGroups.clear();		
		
		expectedGroups.add(groups.get(1));	
		expectedGroups.add(groups.get(3));			
		result = this.database.getAllGroupsForContact(contacts.get(2));		
		assertEquals(expectedGroups, result);
		
		expectedGroups.clear();	
		
		Contact contact = new Contact("aa", "bb", "cc", "ddd");
		result = this.database.getAllGroupsForContact(contact);			
		assertEquals(expectedGroups, result);											
	}
}
