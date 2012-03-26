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

	public Url (int type, String value) {
		this.type = type;
		
		try {
			this.value = new URL(value);
		}
		catch (MalformedURLException e) {
			// TODO
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Url other = (Url) obj;

		if (this.type != other.type) {
			return false;
		}
		if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Url{type=" + type + ", value=" + value + '}';
	}
}
