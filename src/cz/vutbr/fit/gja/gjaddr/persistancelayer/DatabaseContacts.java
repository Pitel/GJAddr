package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with contacts collection.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class DatabaseContacts {

	private int idCounter = 0;
		
	private final String FILENAME = new File(Settings.getDataDir(), "contacts").toString();
				
	private ArrayList<Contact> contacts = null;

	public DatabaseContacts() {		
		this.load();
		this.setLastIdNumber();
	}	
	
	void addNew(List<Contact> newContacts) {
		
		for (Contact contact: newContacts) {
			contact.id = ++this.idCounter;
			this.contacts.add(contact);
		}			
	}

	void update(Contact contact) {
		Contact updatedContact = this.filterItem(contact.getId());
		int index = this.contacts.indexOf(updatedContact);

		if (index != -1) {
			this.contacts.set(index, contact);
		}			
	}
	
	void remove(List<Contact> contactsToRemove) {		
		this.contacts.removeAll(contactsToRemove);
	}	
	
	public List<Contact> getAllContacts() {
		return new ArrayList<Contact>(this.contacts);
	}
	
	private void load()	{		
    Persistance per = new Persistance();    
		this.contacts = (ArrayList<Contact>) per.loadData(FILENAME);	
	}
	
  void save()	{		
    Persistance per = new Persistance();    
		per.saveData(FILENAME, this.contacts);
	}
	
	private void setLastIdNumber() {
		
		int counter = 0;
		
		for (Contact contact: this.contacts) {
			int id = contact.getId();
			if (id > counter) {
				counter = id;
			}
		}
		
		this.idCounter = counter;
	}
	
	private Contact filterItem(int id) {
		for (Contact contact : this.contacts) {			
			if (contact.getId() == id) 
				return contact;
			}

		return null;
	}	
	
	void clear() {
		this.contacts.clear();
		this.idCounter = 0;
	}
	
	List<Contact> filter(List<Contact> requiredContacts) {
		
		List<Contact> filteredContacts = new ArrayList<Contact>();
		
		for (Contact contact : this.contacts) {
			
			if (requiredContacts.contains(contact))
				filteredContacts.add(contact);
			}

		return filteredContacts;
	}		
	
	List<Contact> filterByIds(List<Integer> requiredContactsId) {
		
		List<Contact> filteredContacts = new ArrayList<Contact>();
		
		for (Contact contact : this.contacts) {
			
			if (requiredContactsId.contains(contact.getId()))
				filteredContacts.add(contact);
			}

		return filteredContacts;
	}			
}
