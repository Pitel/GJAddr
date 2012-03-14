package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 * One email from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Email implements Serializable {
	
	static private final long serialVersionUID = 6L;	
	
	private int id;
	
	private int type;
	private String email;
	
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
		
	public Email(int id, int type, String email)					
	{
		this.id = id;
		this.type = type;
		this.email = email;
	}
}
