package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.tables.GroupContact;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with group contacts relationship.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class DatabaseGroupsContacts {

	private final String FILENAME = "groupContacts.gja";
	private List<GroupContact> groupsContacts = null;

	public DatabaseGroupsContacts() {		
		this.load();
	}	
	
	public List<GroupContact> getAllRelationships() {
		return groupsContacts;
	}
	
	private void load()	{
		
		this.groupsContacts = null;
		
		if ((new File(FILENAME)).exists()) {
			try {
				FileInputStream flinpstr = new FileInputStream(FILENAME);
				ObjectInputStream objinstr= new ObjectInputStream(flinpstr);

				try {	
					this.groupsContacts = (List<GroupContact>) objinstr.readObject(); 
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
		
		if (this.groupsContacts == null) {
			this.groupsContacts = new ArrayList<GroupContact>();
		}			
	}
	
	void save()	{
		
		if (this.groupsContacts == null || this.groupsContacts.isEmpty()) {
			return;
		}
		
		try {
			FileOutputStream flotpt = new FileOutputStream(FILENAME);
			ObjectOutputStream objstr= new ObjectOutputStream(flotpt);
			
			try {
				objstr.writeObject(this.groupsContacts); 
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
	
//	List<Contact> filter(List<Integer> reguiredIdList) {
//		
//		List<Contact> filteredContacts = new ArrayList<Contact>();
//		
//		for (Contact contact : this.contacts) {
//			
//			if (reguiredIdList.contains(contact.getId()))
//				filteredContacts.add(contact);
//			}
//
//		return filteredContacts;
//	}	
}
