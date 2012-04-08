package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
import java.util.AbstractList;
import java.util.ArrayList;
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
	private DatabaseAuth tokens;
	
	private enum TableType {
		CONTACTS, GROUPS, GROUPSCONTACTS, SETTINGS, AUTH, ALL
	}	
	
	private boolean autoCommit = true;
	
	public Database() {		
		this.contacts = new DatabaseContacts();
		this.groups = new DatabaseGroups();
		this.groupsContacts = new DatabaseGroupsContacts();
		this.tokens = new DatabaseAuth();
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
				case AUTH:
					this.tokens.save();
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
		this.tokens.clear();

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

	@Override // TOTEST
	public List<Contact> getAllContactsFromGroup(List<Group> requiredGroups) {
		
		// groupId == -1 indicate all contacts
		for (Group group : requiredGroups) {
			if (group.getId() == -1) {
				return this.contacts.getAllContacts();
			}
		}
		
		List<Integer> contactsId = this.groupsContacts.getContactsIdAssignToGroups(requiredGroups);				
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
	
	private List<Contact> getAllContactsFromGroup(Group group) {
		List<Group> requiredGroups = new ArrayList<Group>();
		requiredGroups.add(group);
		
		return this.getAllContactsFromGroup(requiredGroups);
	}

	/**
	 * Get token specified by service.
	 * 
	 * @param service
	 * @return
	 */
	public AuthToken getToken(ServicesEnum service) {
		return this.tokens.get(service.getCode());
	}

	/**
	 * Get token specified by service.
	 *
	 * @param service
	 * @return
	 */
	public AuthToken getToken(Integer service) {
		return this.tokens.get(service);
	}

	/**
	 * Save new token to database.
	 * 
	 * @param token
	 * @return
	 */
	public AuthToken addToken(AuthToken token) {
		this.tokens.add(token);
		this.commitChanges(TableType.AUTH);
		return this.tokens.get(token.getService());
	}
}
