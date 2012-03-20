package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class PhoneNumber implements Serializable {
	
	static private final long serialVersionUID = 6L;	
	
	private int type;
	private String email;	
		
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PhoneNumber(int id, int type, String email) {
		this.type = type;
		this.email = email;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PhoneNumber other = (PhoneNumber) obj;
		if (this.type != other.type) {
			return false;
		}
		if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PhoneNumber{" + "type=" + type + ", email=" + email + '}';
	}
}
