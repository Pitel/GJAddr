package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class PhoneNumber implements Serializable {
	
	static private final long serialVersionUID = 6L;	
	
	private int type;
	private String number;	
		
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public PhoneNumber(int type, String number) {
		this.type = type;
		this.number = number;
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
		if ((this.number == null) ? (other.number != null) : !this.number.equals(other.number)) {
			return false;
		}
		return true;
	}	
	
	@Override
	public String toString() {
		return "PhoneNumber{" + "type=" + type + ", number=" + number + '}';
	}
}
