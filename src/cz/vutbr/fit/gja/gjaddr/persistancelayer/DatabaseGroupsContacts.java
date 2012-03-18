package cz.vutbr.fit.gja.gjaddr.persistancelayer;

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
	
	// TODO check if is the number currently not in the list
	void addContactsToGroup(int groupId, List<Integer> contactsIds) {		
		for (int contactId : contactsIds) {
			GroupContact gc = new GroupContact(groupId, contactId);
			this.groupsContacts.add(gc);
		}
	}
	
	void removeContactsFromGroup(int groupId, List<Integer> contactsIds) {
		
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

	List<Integer> filterByGroupId(int id) {
		return this.filter(true, id);
	}
	
	List<Integer> filterByContactId(int id) {
		return this.filter(false, id);
	}	
	
	private List<Integer> filter(boolean filterGroups, int id) {

		List<Integer> filteredRecords = new ArrayList<Integer>();

		for (GroupContact groupContact : this.groupsContacts) {
			int groupId = groupContact.getGroupId();
			int contactId = groupContact.getContactId();			
			
			if (filterGroups) {
				if (groupId == id) {
					filteredRecords.add(contactId);
				}
			}
			else {
				if (contactId == id) {
					filteredRecords.add(groupId);
				}				
			}			
		}	

		return filteredRecords;
	}	
}
