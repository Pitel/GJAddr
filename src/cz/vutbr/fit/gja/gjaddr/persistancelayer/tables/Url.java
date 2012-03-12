package cz.vutbr.fit.gja.gjaddr.persistancelayer.tables;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * One URL from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Url {
	private int id;

	private int type;
	private URL value; 
	
	private int contactId;

	public int getContactId() {
		return contactId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public URL getValue() {
		return value;
	}

	public void setValue(URL value) {
		this.value = value;
	}

	public Url (int id, int type, String value, int contactId) throws MalformedURLException {
		this.id = id;
		this.type = type;
		this.value = new URL(value);
		this.contactId = contactId;
	}
}
