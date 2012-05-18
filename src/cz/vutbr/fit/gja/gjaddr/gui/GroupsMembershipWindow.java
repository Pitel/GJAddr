package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import org.slf4j.LoggerFactory;

/**
 * Dialog for adding contact to the group.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit,vutbr.cz>
 */
class GroupsMembershipWindow extends JFrame {

  static final long serialVersionUID = 0;
  /**
   * Database instance.
   */
  private static final Database db = Database.getInstance();
  /**
   * List model for contacts or groups.
   */
  private static final DefaultListModel listModel = new DefaultListModel();
  /**
   * List with contacts of groups.
   */
  private static final JList list = new JList(listModel);
  /**
   * Save button.
   */
  final JButton button = new JButton("Save changes");
  /**
   * Currently edited contact.
   */
  private Contact editedContact;
  /**
   * Currently edited group.
   */
  private Group editedGroup;

  /**
   * Constructor for groups membership.
   *
   * @param contact edited contact
   */
  public GroupsMembershipWindow(Contact contact) {
    super("Related groups");
    this.editedContact = contact;
    this.button.addActionListener(new ManageContactGroupsListener());

    this.prepareWindow();
    this.fillGroups();

    setLocationRelativeTo(null);
    pack();
    setVisible(true);

    log("Opening related groups window.");
  }

  /**
   * Contructor for contacts membership.
   *
   * @param group edited group
   */
  public GroupsMembershipWindow(Group group) {
    super("Related contacts");

    this.editedGroup = group;
    this.button.addActionListener(new ManageGroupContactsListener());
    this.prepareWindow();

    this.fillContacts();

    setLocationRelativeTo(null);
    pack();
    setVisible(true);

    log("Opening related contacts window.");
  }

  /**
   * Prepare windows layout.
   */
  private void prepareWindow() {
    final JPanel form = new JPanel();
    final JScrollPane listScrollPane = new JScrollPane(list);

    //list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);		

    add(form, BorderLayout.CENTER);
    add(button, BorderLayout.PAGE_END);
    add(listScrollPane);
    add(list);
  }

  /**
   * Load contacts to the window - group case.
   */
  private void fillContacts() {

    // get all groups from DB
    List<Contact> allContacts = db.getAllContacts();
    this.orderContactList(allContacts);

    // set as list model
    listModel.clear();

    for (Contact contact : allContacts) {
      listModel.addElement(contact);
    }

    // get selected groups for currently edited contact and set the right selected indexes
    List<Group> editedGroups = new ArrayList<Group>();
    editedGroups.add(this.editedGroup);

    List<Contact> selectedContacts = this.db.getAllContactsFromGroups(editedGroups);
    int[] selectedIndexes = this.getContactsSelectedIndexes(allContacts, selectedContacts);
    list.setSelectedIndices(selectedIndexes);
  }

  /**
   * Load groups to the window - contact case.
   */
  private void fillGroups() {

    // get all groups from DB
    List<Group> allGroups = db.getAllGroups();
    this.orderGroupList(allGroups);

    // set as list model
    listModel.clear();
    for (Group g : allGroups) {
      listModel.addElement(g);
    }

    // get selected groups for currently edited contact and set the right selected indexes
    List<Group> selectedGroups = this.db.getAllGroupsForContact(this.editedContact);
    int[] selectedIndexes = this.getGroupsSelectedIndexes(allGroups, selectedGroups);
    list.setSelectedIndices(selectedIndexes);
  }

  /**
   * Get indexes for selected groups.
   *
   * @param allGroups list of all groups
   * @param selectedGroups selected groups
   * @return array of indexes
   */
  private int[] getGroupsSelectedIndexes(List<Group> allGroups, List<Group> selectedGroups) {
    List<Integer> indexes = new ArrayList<Integer>();

    for (Group selectedGroup : selectedGroups) {
      int index = allGroups.indexOf(selectedGroup);
      if (index != -1) { // prevent dead groups
        indexes.add(index);
      }
    }

    return this.convertIntegers(indexes);
  }

  /**
   * Get indexes for selected contacts
   *
   * @param allContacts list of all contacts
   * @param selectedContacts selected contacts
   * @return array of indexes
   */
  private int[] getContactsSelectedIndexes(List<Contact> allContacts, List<Contact> selectedContacts) {
    List<Integer> indexes = new ArrayList<Integer>();

    for (Contact selectedContact : selectedContacts) {
      int index = allContacts.indexOf(selectedContact);
      if (index != -1) { // prevent dead groups
        indexes.add(index);
      }
    }

    return this.convertIntegers(indexes);
  }

  /**
   * Group list sorter.
   */
  private void orderGroupList(List<Group> groups) {
    Collections.sort(groups, new Comparator() {

      @Override
      public int compare(Object o1, Object o2) {
        Group p1 = (Group) o1;
        Group p2 = (Group) o2;
        return p1.getName().compareToIgnoreCase(p2.getName());
      }
    });
  }

  /**
   * Contact list sorter.
   */
  private void orderContactList(List<Contact> contacts) {
    Collections.sort(contacts, new Comparator() {

      @Override
      public int compare(Object o1, Object o2) {
        Contact p1 = (Contact) o1;
        Contact p2 = (Contact) o2;
        return p1.getFullName().compareToIgnoreCase(p2.getFullName());
      }
    });
  }

  /**
   * Converts integers list to the array.
   *
   * @param integers
   * @return
   */
  private int[] convertIntegers(List<Integer> integers) {
    int[] ret = new int[integers.size()];
    for (int i = 0; i < ret.length; i++) {
      ret[i] = integers.get(i).intValue();
    }
    return ret;
  }

  /**
   * Save button handling for managing contacts.
   */
  private class ManageContactGroupsListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      List<Group> selectedGroups = getSelectedGroups();
      List<Contact> contacts = new ArrayList<Contact>();
      contacts.add(editedContact);

      db.updateContactsGroup(contacts, selectedGroups);

      ContactsPanel.fillTable(false, false);
      GroupsPanel.fillList();

      log("Closing related groups window.");

      dispose();
    }
  }

  /**
   * Save button handling for managing groups.
   */
  private class ManageGroupContactsListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      List<Contact> selectedContacts = getSelectedContacts();
      List<Group> groups = new ArrayList<Group>();
      groups.add(editedGroup);

      db.updateGroupsContacts(groups, selectedContacts);

      ContactsPanel.fillTable(false, false);
      GroupsPanel.fillList();

      log("Closing related contacts window.");

      dispose();
    }
  }

  /**
   * Gets the selected group from the list.
   */
  private List<Group> getSelectedGroups() {
    Group[] groupArray = Arrays.copyOf(this.list.getSelectedValues(),
            this.list.getSelectedValues().length,
            Group[].class);
    return Arrays.asList(groupArray);
  }

  /**
   * Gets the selected contacts from the list.
   */
  private List<Contact> getSelectedContacts() {
    Contact[] contactsArray = Arrays.copyOf(this.list.getSelectedValues(),
            this.list.getSelectedValues().length,
            Contact[].class);
    return Arrays.asList(contactsArray);
  }

  /**
   * Method for messages logging.
   *
   * @param msg message to log
   */
  private void log(String msg) {
    LoggerFactory.getLogger(this.getClass()).info(msg);
  }
}
