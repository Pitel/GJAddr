package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Address;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Email;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.PhoneNumber;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * Contact editing window.
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class EditWindow extends JFrame implements ActionListener {
	static final long serialVersionUID = 0;
	private final Database db = new Database();
	private JButton button;
	private JTextField nameField, surnameField, addressField, emailField, phoneField;

	public EditWindow() {
		super("Add Contact");
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
			db.addNewContacts(contact);
			dispose();
		}
	}
}
