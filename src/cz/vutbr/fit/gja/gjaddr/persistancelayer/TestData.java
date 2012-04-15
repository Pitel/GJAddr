package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class with testing data.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class TestData {
	public static void fillTestingData(Database db)	{ 
		fillContacts(db);
		fillGroups(db);		
		fillGroupContacts(db);
	}

	private static void fillContacts(Database db) {
		
		List<Contact> contactsToAdd = new ArrayList<Contact>();		
		
		List<Email> emails = new ArrayList<Email>();
		List<PhoneNumber> phones = new ArrayList<PhoneNumber>();		
		List<Url> urls = new ArrayList<Url>();			
		List<Messenger> messengers = new ArrayList<Messenger>();		
		List<Address> adresses = new ArrayList<Address>();			
		List<Custom> customs = new ArrayList<Custom>();			
						
		Contact contact1 = new Contact("Radek", "Gajdusek", "Speedy", null);
		contact1.setDateOfBirth(new Date(1988 - 1900, 03, 15));
		emails.add(new Email(1, "test@gmail.com"));
		emails.add(new Email(1, "pokus@gmail.com"));
		contact1.setEmails(new ArrayList<Email>(emails));
		phones.add(new PhoneNumber(1, "420582978653"));
		phones.add(new PhoneNumber(2, "420654789369"));		
		contact1.setPhoneNumbers(new ArrayList<PhoneNumber>(phones));
		urls.add(new Url(1, "http://www.lahvators.cz"));
		contact1.setUrls(new ArrayList<Url>(urls));
		messengers.add(new Messenger(1, "ragaj@jabber.org"));
		messengers.add(new Messenger(2, "278456123"));
		contact1.setMessenger(new ArrayList<Messenger>(messengers));
		customs.add(new Custom("test", "123"));
		contact1.setCustoms(new ArrayList<Custom>(customs));
		adresses.add(new Address(1, "Osvoboditelů 44, Kopřivnice, 74221, Czech republic"));
		contact1.setAdresses(new ArrayList<Address>(adresses));		
		contactsToAdd.add(contact1);
		
		emails.clear();
		phones.clear();
		urls.clear();
		messengers.clear();
		adresses.clear();
		customs.clear();
		
		Contact contact2 = new Contact("Jan", "Kaláb", "Pitel", "pokusná poznámka");
		contact2.setDateOfBirth(new Date(1987 - 1900, 04, 01));
		emails.add(new Email(2, "pokus@centrum.cz"));
		contact2.setEmails(new ArrayList<Email>(emails));						
		phones.add(new PhoneNumber(1, "420658987562"));
		phones.add(new PhoneNumber(2, "420587978652"));		
		contact2.setPhoneNumbers(new ArrayList<PhoneNumber>(phones));		
		adresses.add( new Address(1, "Masarykova 133, Brno, 61200"));
		contact2.setAdresses(new ArrayList<Address>(adresses));
		contactsToAdd.add(contact2);
		
		emails.clear();
		phones.clear();
		urls.clear();
		messengers.clear();
		adresses.clear();
		customs.clear();		
		
		Contact contact3 = new Contact("Petr", "Macháček", null, null);
		contact3.setDateOfBirth(new Date(1980 - 1900, 03, 25));
		urls.add(new Url(2, "http://www.seznam.cz"));
		urls.add(new Url(2, "http://www.idos.cz"));		
		contact3.setUrls(new ArrayList<Url>(urls));		
		messengers.add(new Messenger(1, "test@jabbim.cz"));
		messengers.add(new Messenger(2, "147896321"));
		contact3.setMessenger(new ArrayList<Messenger>(messengers));		
		Email email4 = new Email(2, "pokus@seznam.cz");	
		emails.add(new Email(2, "pokus@seznam.cz"));
		contact3.setEmails(new ArrayList<Email>(emails));
		adresses.add(new Address(2, "Sportovní 1207, Nové Město na Moravě, 45879, Slovensko"));
		contact3.setAdresses(new ArrayList<Address>(adresses));			
		contactsToAdd.add(contact3);
		
		db.addNewContacts(contactsToAdd);
	}
	
	private static void fillGroups(Database db)  {
		db.addNewGroup("Lahvators");
		db.addNewGroup("Fit");
		db.addNewGroup("DPMB");
		db.addNewGroup("Test");		
	}		
	
	private static void fillGroupContacts(Database db) {
		List<Integer> contactsId = new ArrayList<Integer>();
		
		List<Contact> contacts = db.getAllContacts();
		List<Group> groups = db.getAllGroups();
	
		List<Contact> contactsToAdd = new ArrayList<Contact>();
		contactsToAdd.add(contacts.get(0));
		contactsToAdd.add(contacts.get(1));		
		
		db.addContactsToGroup(groups.get(0), contactsToAdd);
		
		contactsToAdd.clear();
		contactsToAdd.add(contacts.get(1));
		contactsToAdd.add(contacts.get(2));	
		db.addContactsToGroup(groups.get(1), contactsToAdd);		
		
		contactsToAdd.clear();
		contactsToAdd.add(contacts.get(2));
		db.addContactsToGroup(groups.get(3), contactsToAdd);				
	}

	private static void fillAuthTokens(Database db) {
		AuthToken fb = new AuthToken(ServicesEnum.FACEBOOK, "sdjasjdhajkdha-82y1289");
		AuthToken go = new AuthToken(ServicesEnum.GOOGLE, "jadskalja2kejk2ejklejl1e");
		db.addToken(fb);
		db.addToken(go);
	}
}
