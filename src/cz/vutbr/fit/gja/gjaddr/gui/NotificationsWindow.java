
package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import org.slf4j.LoggerFactory;

/**
 * Display upcoming birthday.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class NotificationsWindow extends JFrame implements ActionListener {
	static final long serialVersionUID = 0;
	/**
	 * Window buttons.
	 */
	JButton noShowButton, okButton;

	/**
	 * Contacts shown in notifications window.
	 */
	List<Contact> contacts;

	/**
	 * Instance of database.
	 */
	private Database db = Database.getInstance();

	/**
	 * Constructor.
	 * 
	 * @param contacts
	 */
	public NotificationsWindow(List<Contact> contacts) {
		super("Import");
		LoggerFactory.getLogger(this.getClass()).debug("Opening notifications window.");

		// save the list of contacts with bday
		this.contacts = contacts;

		// set window apearance
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e);
		}

		// set application icon
		ImageIcon icon = new ImageIcon(getClass().getResource("/res/icon.png"), "GJAddr");
		this.setIconImage(icon.getImage());

		// set page layout
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

		// main header
		this.add(this.createMainHeader());

		// add all contacts
		for (Contact c : contacts) {
			this.add(this.createLine(c));
		}

		// add buttons
		this.add(this.createButtonPanel());

		// make window visible
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Create main window header.
	 *
	 * @return
	 */
	private JPanel createMainHeader() {
		JPanel mainHeaderPanel = new JPanel(new GridLayout());
		JLabel mainHeader = new JLabel("<html><h3>People with birthday<br />next month</h3></html>", JLabel.LEFT);
		mainHeaderPanel.add(mainHeader);
		mainHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return mainHeaderPanel;
	}

	/**
	 * Create line consisting of name and bday.
	 * 
	 * @param c
	 * @return
	 */
	private JPanel createLine(Contact c) {
		JPanel panel = new JPanel(new GridLayout());
		Format formatter = new SimpleDateFormat("d.M.yyyy");
		JLabel name = new JLabel(c.getFullName());
		JLabel bday = new JLabel(formatter.format(c.getDateOfBirth()));
		panel.add(name);
		panel.add(bday);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return panel;
	}

	/**
	 * Create button panel.
	 *
	 * @return
	 */
	private JPanel createButtonPanel() {
		this.noShowButton = new JButton("Don't show again");
		this.noShowButton.addActionListener(this);
		this.noShowButton.setIcon(new ImageIcon(getClass().getResource("/res/cancel.png")));
		this.noShowButton.setIconTextGap(10);
		// OK button
		this.okButton = new JButton("OK");
		this.okButton.addActionListener(this);
		this.okButton.setIcon(new ImageIcon(getClass().getResource("/res/confirm.png")));
		this.okButton.setIconTextGap(10);
		// panel for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPanel.add(this.okButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(this.noShowButton);
		buttonPanel.add(Box.createGlue());
		return buttonPanel;
	}

	/**
	 * Assign components actions.
	 * 
	 * @param ae
	 */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == this.noShowButton) {
			for (Contact c : this.contacts) {
				c.disableBdayShowing();
				this.db.updateContact(c);
			}
			this.dispose();
		} else if (ae.getSource() == this.okButton) {
			this.dispose();
		}
	}
}
