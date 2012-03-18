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
	
	private final String FILENAME = "contacts.gja";
	private List<Contact> contacts = null;

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
	
	void remove(List<Integer> contactsId) {
		
		List<Contact> contactsToRemove = this.filter(contactsId);		
		this.contacts.removeAll(contactsToRemove);
	}	
	
	public List<Contact> getAllContacts() {
		return contacts;
	}
	
	private void load()	{
		
		this.contacts = null;
		
		if ((new File(FILENAME)).exists()) {
			try {
				FileInputStream flinpstr = new FileInputStream(FILENAME);
				ObjectInputStream objinstr= new ObjectInputStream(flinpstr);

				try {	
					this.contacts = (List<Contact>) objinstr.readObject(); 
				} 
				finally {
					try {
						objinstr.close();
					} 
					finally {
						flinpstr.close();
					}
				}
			} 
			catch(IOException ioe) {
				ioe.printStackTrace();
			} 
			catch(ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
		}		
		
		// create empty DB
		if (this.contacts == null) {
			this.contacts = new ArrayList<Contact>();
		}			
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
	
	void save()	{
		
		if (this.contacts == null || this.contacts.isEmpty()) {
			return;
		}
		
		try {
			FileOutputStream flotpt = new FileOutputStream(FILENAME);
			ObjectOutputStream objstr= new ObjectOutputStream(flotpt);
			
			try {
				objstr.writeObject(this.contacts); 
				objstr.flush();
			} 
			finally {				
				try {
					objstr.close();
				} 
				finally {
					flotpt.close();
				}
			}
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
		}		
	}
	
	private Contact filterItem(int id) {
		for (Contact contact : this.contacts) {			
			if (contact.getId() == id) 
				return contact;
			}

		return null;
	}		
	
	List<Contact> filter(List<Integer> reguiredIdList) {
		
		List<Contact> filteredContacts = new ArrayList<Contact>();
		
		for (Contact contact : this.contacts) {
			
			if (reguiredIdList.contains(contact.getId()))
				filteredContacts.add(contact);
			}

		return filteredContacts;
	}		
}
