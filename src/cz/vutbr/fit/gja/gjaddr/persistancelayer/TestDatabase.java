package cz.vutbr.fit.gja.gjaddr.persistancelayer;

/**
 * Class with testing data.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class TestDatabase {
	public static void fillTestingData(Database db)	{ 
		System.out.println("Fill db ...");

		fillContacts(db);
		fillGroups(db);		
		fillUrls(db);
		fillEmails(db);
		fillPhoneNumbers(db);
		fillMessengers(db);
		fillAdresses(db);
		fillCustom(db);
		assignContactsToGroup(db);
	}

	private static void fillContacts(Database db) {
		db.executeUpdate("INSERT INTO contact (firstName, surName, nickName) VALUES ('Radek', 'Gajdusek', 'Speedy');");	
		db.executeUpdate("INSERT INTO contact (firstName, surName, nickName, note) VALUES ('Jan', 'Kaláb', 'Pitel', 'pokusná poznámka');");			
		db.executeUpdate("INSERT INTO contact (firstName, nickName) VALUES ('Petr', 'Macháček');");			
	}
	
	private static void fillGroups(Database db)  {
		db.executeUpdate("INSERT INTO category (name) VALUES ('Lahvators');"); 
		db.executeUpdate("INSERT INTO category (name) VALUES ('Fit');");     
		db.executeUpdate("INSERT INTO category (name) VALUES ('DPMB');");   
		db.executeUpdate("INSERT INTO category (name) VALUES ('Test');");   
	}

	private static void fillUrls(Database db) {
		db.executeUpdate("INSERT INTO url (type, value, contactId) VALUES (1, 'http://www.lahvators.cz', 1);");
		db.executeUpdate("INSERT INTO url (type, value, contactId) VALUES (2, 'http://www.seznam.cz', 3);");
		db.executeUpdate("INSERT INTO url (type, value, contactId) VALUES (2, 'http://www.idos.cz', 3);");		
	}
	
	private static void fillEmails(Database db)  {
		db.executeUpdate("INSERT INTO email (type, email, contactId) VALUES (1, 'test@gmail.com', 1);"); 
		db.executeUpdate("INSERT INTO email (type, email, contactId) VALUES (1, 'pokus@gmail.com', 1);");   
		db.executeUpdate("INSERT INTO email (type, email, contactId) VALUES (2, 'pokus@centrum.cz', 2);");		
		db.executeUpdate("INSERT INTO email (type, email, contactId) VALUES (2, 'pokus@seznam.cz', 3);"); 
	}
	
	private static void fillPhoneNumbers(Database db) {
		db.executeUpdate("INSERT INTO phoneNumber (type, number, contactId) VALUES (1, '420582978653', 1);"); 
		db.executeUpdate("INSERT INTO phoneNumber (type, number, contactId) VALUES (2, '420658987562', 2);"); 
		db.executeUpdate("INSERT INTO phoneNumber (type, number, contactId) VALUES (1, '420654789369', 1);"); 
		db.executeUpdate("INSERT INTO phoneNumber (type, number, contactId) VALUES (2, '420587978652', 2);"); 		
	}
	
	private static void fillMessengers(Database db) {
		db.executeUpdate("INSERT INTO messenger (type, value, contactId) VALUES (1, 'ragaj@jabber.org', 1);"); 
		db.executeUpdate("INSERT INTO messenger (type, value, contactId) VALUES (2, '278456123', 1);"); 
		db.executeUpdate("INSERT INTO messenger (type, value, contactId) VALUES (1, 'test@jabbim.cz', 2);"); 
		db.executeUpdate("INSERT INTO messenger (type, value, contactId) VALUES (2, '147896321', 2);"); 		
	}	

	private static void fillAdresses(Database db) {
		db.executeUpdate("INSERT INTO adress (type, street, number, city, postCode, contactId) "
						        + "VALUES (1, 'Osvoboditelů', 44, 'Kopřivnice', 74221, 1);"); 
		db.executeUpdate("INSERT INTO adress (type, street, number, city, postCode, contactId) "
						        + "VALUES (1, 'Masarykova', 133, 'Brno', 61200, 2);"); 
		db.executeUpdate("INSERT INTO adress (type, street, number, city, postCode, contactId) "
						        + "VALUES (2, 'Sportovní', 1207, 'Nové Město na Moravě', 45879, 2);"); 
	}	

	private static void fillCustom(Database db) {
		db.executeUpdate("INSERT INTO custom (name, value, contactId) VALUES ('Hokejista', 'ano', 1);"); 
	}		
	
	private static void assignContactsToGroup(Database db) {
		db.executeUpdate("INSERT INTO groupContact (contactId, groupId) VALUES (1, 1);"); 
		db.executeUpdate("INSERT INTO groupContact (contactId, groupId) VALUES (2, 1);"); 
		db.executeUpdate("INSERT INTO groupContact (contactId, groupId) VALUES (2, 3);"); 		
	}			
}
