package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.TypesEnum;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

	private Contact contact;
	private final JButton button = new JButton();
	private final PhotoButton photo = new PhotoButton();
	private final JTextField nameField = new JTextField();
	private final JTextField surnameField = new JTextField();
	private final JTextField addressField = new JTextField();
	private final JTextField emailField = new JTextField();
	private final JTextField phoneField = new JTextField();

	/**
	 * Constructor for adding new contact
	 */
	public ContactWindow() {
		super("Add contact");
		setIconImage(new ImageIcon(getClass().getResource("/res/plus.png"), "+").getImage());
		photo.setIcon(new ImageIcon(getClass().getResource("/res/photo.png"), ":)"));
		button.setText("Add contact");
		button.addActionListener(new NewContactActionListener());
		contact = new Contact();
		prepare();
	}

	/**
	 * Constructor for editing contact
	 */
	public ContactWindow(Contact contact) {
		super("Edit contact");
		this.contact = contact;
		setIconImage(new ImageIcon(getClass().getResource("/res/edit.png"), "Edit").getImage());
		photo.setContact(contact);
		button.setText("Edit contact");
		button.addActionListener(new EditContactActionListener());
		nameField.setText(contact.getFirstName());
		surnameField.setText(contact.getSurName());
		addressField.setText(contact.getAllAddresses());
		phoneField.setText(contact.getAllPhones());
		emailField.setText(contact.getAllEmails());
		phoneField.setText(contact.getAllPhones());
		prepare();
	}

	/**
	 * Method for creating window content layout.
	 */
	private void prepare() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			System.err.println(e);
		}
		final JPanel form = new JPanel(new GridBagLayout());
		form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 1, 1, 1);
		c.weightx = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 0;
		form.add(new JLabel("Photo"), c);
		c.gridx = 1;
		c.weightx = 1;
		form.add(photo, c);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;
		form.add(new JLabel("Name"), c);
		c.gridx = 1;
		c.weightx = 1;
		form.add(nameField, c);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;
		form.add(new JLabel("Surname"), c);
		c.gridx = 1;
		c.weightx = 1;
		form.add(surnameField, c);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;
		form.add(new JLabel("Address"), c);
		c.gridx = 1;
		c.weightx = 1;
		form.add(addressField, c);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;
		form.add(new JLabel("E-mail"), c);
		c.gridx = 1;
		c.weightx = 1;
		form.add(emailField, c);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;
		form.add(new JLabel("Phone"), c);
		c.gridx = 1;
		c.weightx = 1;
		form.add(phoneField, c);
		add(form, BorderLayout.CENTER);
		add(button, BorderLayout.PAGE_END);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	private void resolvecontact() {
		contact.setPhoto((ImageIcon) photo.getIcon());
		contact.setFirstName(nameField.getText());
		contact.setSurName(surnameField.getText());

		final ArrayList<Address> addresses = new ArrayList<Address>();
		addresses.add(new Address(TypesEnum.HOME, addressField.getText()));
		contact.setAdresses(addresses);

		final ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();
		phones.add(new PhoneNumber(TypesEnum.HOME, phoneField.getText()));
		contact.setPhoneNumbers(phones);

		final ArrayList<Email> emails = new ArrayList<Email>();
		emails.add(new Email(TypesEnum.HOME, emailField.getText()));
		contact.setEmails(emails);
	}

	/**
	 * Submiting new contact action
	 */
	private class NewContactActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Contact> newContacts = new ArrayList<Contact>();

			resolvecontact();
			newContacts.add(contact);
			db.addNewContacts(newContacts);

			// update tables
			ContactsPanel.fillTable();
			GroupsPanel.fillList();

			dispose();
		}
	}

	/**
	 * Confirming contact change action
	 */
	private class EditContactActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			resolvecontact();
			db.updateContact(contact);
			ContactsPanel.fillTable();
			dispose();
		}
	}
}
