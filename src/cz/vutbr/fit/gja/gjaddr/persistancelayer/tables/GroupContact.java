package cz.vutbr.fit.gja.gjaddr.persistancelayer.tables;

/**
 *
 * @author Ragaj
 */
public class GroupContact {
	
	private int contactId;
	private int groupId;

	public int getContactId() {
		return contactId;
	}

	public int getGroupId() {
		return groupId;
	}

	public GroupContact(int contactId, int groupId) {
		this.contactId = contactId;
		this.groupId = groupId;
	}
}
