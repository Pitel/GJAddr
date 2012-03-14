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
	
	@Override // DONE tested
	public List<Contact> getAllContacts() {
		return this.contacts.getAllContacts();
	}

	@Override // DONE tested
	public List<Contact> getSpecificContacts(List<Integer> requiredIds) {
		return this.contacts.filter(requiredIds);
	}

	@Override // TODO not implemented
	public List<Contact> getAllContactsFromGroup(int groupId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override // DONE tested
	public List<Group> getAllGroups() {
		return this.groups.getAllGroups();
	}

	@Override // DONE tested
	public List<Group> addNewGroup(String name) {
		this.groups.addNew(name);
		return this.getAllGroups();
	}

	@Override // DONE tested
	public List<Group> updateGroup(Group group) {
		this.groups.updateGroup(group);
		return this.groups.getAllGroups();
	}

	@Override // DONE tested
	public List<Group> removeGroups(List<Integer> groupIds) {
		this.groups.removeGroup(groupIds);
		return this.groups.getAllGroups();
	}

	@Override // DONE tested
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
}
