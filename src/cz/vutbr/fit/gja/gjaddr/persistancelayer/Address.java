package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class Address implements Serializable {
		
	static private final long serialVersionUID = 6L;

	private int type;
	private String address;	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Create class representation of address.
	 * 
	 * @param type
	 * @param address
	 */	
	public Address(int type, String address) {
		this.type = type;
		this.address = address;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Address other = (Address) obj;
		if (this.type != other.type) {
			return false;
		}
		if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Address{" + "type=" + type + ", address=" + address + '}';
	}
	
	
}
