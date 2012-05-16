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
		
	private final String FILENAME = new File(Settings.instance().getDataDir(), "contacts").toString();
				
	private ArrayList<Contact> contacts = null;

	public DatabaseContacts() {		
		this.load();
		this.setLastIdNumber();
	}	
	
	void addNew(List<Contact> newContacts) {
		
		for (Contact contact: newContacts) {
      this.removeEmptyValues(contact);
			contact.id = ++this.idCounter;
			this.contacts.add(contact);
		}			
	}

	void update(Contact contact) {
    this.removeEmptyValues(contact);    
		Contact updatedContact = this.filterItem(contact.getId());
		int index = this.contacts.indexOf(updatedContact);

		if (index != -1) {
			this.contacts.set(index, contact);
		}			
	}
  
  /**
   * Removes empty entries from contact.
   * @param contact
   */
  private void removeEmptyValues(Contact contact) {
       
    /*
     * TODO
        private List<Messenger> messenger;
        private List<Custom> customs;
     */        
    
    List<Event> datesToRemove = new ArrayList<Event>();        
    List<Address> adressesToRemove = new ArrayList<Address>();
    List<Email> emailsToRemove = new ArrayList<Email>();    
    List<Url> urlsToRemove = new ArrayList<Url>();   
    List<PhoneNumber> phonesToRemove = new ArrayList<PhoneNumber>();           
    
    for (Event event : contact.getDates()) {
      if (event.getDate() == null) {
        datesToRemove.add(event);
      }
    }        
    
    for (Address addr : contact.getAdresses()) {
      if (addr.getAddress().isEmpty()) {
        adressesToRemove.add(addr);
      }
    }
    
    for (Url url : contact.getUrls()) {
      if (url.getValue() == null) {
        urlsToRemove.add(url);
      }
    }      
    
    for (Email email : contact.getEmails()) {
      if (email.getEmail().isEmpty()) {
        emailsToRemove.add(email);
      }
    }  
    
    for (PhoneNumber phone : contact.getPhoneNumbers()) {
      if (phone.getNumber().isEmpty()) {
        phonesToRemove.add(phone);
      }
    }        
    
    contact.getDates().removeAll(datesToRemove);    
    contact.getAdresses().removeAll(adressesToRemove);
    contact.getEmails().removeAll(emailsToRemove);   
    contact.getUrls().removeAll(urlsToRemove);       
    contact.getPhoneNumbers().removeAll(phonesToRemove);      
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
