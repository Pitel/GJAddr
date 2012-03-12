package cz.vutbr.fit.gja.gjaddr.persistancelayer.tables;

/**
 *
 * @author Ragaj
 */
public class PhoneNumber {
	
	private int id;
	
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	private int contactId;

	public int getContactId() {
		return contactId;
	}

	public PhoneNumber(int id, int type, String email, int contactId) {
		this.id = id;
		this.type = type;
		this.email = email;
		this.contactId = contactId;
	}
}
