package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

/**
 * Contact editing window.
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class ContactWindow extends JFrame implements ActionListener {
	static final long serialVersionUID = 0;
	
	private final Database db = Database.getInstance();
	private JButton button;
	private JTextField nameField, surnameField, addressField, emailField, phoneField;

	public ContactWindow(boolean newContact) {
		super("Add Contact");
		
		if (newContact) {
			this.addContactWindow();
		}
		else {
			{
				ContactsPanel.fillTable(this.removeContacts());
			}			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
		if (e.getSource() == button) {
			final String name = nameField.getText();
			final String surname = surnameField.getText();
			final ArrayList<Address> addresses = new ArrayList<Address>();
			addresses.add(new Address(0, addressField.getText(), 0, null, 0, null));
			final ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();
			phones.add(new PhoneNumber(0, phoneField.getText()));
			final ArrayList<Email> emails = new ArrayList<Email>();
			emails.add(new Email(0, emailField.getText()));
			final ArrayList<Contact> contact = new ArrayList<Contact>();
			contact.add(new Contact(name, surname, null, null, null, null, null, addresses, phones, emails, null));
			//System.out.println(contact);

			ContactsPanel.fillTable(db.addNewContacts(contact));			
			dispose();
		}
	}
	
	/**
	 * Method for removing contact
	 */
	 List<Contact> removeContacts() {
		Contact contact = ContactsPanel.getSelectedContact();
		
		int delete = JOptionPane.showConfirmDialog(
			this,
			"Remove contact " + contact.getFullName() + "?",
			"Remove contact",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			new ImageIcon(getClass().getResource("/res/minus.png"), "-")
		);
		//System.out.println(delete);
		if (delete == 0) {			
			List<Contact> contactToRemove = new ArrayList<Contact>();
			contactToRemove.add(contact);
			return db.removeContacts(contactToRemove);
		}
		
		return null;
	}		
	 
	/**
	 * Method for removing contact
	 */
	 void addContactWindow() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e);
		}
		setIconImage(new ImageIcon(getClass().getResource("/res/plus.png"), "GJAddr").getImage());
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		final JPanel form = new JPanel(new GridLayout(0, 2));
		form.add(new JLabel("Name"));
		nameField = new JTextField();
		form.add(nameField);
		form.add(new JLabel("Surame"));
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
		button = new JButton("Add contact");
		button.addActionListener(this);
		add(button);
		//setResizable(false);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}			 
}
