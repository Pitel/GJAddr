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
	
	private enum TableType {
		CONTACTS, GROUPS, GROUPSCONTACTS, SETTINGS, ALL
	}	
	
	private boolean autoCommit = true;
	
	public Database() {		
		this.contacts = new DatabaseContacts();
		this.groups = new DatabaseGroups();
		this.groupsContacts = new DatabaseGroupsContacts();
	}
	
	private void commitChanges(TableType day) {
		if (this.autoCommit) {
			switch(day) {
				case CONTACTS:
					this.contacts.save();
					break;
				case GROUPS:
					this.groups.save();
					break;				
				case GROUPSCONTACTS:
					this.groupsContacts.save();
					break;	
				case SETTINGS:
					// TODO
					break;							
				case ALL:
					this.contacts.save();
					this.groups.save();					
					this.groupsContacts.save();
					break;
				default:
					// TODO
			}
		}
	}
	
	public void clearAllData() {
		this.contacts.clear();
		this.groups.clear();
		this.groupsContacts.clear();

		this.commitChanges(TableType.ALL);
	} 	
	
	@Override // DONE 
	public List<Contact> getAllContacts() {
		return this.contacts.getAllContacts();
	}

	@Override // DONE 
	public List<Contact> getSpecificContacts(List<Contact> requiredContacts) {
		return this.contacts.filter(requiredContacts);
	}

	@Override // DONE
	public List<Contact> getAllContactsFromGroup(Group group) {
		List<Integer> contactsId = this.groupsContacts.filterByGroupId(group.getId());
		return this.contacts.filterByIds(contactsId);
	}
	
	@Override // DONE
	public List<Group> getAllGroups() {
		return this.groups.getAllGroups();
	}

	@Override // DONE
	public List<Group> addNewGroup(String name) {

		if (!this.groups.addNew(name)) {
			return null;
		}
		
		this.commitChanges(TableType.GROUPS);
		return this.getAllGroups();
	}

	@Override // DONE
	public List<Group> updateGroup(Group group) {
		this.groups.updateGroup(group);
		this.commitChanges(TableType.GROUPS);
		return this.groups.getAllGroups();
	}

	@Override // DONE
	public List<Group> removeGroups(List<Group> groupsToRemove) {
		this.groups.removeGroup(groupsToRemove);
		this.commitChanges(TableType.GROUPS);
		return this.groups.getAllGroups();
	}

	@Override // DONE
	public List<Contact> addNewContacts(List<Contact> contacts) {
		this.contacts.addNew(contacts);
		this.commitChanges(TableType.CONTACTS);		
		return this.contacts.getAllContacts();
	}

	@Override // DONE
	public List<Contact> updateContact(Contact contact) {
		this.contacts.update(contact);
		this.commitChanges(TableType.CONTACTS);					
		return this.getAllContacts();
	}

	@Override // DONE
	public List<Contact> removeContacts(List<Contact> contactsToRemove) {
		this.contacts.remove(contactsToRemove);		
		this.groupsContacts.removeContactsEntries(contactsToRemove);
		this.commitChanges(TableType.CONTACTS);		
		this.commitChanges(TableType.GROUPSCONTACTS);				
		return this.contacts.getAllContacts();
	}

	@Override // DONE
	public List<Contact> addContactsToGroup(Group group, List<Contact> contactsToAdd) {
		this.groupsContacts.addContactsToGroup(group, contactsToAdd);
		this.commitChanges(TableType.GROUPSCONTACTS);
		return this.getAllContactsFromGroup(group);
	}

	@Override // DONE
	public List<Contact> removeContactsFromGroup(Group group, List<Contact> contactsToRemove) {
		this.groupsContacts.removeContactsFromGroup(group, contactsToRemove);
		this.commitChanges(TableType.GROUPSCONTACTS);
		return this.getAllContactsFromGroup(group);
	}

	@Override // DONE
	public List<Group> getAllGroupsForContact(Contact contact) {
		List<Integer> groupsId = this.groupsContacts.filterByContactId(contact.getId());
		return this.groups.filter(groupsId);
	}	
}
