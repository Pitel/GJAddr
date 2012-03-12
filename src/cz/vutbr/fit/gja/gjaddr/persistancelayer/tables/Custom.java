package cz.vutbr.fit.gja.gjaddr.persistancelayer.tables;

/**
 *
 * @author Ragaj
 */
public class Custom {
	private int id;
	
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Custom(int id, String name, String value, int contactId) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.contactId = contactId;
	}	
}
