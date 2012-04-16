package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.TypesEnum;
import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class PhoneNumber implements Serializable {
	
	static private final long serialVersionUID = 6L;	
	
	private TypesEnum type;
	private String number;	
		
	public TypesEnum getType() {
		return type;
	}

	public void setType(TypesEnum type) {
		this.type = type;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public PhoneNumber(TypesEnum type, String number) {
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
