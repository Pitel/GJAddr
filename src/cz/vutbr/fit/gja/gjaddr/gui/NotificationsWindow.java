
package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import java.awt.GridLayout;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.slf4j.LoggerFactory;

/**
 * Display upcoming birthday.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class NotificationsWindow extends JFrame {

	/**
	 * Constructor.
	 * 
	 * @param contacts
	 */
	public NotificationsWindow(List<Contact> contacts) {
		super("Import");
		LoggerFactory.getLogger(this.getClass()).debug("Opening notifications window.");

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

		for (Contact c : contacts) {
			this.add(this.createLine(c));
		}

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
}
