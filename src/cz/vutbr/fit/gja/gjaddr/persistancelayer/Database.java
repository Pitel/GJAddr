package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.util.List;

/**
 * Class for communication with database.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Database implements IDatabase {
	
	private DatabaseContacts contacts;
	private DatabaseGroups groups;
	private DatabaseGroupsContacts groupsContacts;	
	
	public Database() {		
		this.contacts = new DatabaseContacts();
		this.groups = new DatabaseGroups();
		this.groupsContacts = new DatabaseGroupsContacts();
	}
	
	public void commitChanges() {
		this.contacts.save();
		this.groups.save();
		this.groupsContacts.save();
	}
	
	@Override // DONE 
	public List<Contact> getAllContacts() {
		return this.contacts.getAllContacts();
	}

	@Override // DONE 
	public List<Contact> getSpecificContacts(List<Integer> requiredIds) {
		return this.contacts.filter(requiredIds);
	}

	@Override // DONE
	public List<Contact> getAllContactsFromGroup(int groupId) {
		List<Integer> contactsId = this.groupsContacts.filterByGroupId(groupId);
		return this.getSpecificContacts(contactsId);		
	}
	
	@Override // DONE
	public List<Group> getAllGroups() {
		return this.groups.getAllGroups();
	}

	@Override // DONE
	public List<Group> addNewGroup(String name) {
		this.groups.addNew(name);
		return this.getAllGroups();
	}

	@Override // DONE
	public List<Group> updateGroup(Group group) {
		this.groups.updateGroup(group);
		return this.groups.getAllGroups();
	}

	@Override // DONE
	public List<Group> removeGroups(List<Integer> groupIds) {
		this.groups.removeGroup(groupIds);
		return this.groups.getAllGroups();
	}

	@Override // DONE
	public List<Contact> addNewContacts(List<Contact> contacts) {
		this.contacts.addNew(contacts);
		return this.contacts.getAllContacts();
	}

	@Override // TOTEST
	public List<Contact> updateContact(Contact contact) {
		this.contacts.update(contact);
		return this.getAllContacts();
	}

	@Override // TOTEST
	public List<Contact> removeContacts(List<Integer> contacts) {
		this.contacts.remove(contacts);
		return this.contacts.getAllContacts();
	}

	@Override // DONE
	public List<Contact> addContactsToGroup(int groupId, List<Integer> contactsIdToAdd) {
		this.groupsContacts.addContactsToGroup(groupId, contactsIdToAdd);
		return this.getAllContactsFromGroup(groupId);
	}

	@Override // NOT IMPLEMENTED
	public List<Contact> removeContactsFromGroup(int groupId, List<Integer> contactsIdToRemove) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override // DONE
	public List<Group> getAllGroupsForContact(int contactId) {
		List<Integer> groupsId = this.groupsContacts.filterByContactId(contactId);
		return this.groups.filter(groupsId);
	}
	
	public void clearAllData() {
		this.contacts.clear();
		this.groups.clear();
		this.groupsContacts.clear();

		this.commitChanges();
	} 
}
