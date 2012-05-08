package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with group contacts relationship.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class DatabaseGroupsContacts {

	private final String FILENAME = new File(Settings.getDataDir(), "groupsContacts").toString();
	private List<GroupContact> groupsContacts = null;

	public DatabaseGroupsContacts() {		
		this.load();
	}	
	
	// TODO check if is the number currently not in the list
	void addContactsToGroup(Group group, List<Contact> contactsToAdd) {		
		for (Contact contact : contactsToAdd) {
			GroupContact gc = new GroupContact(group.getId(), contact.getId());
			this.groupsContacts.add(gc);
		}
	}
	
	void updateGroupsContacts(List<Group> groups, List<Contact> contacts) {
		this.removeGroupsEntries(groups);
		for (Group group : groups) {
			this.addContactsToGroup(group, contacts);
		}
	}	
	
	void updateContactsGroups(List<Contact> contacts, List<Group> groups) {
		this.removeContactsEntries(contacts);
		for (Group group : groups) {
			this.addContactsToGroup(group, contacts);
		}
	}
	
	void removeContactsFromGroup(Group group, List<Contact> contactsToRemove) {
		List<GroupContact> entriesToRemove = new ArrayList<GroupContact>();		
		List<Integer> contactsToRemoveIds = new ArrayList<Integer>();
		
		for (Contact contact: contactsToRemove) {
			contactsToRemoveIds.add(contact.getId());
		}
		
		List<Integer> contactsFromGroup = this.filterByGroupId(group.getId());
		
		for (GroupContact gc: this.groupsContacts) {
			int contactId = gc.getContactId();
			
			// remove all entries, that are:
			// 1. assign to the required group
			// 2. delete is required as a second parameter
			if (contactsFromGroup.contains(contactId) && contactsToRemoveIds.contains(contactId)) {
				entriesToRemove.add(gc);
			}
		}
		
		this.groupsContacts.removeAll(entriesToRemove);
	}
	
	void removeContactsEntries(List<Contact> contactsToRemove) {
		List<GroupContact> entriesToRemove = new ArrayList<GroupContact>();
		for (Contact contact: contactsToRemove) {
			for (GroupContact gc: this.groupsContacts) {
				if (gc.getContactId() == contact.getId()) {
					entriesToRemove.add(gc);
				}
			}
		}
		
		this.groupsContacts.removeAll(entriesToRemove);
	}
	
	void removeGroupsEntries(List<Group> groupsToRemove) {
		List<GroupContact> entriesToRemove = new ArrayList<GroupContact>();		
		for (Group group: groupsToRemove) {
			for (GroupContact gc: this.groupsContacts) {
				if (gc.getGroupId() == group.getId()) {
					entriesToRemove.add(gc);
				}
			}
		}
			
		this.groupsContacts.removeAll(entriesToRemove);			
	}
	
	private void load()	{
    Persistance per = new Persistance();    
		this.groupsContacts = per.loadData(FILENAME);		
	}
	
	void save()	{
    Persistance per = new Persistance();    
		per.saveData(FILENAME, this.groupsContacts);
	}	
	
	void clear() {
		this.groupsContacts.clear();
	}	

	List<Integer> filterByGroupId(int id) {
		return this.filter(true, id);
	}
	
	List<Integer> filterByContactId(int id) {
		return this.filter(false, id);
	}	
	
	List<Integer> getContactsIdAssignToGroups(List<Group> groups) {
		
		List<Integer> contactsId = new ArrayList<Integer>();
		List<Integer> tempList = null;
		
		for (Group group: groups) {
			tempList = this.filterByGroupId(group.getId());
			
			for (int contactId: tempList) {
				
				// add id only in case, that is not already in the list
				if (!contactsId.contains(contactId)) {
					contactsId.add(contactId);
				}
			}
		}
		
		return contactsId;
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
