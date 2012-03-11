package cz.vutbr.fit.gja.gjaddr.persistancelayer;

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
  
	private Database() { 
		try {
			Class.forName("org.sqlite.JDBC");

			// create a database, or open an existing database, in the sketch data folder:      
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + this.DB_NAME);

			//this.conn.setAutoCommit(false);
		}
		catch (ClassNotFoundException e) {
			System.err.println(e);
		}
		catch (SQLException e) {
			System.err.println(e);
		}

		// TODO - decide if use old DB or use new
		this.createEmptyDb();

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
		this.createTableMessenger();    
		this.createTableUrl();   
		this.createTableAdress(); 
		this.createTablePhoneNumber();
		this.createTableEmail();
		this.createTableCustom();  
		this.createTableGroup();
	}

	private void cleanUpDatabase()
  {
    String[] tables = { "custom", "email", "phoneNumber", "adress",
                         "url", "messenger", "contact", "category" };     
    
    System.out.println("Cleaning up db ...");       
   
    for (int i = 0; i < tables.length; i++)
    {
      // this.executeUpdate("DROP TABLE IF EXISTS " + tables[i]);  
      this.executeUpdate("DROP TABLE " + tables[i]);
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
		
	private void createTableMessenger() {      
		String def = "CREATE TABLE messenger(" +
									"Id INTEGER PRIMARY KEY, " + 
									"Type INTEGER, " + 
									"Value TEXT, "  +
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id)" +
									");";

		this.executeUpdate(def);   
	} 
  
	private void createTableUrl() {      
		String def = "CREATE TABLE url(" +
									"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"type INTEGER, " + 
									"value TEXT, "  +
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id)" +
									");";

		this.executeUpdate(def);   
	}
  
	private void createTableAdress() {      
		String def = "CREATE TABLE adress(" +
									"id INTEGER PRIMARY KEY, " + 
									"type INTEGER, " + 
									"street TEXT, " + 
									"number INTEGER, " + 
									"city TEXT, " +             
									"postCode INTEGER, "  +
									"country TEXT, "  +            
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id)" +
									");";

		this.executeUpdate(def);   
	}    
      
	private void createTablePhoneNumber() {      
		String def = "CREATE TABLE phoneNumber(" +
									"id INTEGER PRIMARY KEY, " + 
									"type INTEGER, " + 
									"number INTEGER, "  +
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id)" +
									");";

		this.executeUpdate(def);   
	}  
  
	private void createTableEmail() {      
		String def = "CREATE TABLE email(" +
									"id INTEGER PRIMARY KEY, " + 
									"type INTEGER, " + 
									"email TEXT, "  +
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id)" +
									");";

		this.executeUpdate(def);   
	}  
  
	private void createTableCustom() {      
		String def = "CREATE TABLE custom(" +
									"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
									"name TEXT, " + 
									"value TEXT, " +        
									"contactId INTEGER NOT NULL, " +                   
									"FOREIGN KEY(contactId) REFERENCES contact(id)" +
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
			result = -1;
		}  

		System.out.println("Result: " + result);
		return result;
	}
  
  // SELECT action / internal modifier for testing
	ResultSet executeQuery(String query) {
		try {
			System.out.println(query);
			Statement stmt = this.conn.createStatement();

			return stmt.executeQuery(query);
		}
		catch (SQLException e) {
			return null;
		}  
	}

	@Override
	public List<Contact> getAllContacts() {
		List<Contact> contacts = new ArrayList<Contact>();

		ResultSet rs = this.executeQuery("SELECT c.firstName, c.surName, c.nickName, u.value "
																			+ "FROM contact c, url u "
																			+ "WHERE c.id = u.contactId;");   
		try {
			while (rs.next()) {
				String firstName = rs.getString("firstName");
				String surName = rs.getString("surName");
				String nickName = rs.getString("nickName");   

				contacts.add(new Contact(firstName, surName, nickName));
			}
		}
		catch (SQLException e) {
			System.err.println("getAllContacts failed");
			return null;
		}

		return contacts;
	}
 
	@Override
	public List<Group> getAllGroups() {
	List<Group> groups = new ArrayList<Group>();

	ResultSet rs = this.executeQuery("SELECT * FROM category");
	
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
}
