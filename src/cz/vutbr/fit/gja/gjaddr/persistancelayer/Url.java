package cz.vutbr.fit.gja.gjaddr.persistancelayer;

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

	public int getType() {
		return this.type;
	}

	public URL getValue() {
		return this.value;
	}

	public Url (int id, int type, String value) throws MalformedURLException {
		this.id = id;
		this.type = type;
		this.value = new URL(value);
	}
}
