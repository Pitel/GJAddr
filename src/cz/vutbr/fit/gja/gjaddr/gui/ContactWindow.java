package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Contact editing window.
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class ContactWindow extends JFrame {
	static final long serialVersionUID = 0;
	
	private final Database db = Database.getInstance();
	private Contact editedContact;
	
	private JButton button;
	private JTextField nameField, surnameField, addressField, emailField, phoneField;

	static enum Action {
    ADD, EDIT, REMOVE 
	}
	
	public ContactWindow(Action action) {
		super("Add Contact");
				
		switch(action) {
			case ADD:
				this.editedContact = new Contact();
				this.addContact(action);				
				break;
			case EDIT:
				this.editedContact = ContactsPanel.getSelectedContact();
				this.editContact(action);
				break;
			case REMOVE:
				if(this.removeContacts())
					GroupsPanel.fillList();
				break;
		}		
	}
	
	/**
	 * Method for removing contact
	 */
	private boolean removeContacts() {
		Contact contact = ContactsPanel.getSelectedContact();
		
		int delete = JOptionPane.showConfirmDialog(
			this,
			"Delete contact " + contact.getFullName() + "?",
			"Delete contact",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			new ImageIcon(getClass().getResource("/res/minus.png"), "-")
		);

		if (delete == 0) {			
			List<Contact> contactToRemove = new ArrayList<Contact>();
			contactToRemove.add(contact);
			db.removeContacts(contactToRemove);
			return true;
		}
		
		return false;
	}		
	
	private void addContact(Action action) {
		 this.createWindowContent(action);
	 }
	
	private void editContact(Action action) {
		 this.createWindowContent(action);
		 this.fillEditedContact();
	 }

	 	/**
	 * Method for creating window content layout.
	 */
	private void createWindowContent(Action action) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {
			System.err.println(e);
		}
		
		final JPanel form = new JPanel(new GridLayout(0, 2));		
		
		if (action == Action.ADD) {
			setIconImage(new ImageIcon(getClass().getResource("/res/plus.png"), "GJAddr").getImage());
			button = new JButton("Add contact");	
			button.addActionListener(new NewContactActionListener());			
		}
		else {
			setIconImage(new ImageIcon(getClass().getResource("/res/plus_g.png"), "GJAddr").getImage());
			button = new JButton("Update contact");		
			button.addActionListener(new EditContactActionListener());
		}
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		form.add(new JLabel("Name"));
		nameField = new JTextField();
		form.add(nameField);
		form.add(new JLabel("Surname"));
		surnameField = new JTextField();
		form.add(surnameField);
		form.add(new JLabel("Address"));
		addressField = new JTextField();
		form.add(addressField);
		form.add(new JLabel("E-mail"));
		emailField = new JTextField();
		form.add(emailField);
		form.add(new JLabel("Phone"));
		phoneField = new JTextField();
		form.add(phoneField);
		add(form);
		add(button);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}		
	 
	private void fillEditedContact() {	 
		 this.nameField.setText(this.editedContact.getFirstName());
		 this.surnameField.setText(this.editedContact.getSurName());
		 this.phoneField.setText(this.editedContact.getAllPhones());
		 this.emailField.setText(this.editedContact.getAllEmails());		 
		 this.phoneField.setText(this.editedContact.getAllPhones());		 		 
	 }
	
	private void resolveEditedContact() {
		this.editedContact.setFirstName(nameField.getText());
		this.editedContact.setSurName(surnameField.getText());

		final ArrayList<Address> addresses = new ArrayList<Address>();
		addresses.add(new Address(0, addressField.getText()));
		this.editedContact.setAdresses(addresses);
		
		final ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();
		phones.add(new PhoneNumber(0, phoneField.getText()));
		this.editedContact.setPhoneNumbers(phones);		

		final ArrayList<Email> emails = new ArrayList<Email>();
		emails.add(new Email(0, emailField.getText()));
		this.editedContact.setEmails(emails);		
	}	 
	
	private class NewContactActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Contact> newContacts = new ArrayList<Contact>();
			
			resolveEditedContact();
			newContacts.add(editedContact);			
			db.addNewContacts(newContacts);
			
			// update tables
			ContactsPanel.fillTable();	
			GroupsPanel.fillList();	
			
			dispose();				
		}		
	}
	
	private class EditContactActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			resolveEditedContact();
			List<Contact> contactsToUpdate = db.updateContact(editedContact);
			
			ContactsPanel.fillTable();			
			dispose();				
		}		
	}	
}
