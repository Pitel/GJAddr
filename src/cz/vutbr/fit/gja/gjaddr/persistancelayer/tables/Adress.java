package cz.vutbr.fit.gja.gjaddr.persistancelayer.tables;

/**
 *
 * @author Ragaj
 */
public class Adress {
	
	private int id;
	
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	private String street;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	private int postCode;

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
	
	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	private int contactId;

	public int getContactId() {
		return contactId;
	}

	public Adress(int id, int type, String street, int number, String city, int postCode, String country, int contactId) {
		this.id = id;
		this.type = type;
		this.street = street;
		this.number = number;
		this.city = city;
		this.postCode = postCode;
		this.country = country;
		this.contactId = contactId;
	}
}
