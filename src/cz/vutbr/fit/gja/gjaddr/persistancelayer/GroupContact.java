package cz.vutbr.fit.gja.gjaddr.persistancelayer.tables;

import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class GroupContact implements Serializable {
	
	static private final long serialVersionUID = 6L;	
	
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
