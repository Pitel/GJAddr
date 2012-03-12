package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.tables.*;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating database, handle the database actions.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Database implements IDatabase {
	public static Database instance = null;

	private final String DB_NAME = "gja.db"; 
	private Connection conn = null;

	// database tables
	private List<Contact> contacts = null;
	private List<Group> groups = null;
	private List<GroupContact> groupContacts = null;
 	
	private Database() { 
		try {
			// create empty DB if not exists DB file
			boolean dbFileExists = (new File(this.DB_NAME)).exists();		
			
			Class.forName("org.sqlite.JDBC");

			// create a database, or open an existing database, in the sketch data folder:      
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + this.DB_NAME);

			if (!dbFileExists) {
				this.createEmptyDb();			
			}

			this.loadDataFromDb();
			
			//this.conn.setAutoCommit(false);
		}
		catch (ClassNotFoundException e) {
			System.err.println(e);
		}
		catch (SQLException e) {
			System.err.println(e);
		}
		
		//conn.close();
	}
   
	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();			
		}

		return instance;
  }    
  
	private void createEmptyDb() {

		this.cleanUpDatabase();

		this.createTableContact();
		this.createTableGroup();

		this.createTableGroupContact();

		this.createTableMessenger();    
		this.createTableUrl();   
		this.createTableAdress(); 
		this.createTablePhoneNumber();
		this.createTableEmail();
		this.createTableCustom();  
	}	
	
	private void cleanUpDatabase() {
    String[] tables = { "custom", "email", "phoneNumber", "adress",
                         "url", "messenger", "contact", "category", "groupContact" };     
    
    System.out.println("Cleaning up db ...");       
   
    for (int i = 0; i < tables.length; i++)
    {
			this.executeUpdate("DROP TABLE IF EXISTS " + tables[i]);  
    }
  }
		
	private void createTableContact() {      
		String def = "CREATE TABLE contact(" +
									"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"firstName TEXT, " + 
									"surName TEXT, " +          
									"nickName TEXT, "  +
									"dateOfBirth NUMERIC, "  +
									"photo BLOB, "  +    
									"note TEXT"  +              
									");";

		this.executeUpdate(def);   
	}      
	  
	private void createTableGroup() {
		String def = "CREATE TABLE category(" +
									"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"name TEXT " + 
									");";

		this.executeUpdate(def);   
	}
	
	private void createTableGroupContact() {      
		String def = "CREATE TABLE groupContact(" +
									"contactId INTEGER NOT NULL, " + 
									"groupId INTEGER NOT NULL, " +   						
									"PRIMARY KEY (contactId, groupId), " +		
									"FOREIGN KEY (contactId) REFERENCES contact(id), " +
									"FOREIGN KEY (groupId) REFERENCES category(id)" +
									");";

		this.executeUpdate(def);   
	}  		
		
	private void createTableMessenger() {      
		String def = "CREATE TABLE messenger(" +
									"Id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"Type INTEGER, " + 
									"Value TEXT, "  +
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id) ON DELETE CASCADE" +
									");";

		this.executeUpdate(def);   
	} 
  
	private void createTableUrl() {      
		String def = "CREATE TABLE url(" +
									"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"type INTEGER, " + 
									"value TEXT, "  +
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id) ON DELETE CASCADE" +
									");";

		this.executeUpdate(def);  
	}
  
	private void createTableAdress() {      
		String def = "CREATE TABLE adress(" +
									"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"type INTEGER, " + 
									"street TEXT, " + 
									"number INTEGER, " + 
									"city TEXT, " +             
									"postCode INTEGER, "  +
									"country TEXT, "  +            
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id) ON DELETE CASCADE" +
									");";

		this.executeUpdate(def);   
	}    
      
	private void createTablePhoneNumber() {      
		String def = "CREATE TABLE phoneNumber(" +
									"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"type INTEGER, " + 
									"number INTEGER, "  +
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id) ON DELETE CASCADE" +
									");";

		this.executeUpdate(def);  
	}  
  
	private void createTableEmail() {      
		String def = "CREATE TABLE email(" +
									"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"type INTEGER, " + 
									"email TEXT, "  +
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id) ON DELETE CASCADE" +
									");";

		this.executeUpdate(def);   
	}  
  
	private void createTableCustom() {      
		String def = "CREATE TABLE custom(" +
									"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"name TEXT, " + 
									"value TEXT, " +        
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id) ON DELETE CASCADE" +
									");";

		this.executeUpdate(def);   
	} 	
	
	private void commitChanges() {
		try {
			this.conn.commit();
		}
		catch (SQLException e) {
			System.err.println("Commit failed" + e);
		}
	}
  	
  // INSERT, UPDATE, DELETE actions  / internal modifier for testing
	int executeUpdate(String query) {
		int result;

		try {
			System.out.println(query);
			Statement stmt = this.conn.createStatement();

			result = stmt.executeUpdate(query);
		} 
		catch (SQLException e) {
			System.err.println(e.toString());
			result = -1;
		}  

		System.out.println("Result: " + result);
		return result;
	}
  
  // SELECT action / internal modifier for testing
	ResultSet executeQuery(String query) {
		try {
			Statement stmt = this.conn.createStatement();

			return stmt.executeQuery(query);
		}
		catch (SQLException e) {
			return null;
		}  
	}

	@Override
	public List<Group> getAllGroups() {
		System.out.println("getAllGroups");
		System.out.println("------------------");
		return this.groups;
	}

	@Override
	public List<Contact> getAllContacts() {
		System.out.println("getAllContacts");
		System.out.println("------------------");		
		return this.contacts;				
	}
	
	@Override
	public List<Contact> getSpecificContacts(List<Integer> ids) {
		System.out.println("getSpecificContacts");
		System.out.println("------------------");				
		return this.filterContacts(ids);
	}
	
	@Override
	public List<Contact> getAllContactsFromGroup(int groupId) {
		System.out.println("getAllContactsFromGroup");
		System.out.println("------------------");				
		return this.filterContacts(groupId);		
	}
	
	private List<Contact> filterContacts (List<Integer> ids) {
		List<Contact> specificContacts = new ArrayList<Contact>();
		
		for (Contact contact : contacts)
		{
			if (ids.contains(contact.getId()))
			{
				specificContacts.add(contact);
			}
		}		
		
		return specificContacts;
	}
	
	private List<Contact> filterContacts (int groupId) {
		
		List<GroupContact> relevantGroups = new ArrayList<GroupContact>();
		
		for (GroupContact groupCont : this.groupContacts) {
			if (groupCont.getGroupId() == groupId) {
				relevantGroups.add(groupCont);
			}
		}
		
		List<Contact> specificContacts = new ArrayList<Contact>();
		
		for (GroupContact gc : relevantGroups) {			
			for (Contact contact : this.contacts) {
				if (gc.getContactId() == contact.getId()) {
					specificContacts.add(contact);
				}
			}
		}		
		
		return specificContacts;
	}	
			
	private void loadDataFromDb()	{
		List<Contact> contacts  = this.loadContacts();
		this.groups = this.loadGroups();
		this.groupContacts = this.loadGroupContacts();
		
		List<Email> emails = this.loadEmails();
		List<Url> urls = this.loadUrls();
		List<Custom> customs  = this.loadCustoms();
		List<PhoneNumber> phones = this.loadPhones();
		List<Messenger> messengers = this.loadMessengers();
		List<Adress> adresses = this.loadAdresses();
		
		for (Contact contact : contacts) {
			contact.setEmails(this.filterEmails(emails, contact.getId()));
			contact.setUrls(this.filterUrls(urls, contact.getId()));
			contact.setCustoms(this.filterCustoms(customs, contact.getId()));
			contact.setPhoneNumbers(this.filterPhoneNumbers(phones, contact.getId()));
			contact.setMessenger(this.filterMessengers(messengers, contact.getId()));
			contact.setAdresses(this.filterAdresses(adresses, contact.getId()));			
		}	
		
		this.contacts = contacts;
	}
	
	private List<Group> loadGroups() {
	
		List<Group> groups = new ArrayList<Group>();

		ResultSet rs = this.executeQuery("SELECT * FROM category;");

		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				groups.add(new Group(id, name));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllGroups failed");
			return null;
		}

		return groups;		
	}	
		
	private List<GroupContact> loadGroupContacts()	{
		ResultSet rs = this.executeQuery("SELECT * FROM groupContact;");
		
		List<GroupContact> groupContacts = new ArrayList<GroupContact>();
		
		try {
			while (rs.next()) {
				int contactId = rs.getInt("contactId");				
				int groupId = rs.getInt("groupId");
				

				groupContacts.add(new GroupContact(groupId, contactId));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllContactsxxx failed");
			return null;
		}
		
		return groupContacts;				
	}				
	
	private List<Adress> loadAdresses()	{
		ResultSet rs = this.executeQuery("SELECT * FROM adress;");
		
		List<Adress> adresses = new ArrayList<Adress>();
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");				
				int type = rs.getInt("type");
				String street = rs.getString("street");
				int number = rs.getInt("number");	
				String city = rs.getString("city");		
				int postCode = rs.getInt("postCode");			
				String country = rs.getString("country");		
				int contactId = rs.getInt("contactId");							

				adresses.add(new Adress(id, type, street, number, city, postCode, country, contactId));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllContactsxxx failed");
			return null;
		}
		
		return adresses;				
	}				
	
	private List<Messenger> loadMessengers()	{
		ResultSet rs = this.executeQuery("SELECT * FROM messenger;");
		
		List<Messenger> messengers = new ArrayList<Messenger>();
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");				
				int type = rs.getInt("type");
				String value = rs.getString("value");
				int contactId = rs.getInt("contactId"); 				

				messengers.add(new Messenger(id, type, value, contactId));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllContactsxxx failed");
			return null;
		}
		
		return messengers;				
	}				
	
	private List<Custom> loadCustoms() {
		ResultSet rs = this.executeQuery("SELECT * FROM custom;");
		
		List<Custom> customs = new ArrayList<Custom>();
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");				
				String name = rs.getString("name");
				String value = rs.getString("value");
				int contactId = rs.getInt("contactId"); 				

				customs.add(new Custom(id, name, value, contactId));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllContactsxxx failed");
			return null;
		}
		
		return customs;				
	}		
	
	private List<PhoneNumber> loadPhones()	{
		ResultSet rs = this.executeQuery("SELECT * FROM phoneNumber;");
		
		List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");				
				int type = rs.getInt("type");
				String number = rs.getString("number");
				int contactId = rs.getInt("contactId"); 				

				phoneNumbers.add(new PhoneNumber(id, type, number, contactId));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllContactsxxx failed");
			return null;
		}
		
		return phoneNumbers;				
	}	
	
	private List<Contact> loadContacts() {
		ResultSet rs = this.executeQuery("SELECT * FROM contact;");
		
		List<Contact> contacts = new ArrayList<Contact>();
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");				
				String firstName = rs.getString("firstName");
				String surName = rs.getString("surName");
				String nickName = rs.getString("nickName");   
				String note = rs.getString("note"); 				

				contacts.add(new Contact(id, firstName, surName, nickName, note));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllContactsxxx failed");
			return null;
		}
		
		return contacts;				
	}
	
	private List<Url> loadUrls() {
		ResultSet rs = this.executeQuery("SELECT * FROM url;");
		
		List<Url> urls = new ArrayList<Url>();
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");				
				int type = rs.getInt("type");
				String value = rs.getString("value");
				int contactId = rs.getInt("contactId");   
			
				urls.add(new Url(id, type, value, contactId));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllContactsxxx failed");
			return null;
		}
		catch (MalformedURLException e)
		{
			// TODO
		}
		
		return urls;					
	}
	
	private List<Email> loadEmails() {
		ResultSet rs = this.executeQuery("SELECT * FROM email;");
		
		List<Email> emails = new ArrayList<Email>();
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");				
				int type = rs.getInt("type");
				String email = rs.getString("email");
				int contactId = rs.getInt("contactId");   
			
				emails.add(new Email(id, type, email, contactId));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllContactsxxx failed");
			return null;
		}
		
		return emails;				
	}	
	
	private List<Email> filterEmails(List<Email> emails, int contactId) {

		List<Email> contactEmails = new ArrayList<Email>();
		for (Email e : emails) {
			if (e.getContactId() == contactId)
			{
				contactEmails.add(e);
			}
		}
		
		return contactEmails;
	}	
	
	private List<Url> filterUrls(List<Url> urls, int contactId) {

		List<Url> contactUrls = new ArrayList<Url>();
		for (Url e : urls) {
			if (e.getContactId() == contactId)
			{
				contactUrls.add(e);
			}
		}
		
		return contactUrls;
	}		
	
	private List<Custom> filterCustoms(List<Custom> urls, int contactId) {

		List<Custom> contactCustoms = new ArrayList<Custom>();
		for (Custom c : urls) {
			if (c.getContactId() == contactId)
			{
				contactCustoms.add(c);
			}
		}
		
		return contactCustoms;
	}		
	
	private List<PhoneNumber> filterPhoneNumbers(List<PhoneNumber> urls, int contactId) {

		List<PhoneNumber> contactPhones = new ArrayList<PhoneNumber>();
		for (PhoneNumber c : urls) {
			if (c.getContactId() == contactId)
			{
				contactPhones.add(c);
			}
		}
		
		return contactPhones;
	}		
	
	private List<Messenger> filterMessengers(List<Messenger> urls, int contactId) {

		List<Messenger> contactMessengers = new ArrayList<Messenger>();
		for (Messenger c : urls) {
			if (c.getContactId() == contactId)
			{
				contactMessengers.add(c);
			}
		}
		
		return contactMessengers;
	}			
	
	private List<Adress> filterAdresses(List<Adress> urls, int contactId) {

		List<Adress> contactAdresses = new ArrayList<Adress>();
		for (Adress c : urls) {
			if (c.getContactId() == contactId)
			{
				contactAdresses.add(c);
			}
		}
		
		return contactAdresses;
	}
}
