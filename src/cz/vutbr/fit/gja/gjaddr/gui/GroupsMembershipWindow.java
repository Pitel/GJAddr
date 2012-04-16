package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

/**
 * Dialog for adding contact to the group.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit,vutbr.cz>
 */
class GroupsMembershipWindow extends JFrame {

	static final long serialVersionUID = 0;
	private static final Database db = Database.getInstance();
	private static final DefaultListModel listModel = new DefaultListModel();
	private static final JList list = new JList(listModel);
	final JButton button = new JButton("Save changes");	
	
	private Contact editedContact;
	private Group editedGroup;

	public GroupsMembershipWindow(Contact contact) {
		super("Related groups");
		this.editedContact = contact;
		this.button.addActionListener(new ManageContactGroupsListener());		

		this.prepareWindow();
		this.fillGroups();

		setLocationRelativeTo(null);		
		pack();		
		setVisible(true);	
	}

	public GroupsMembershipWindow(Group group) {
		super("Related contacts");
		
		this.editedGroup = group;
		this.button.addActionListener(new ManageGroupContactsListener());
		this.prepareWindow();		
		
		this.fillContacts();
		
		setLocationRelativeTo(null);		
		pack();		
		setVisible(true);			
	}

	private void prepareWindow() {
		final JPanel form = new JPanel();
		final JScrollPane listScrollPane = new JScrollPane(list);

		//list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);		
		
		add(form, BorderLayout.CENTER);
		add(button, BorderLayout.PAGE_END);
		add(listScrollPane);
		add(list);	
	}

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
		
		List<Contact> selectedContacts = this.db.getAllContactsFromGroup(editedGroups);
		int[] selectedIndexes = this.getContactsSelectedIndexes(allContacts, selectedContacts);
		list.setSelectedIndices(selectedIndexes);
	}	
	
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

	private int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = integers.get(i).intValue();
		}
		return ret;
	}
	
	class ManageContactGroupsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Group> selectedGroups = getSelectedGroups();
			List<Contact> contacts = new ArrayList<Contact>();
			contacts.add(editedContact);

			db.updateContactsGroup(contacts, selectedGroups);

			ContactsPanel.fillTable();
			GroupsPanel.fillList();

			dispose();
		}
	}
	
	class ManageGroupContactsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Contact> selectedContacts = getSelectedContacts();
			List<Group> groups = new ArrayList<Group>();
			groups.add(editedGroup);
			
			db.updateGroupsContacts(groups, selectedContacts);

			ContactsPanel.fillTable();
			GroupsPanel.fillList();

			dispose();
		}
	}	

	private List<Group> getSelectedGroups() {
		Group[] groupArray = Arrays.copyOf(this.list.getSelectedValues(), this.list.getSelectedValues().length, Group[].class);
		return Arrays.asList(groupArray);
	}
	
	private List<Contact> getSelectedContacts() {
		Contact[] contactsArray = Arrays.copyOf(this.list.getSelectedValues(), this.list.getSelectedValues().length, Contact[].class);
		return Arrays.asList(contactsArray);
	}	
}
