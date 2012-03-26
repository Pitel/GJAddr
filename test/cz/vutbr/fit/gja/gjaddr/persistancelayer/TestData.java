package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.util.ArrayList;
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
		
		List<Email> emails = new ArrayList<Email>();
		List<PhoneNumber> phones = new ArrayList<PhoneNumber>();		
		List<Url> urls = new ArrayList<Url>();			
		List<Messenger> messengers = new ArrayList<Messenger>();		
		List<Address> adresses = new ArrayList<Address>();			
		List<Custom> customs = new ArrayList<Custom>();			
						
		Contact contact1 = new Contact("Radek", "Gajdusek", "Speedy", null);
		
		Email email1 = new Email(1, "test@gmail.com");
		Email email2 = new Email(1, "pokus@gmail.com");
		emails.add(email1);
		emails.add(email2);
		contact1.setEmails(emails);
		
		PhoneNumber phone1 = new PhoneNumber(1, "420582978653");
		PhoneNumber phone2 = new PhoneNumber(2, "420654789369");		
		phones.add(phone1);
		phones.add(phone2);		
		contact1.setPhoneNumbers(phones);
		
		Url url1 = new Url(1, "http://www.lahvators.cz");		
		urls.add(url1);
		contact1.setUrls(urls);
		
		Messenger mess1 = new Messenger(1, "ragaj@jabber.org");
		Messenger mess2 = new Messenger(2, "278456123");		
		messengers.add(mess1);
		messengers.add(mess2);
		contact1.setMessenger(messengers);
		
		Custom custom = new Custom("test", "123");
		customs.add(custom);
		contact1.setCustoms(customs);
		
		Address adress1 = new Address(1, "Osvoboditelů", 44, "Kopřivnice", 74221, "Czech republic");
		adresses.add(adress1);
		contact1.setAdresses(adresses);
		
		emails.clear();
		phones.clear();
		urls.clear();
		messengers.clear();
		adresses.clear();
		customs.clear();
		
		Contact contact2 = new Contact("Jan", "Kaláb", "Pitel", "pokusná poznámka");
		
		Email email3 = new Email(2, "pokus@centrum.cz");		
		emails.add(email3);
		contact2.setEmails(emails);		
		
		PhoneNumber phone3 = new PhoneNumber(1, "420658987562");
		PhoneNumber phone4 = new PhoneNumber(2, "420587978652");			
		phones.add(phone3);
		phones.add(phone4);		
		contact2.setPhoneNumbers(phones);		
		
		Address adress2 = new Address(1, "Masarykova", 133, "Brno", 61200, null);	
		adresses.add(adress2);
		contact2.setAdresses(adresses);
		
		emails.clear();
		phones.clear();
		urls.clear();
		messengers.clear();
		adresses.clear();
		customs.clear();		
		
		Contact contact3 = new Contact("Petr", "Macháček", null, null);
		
		Url url2 = new Url(2, "http://www.seznam.cz");
		Url url3 = new Url(2, "http://www.idos.cz");
		urls.add(url2);
		urls.add(url3);		
		contact3.setUrls(urls);		
		
		Messenger mess3 = new Messenger(1, "test@jabbim.cz");
		Messenger mess4 = new Messenger(2, "147896321");			
		messengers.add(mess3);
		messengers.add(mess4);
		contact3.setMessenger(messengers);		

		Email email4 = new Email(2, "pokus@seznam.cz");	
		emails.add(email4);
		contact3.setEmails(emails);
		
		Address adress3 = new Address(2, "Sportovní", 1207, "Nové Město na Moravě", 45879, "Slovensko");	
		adresses.add(adress3);
		contact3.setAdresses(adresses);
				
		
		List<Contact> contactsToAdd = new ArrayList<Contact>();
		contactsToAdd.add(contact1);
		contactsToAdd.add(contact2);
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
}
