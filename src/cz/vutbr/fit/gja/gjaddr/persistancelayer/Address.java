package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class Address implements Serializable {
		
	static private final long serialVersionUID = 6L;

	private int type;
	private String street;	
	private int number;	
	private String city;	
	private int postCode;	
	private String country;	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Address(int type, String street, int number, String city, int postCode, String country) {
		this.type = type;
		this.street = street;
		this.number = number;
		this.city = city;
		this.postCode = postCode;
		this.country = country;
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
		if ((this.street == null) ? (other.street != null) : !this.street.equals(other.street)) {
			return false;
		}
		if (this.number != other.number) {
			return false;
		}
		if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
			return false;
		}
		if (this.postCode != other.postCode) {
			return false;
		}
		if ((this.country == null) ? (other.country != null) : !this.country.equals(other.country)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Address{" + "type=" + type + ", street=" + street + ", number=" + number + ", city=" + city + ", postCode=" + postCode + ", country=" + country + '}';
	}	
}
