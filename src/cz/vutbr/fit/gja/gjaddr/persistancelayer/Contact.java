package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.util.Date;
import java.util.List;

/**
 * Contact object representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Contact {
	// not visible for GUI, only for DB usage
	private int id;

	private String firstName;
	private String surName;
	private String nickName;

	private Date dateOfBirth;
	//private x Photo;
	private String note;  

	// Messengers
	//private List<Messenger> messenger;

	// URLs
	private List<Url> url;  

	// Adresses
	//private List<Adress> adress;  

	// Phone numbers
	//private List<PhoneNumber> phoneNumbers;  

	// Emails
	//private List<Emails> emails;  

	// Custom
	//private List<Custom> customs;  

	public Contact(String firstName, String surName, String nickName) {
		this.firstName = firstName;
		this.surName = surName;
		this.nickName = nickName;
	}
}
