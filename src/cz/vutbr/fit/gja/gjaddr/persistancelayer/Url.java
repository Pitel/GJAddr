package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * One URL from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Url implements Serializable {
	
	static private final long serialVersionUID = 6L;	
	
	private int id;

	private int type;
	private URL value; 

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

	public Url (int id, int type, String value) {
		this.id = id;
		this.type = type;
		
		try {
			this.value = new URL(value);
		}
		catch (MalformedURLException e) {
			// TODO
		}
	}
}
