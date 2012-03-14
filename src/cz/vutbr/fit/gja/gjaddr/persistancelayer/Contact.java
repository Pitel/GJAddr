package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Adress;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Contact object representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Contact implements Serializable {

	static private final long serialVersionUID = 6L;
	
	// not visible for GUI, only for DB usage
	int id = -1;

	private String firstName;
	private String surName;
	private String nickName;
	private Date dateOfBirth;
	//private x Photo;
	private String note;
	
	private List<Messenger> messenger;	
	private List<Url> urls;	
	private List<Adress> adresses;	
	private List<PhoneNumber> phoneNumbers;	
	private List<Email> emails;	
	private List<Custom> customs;	
	
	public int getId() {
		return id;
	}
	
	public List<Messenger> getMessenger() {
		return messenger;
	}

	public void setMessenger(List<Messenger> messenger) {
		this.messenger = messenger;
	}

	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}

	public List<Adress> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<Adress> adresses) {
		this.adresses = adresses;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public List<Custom> getCustoms() {
		return customs;
	}

	public void setCustoms(List<Custom> customs) {
		this.customs = customs;
	}	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
		
	
	public Contact(int id, String firstName, String surName, String nickName, String note) {
		this.id = id;
		this.firstName = firstName;
		this.surName = surName;
		this.nickName = nickName;
		this.note = nickName;		
	}

	public Contact(int id, String firstName, String surName, String nickName, Date dateOfBirth, 
					       String note, List<Messenger> messenger, List<Url> urls, List<Adress> adresses, 
								 List<PhoneNumber> phoneNumbers, List<Email> emails, List<Custom> customs) {
		this.id = id;
		this.firstName = firstName;
		this.surName = surName;
		this.nickName = nickName;
		this.dateOfBirth = dateOfBirth;
		this.note = note;
		this.messenger = messenger;
		this.urls = urls;
		this.adresses = adresses;
		this.phoneNumbers = phoneNumbers;
		this.emails = emails;
		this.customs = customs;
	}
	
	
}
