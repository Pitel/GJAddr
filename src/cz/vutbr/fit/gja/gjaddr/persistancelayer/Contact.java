package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
	private List<Address> adresses;	
	private List<PhoneNumber> phoneNumbers;	
	private List<Email> emails;	
	private List<Custom> customs;	
	
	public int getId() {
		return id;
	}
	
	public List<Messenger> getMessenger() {
		return (messenger != null) ? messenger : new ArrayList<Messenger>();
	}

	public void setMessenger(List<Messenger> messenger) {
		this.messenger = messenger;
	}

	public List<Url> getUrls() {
		return (urls != null) ? urls : new ArrayList<Url>();
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}

	public List<Address> getAdresses() {
		return (adresses != null) ? this.adresses : new ArrayList<Address>();
	}

	public void setAdresses(List<Address> adresses) {
		this.adresses = adresses;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return (phoneNumbers != null) ? this.phoneNumbers : new ArrayList<PhoneNumber>();
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<Email> getEmails() {
		return (emails != null) ? emails : new ArrayList<Email>();
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public List<Custom> getCustoms() {
		return (customs != null) ? customs : new ArrayList<Custom>();
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Return true if contact has birthday within one month.
	 *
	 * @return
	 */
	public boolean hasBirthday() {
		if (this.dateOfBirth == null) {
			return false;
		}
		// date today
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, -1);
		// date within one month
		Calendar month = Calendar.getInstance();
		month.add(Calendar.DAY_OF_YEAR, +30);
		// birthday of contact
		Calendar bday = Calendar.getInstance();
		bday.setTime(this.dateOfBirth);
		bday.set(Calendar.YEAR, 2012);
		if (bday.after(today) && bday.before(month)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getFullName() {
		StringBuilder fullName = new StringBuilder();
		
		try {
			fullName.append(this.surName);	
			fullName.append(" ");
			fullName.append(this.firstName);
		}
		catch (NullPointerException e) {
			return "";
		}
		
		return fullName.toString();
	}
	
	public String getAllAddresses() {
		String separator = "";		
		StringBuilder addresses = new StringBuilder();
		
		try {
			for (Address address : this.adresses) {
				addresses.append(separator);
				addresses.append(address.getAddress());
				separator = "; ";
			}	
		}
		catch (NullPointerException e) {
			return "";
		}
		
		return addresses.toString();
	}
	
	public String getAllPhones() {
		String separator = "";
		final StringBuilder phones = new StringBuilder();
		try {
			for (PhoneNumber phone : this.phoneNumbers) {
				phones.append(separator);
				phones.append(phone.getNumber());
				separator = ", ";
			}
		}
		catch (NullPointerException e) {
			return "";
		}

		return phones.toString();
	}
	
	public String getAllEmails() {
		String separator = "";
		final StringBuilder emails = new StringBuilder();

		try {
			for (Email email : this.emails) {
				emails.append(separator);
				emails.append(email.getEmail());
				separator = ", ";
			}
		} 
		catch (NullPointerException e) {
			return "";
		}
		
		return emails.toString();
	}	
	
	public Contact(String firstName, String surName, String nickName, String note) {
		this.firstName = firstName;
		this.surName = surName;
		this.nickName = nickName;
		this.note = nickName;		
	}

	public Contact(String firstName, String surName, String nickName, Date dateOfBirth, 
					       String note, List<Messenger> messenger, List<Url> urls, List<Address> adresses, 
								 List<PhoneNumber> phoneNumbers, List<Email> emails, List<Custom> customs) {
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Contact other = (Contact) obj;
		if (this.id != other.id) {
			return false;
		}
		if ((this.surName == null) ? (other.surName != null) : !this.surName.equals(other.surName)) {
			return false;
		}
		if ((this.nickName == null) ? (other.nickName != null) : !this.nickName.equals(other.nickName)) {
			return false;
		}
		if (this.dateOfBirth != other.dateOfBirth && (this.dateOfBirth == null || !this.dateOfBirth.equals(other.dateOfBirth))) {
			return false;
		}
		if ((this.note == null) ? (other.note != null) : !this.note.equals(other.note)) {
			return false;
		}
		if (this.messenger != other.messenger && (this.messenger == null || !this.messenger.equals(other.messenger))) {
			return false;
		}
		if (this.urls != other.urls && (this.urls == null || !this.urls.equals(other.urls))) {
			return false;
		}
		if (this.adresses != other.adresses && (this.adresses == null || !this.adresses.equals(other.adresses))) {
			return false;
		}
		if (this.phoneNumbers != other.phoneNumbers && (this.phoneNumbers == null || !this.phoneNumbers.equals(other.phoneNumbers))) {
			return false;
		}
		if (this.emails != other.emails && (this.emails == null || !this.emails.equals(other.emails))) {
			return false;
		}
		if (this.customs != other.customs && (this.customs == null || !this.customs.equals(other.customs))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Contact{" + "id=" + id + ", firstName=" + firstName + ", surName=" + surName 
						          + ", nickName=" + nickName + ", dateOfBirth=" + dateOfBirth + ", note=" 
						          + note + ", messenger=" + messenger + ", urls=" + urls + ", adresses=" 
						          + adresses + ", phoneNumbers=" + phoneNumbers + ", emails=" + emails + ", customs=" 
						          + customs + '}';
	}
}
