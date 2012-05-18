package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with contacts collection.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class DatabaseContacts {

  /**
   * Internal id counter.
   */
  private int idCounter = 0;
  /**
   * Persistance filename.
   */
  private final String FILENAME = new File(Settings.instance().getDataDir(), "contacts").toString();
  /**
   * Contacts collection.
   */
  private ArrayList<Contact> contacts = null;

  /**
   * Constructor.
   */
  public DatabaseContacts() {
    this.load();
    this.setLastIdNumber();
  }

  /**
   * Add new contact.
   *
   * @param newContacts contact
   */
  void addNew(List<Contact> newContacts) {

    for (Contact contact : newContacts) {
      this.removeEmptyValues(contact);
      contact.id = ++this.idCounter;
      this.contacts.add(contact);
    }
  }

  /**
   * Update contact.
   *
   * @param contact contact.
   */
  void update(Contact contact) {
    this.removeEmptyValues(contact);
    Contact updatedContact = this.filterItem(contact.getId());
    int index = this.contacts.indexOf(updatedContact);

    if (index != -1) {
      this.contacts.set(index, contact);
    }
  }

  /**
   * Removes empty entries from contact.
   *
   * @param contact
   */
  private void removeEmptyValues(Contact contact) {

    List<Event> datesToRemove = new ArrayList<Event>();
    List<Address> adressesToRemove = new ArrayList<Address>();
    List<Email> emailsToRemove = new ArrayList<Email>();
    List<Url> urlsToRemove = new ArrayList<Url>();
    List<PhoneNumber> phonesToRemove = new ArrayList<PhoneNumber>();
    List<Messenger> messengersToRemove = new ArrayList<Messenger>(); 

    for (Event event : contact.getDates()) {
      if (event.getDate() == null) {
        datesToRemove.add(event);
      }
    }

    for (Address addr : contact.getAdresses()) {
      if (addr.getAddress().isEmpty()) {
        adressesToRemove.add(addr);
      }
    }

    for (Url url : contact.getUrls()) {
      if (url.getValue() == null) {
        urlsToRemove.add(url);
      }
    }

    for (Email email : contact.getEmails()) {
      if (email.getEmail().isEmpty()) {
        emailsToRemove.add(email);
      }
    }

    for (PhoneNumber phone : contact.getPhoneNumbers()) {
      if (phone.getNumber().isEmpty()) {
        phonesToRemove.add(phone);
      }
    }
    
    for (Messenger messenger : contact.getMessenger()) {
      if (messenger.getValue().isEmpty()) {
        messengersToRemove.add(messenger);
      }
    }  
    
    // Custom are currently not supported - null value
    List<Custom> customsToRemove = new ArrayList<Custom>();  
    List<Custom> customs = contact.getCustoms();
    if (customs != null) {
      for (Custom custom : contact.getCustoms()) {
        if (custom.getValue().isEmpty()) {
          customsToRemove.add(custom);
        }
      }
      contact.getCustoms().removeAll(customsToRemove);
    }

    contact.getDates().removeAll(datesToRemove);
    contact.getAdresses().removeAll(adressesToRemove);
    contact.getEmails().removeAll(emailsToRemove);
    contact.getUrls().removeAll(urlsToRemove);
    contact.getPhoneNumbers().removeAll(phonesToRemove);
    contact.getMessenger().removeAll(messengersToRemove);   
  }

  /**
   * Remove contacts from collection.
   *
   * @param contactsToRemove contacts to remove
   */
  void remove(List<Contact> contactsToRemove) {
    this.contacts.removeAll(contactsToRemove);
  }

  /**
   * Get all contacts.
   *
   * @return list of all contacts.
   */
  public List<Contact> getAllContacts() {
    return new ArrayList<Contact>(this.contacts);
  }

  /**
   * Load all contacts from persistance.
   */
  private void load() {
    Persistance per = new Persistance();
    this.contacts = (ArrayList<Contact>) per.loadData(FILENAME);
  }

  /**
   * Save all contacts.
   */
  void save() {
    Persistance per = new Persistance();
    per.saveData(FILENAME, this.contacts);
  }

  /**
   * Set last id number.
   */
  private void setLastIdNumber() {

    int counter = 0;

    for (Contact contact : this.contacts) {
      int id = contact.getId();
      if (id > counter) {
        counter = id;
      }
    }

    this.idCounter = counter;
  }

  /**
   * Filter contacts according to the id
   *
   * @param id required id
   * @return contact or null
   */
  private Contact filterItem(int id) {
    for (Contact contact : this.contacts) {
      if (contact.getId() == id) {
        return contact;
      }
    }

    return null;
  }

  /**
   * Clear all contacts.
   */
  void clear() {
    this.contacts.clear();
    this.idCounter = 0;
  }

  /**
   * Filter contacts.
   *
   * @param requiredContacts list of required contacts.
   * @return list of contact or empty list
   */
  List<Contact> filter(List<Contact> requiredContacts) {

    List<Contact> filteredContacts = new ArrayList<Contact>();

    for (Contact contact : this.contacts) {

      if (requiredContacts.contains(contact)) {
        filteredContacts.add(contact);
      }
    }

    return filteredContacts;
  }

  /**
   * Filter contacts by ids.
   *
   * @param requiredContacts list of required contacts ids.
   * @return list of contact or empty list
   */
  List<Contact> filterByIds(List<Integer> requiredContactsId) {

    List<Contact> filteredContacts = new ArrayList<Contact>();

    for (Contact contact : this.contacts) {

      if (requiredContactsId.contains(contact.getId())) {
        filteredContacts.add(contact);
      }
    }

    return filteredContacts;
  }
}
