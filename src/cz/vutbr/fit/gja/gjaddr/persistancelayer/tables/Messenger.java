package cz.vutbr.fit.gja.gjaddr.persistancelayer.tables;

/**
 *
 * @author Ragaj
 */
public class Messenger {
	
	private int id;
	
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	private int contactId;

	public int getContactId() {
		return contactId;
	}

	public Messenger(int id, int type, String value, int contactId) {
		this.id = id;
		this.type = type;
		this.value = value;
		this.contactId = contactId;
	}
}
