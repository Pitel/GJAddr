package cz.vutbr.fit.gja.gjaddr.persistancelayer.tables;

/**
 * One email from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Email {
	
	private int id;
	
	private int type;
	private String email;
	
	private int contactId;

	public int getContactId() {
		return contactId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	
		
	public Email(int id, int type, String email, int contactId)					
	{
		this.id = id;
		this.type = type;
		this.email = email;
		this.contactId = contactId;
	}
}
