package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class Adress implements Serializable {
		
	static private final long serialVersionUID = 6L;
	
	private int id;	
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

	public Adress(int id, int type, String street, int number, String city, int postCode, String country) {
		this.id = id;
		this.type = type;
		this.street = street;
		this.number = number;
		this.city = city;
		this.postCode = postCode;
		this.country = country;
	}
}
