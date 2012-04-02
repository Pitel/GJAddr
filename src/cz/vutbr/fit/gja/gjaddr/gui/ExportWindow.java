
package cz.vutbr.fit.gja.gjaddr.gui;

import a_vcard.android.syncml.pim.vcard.VCardException;
import cz.vutbr.fit.gja.gjaddr.importexport.VCard;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.DatabaseGroups;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import org.slf4j.LoggerFactory;

/**
 * Window for export management.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class ExportWindow extends JFrame implements ActionListener {

	/**
	 * Radio buttons with selection of export group.
	 */
	private JRadioButton noGroupButton, selectGroupButton, newGroupButton;

	/**
	 * Radio buttons with selection of export format.
	 */
	private JRadioButton vCardButton, csvButton;

	/**
	 * Group of radio buttons with selection of export group.
	 */
	private ButtonGroup groupButtonGroup, exportFormatButtonGroup;

	private JComboBox groupsList;

	private JButton openButton, cancelButton;

	private JFileChooser fileChooser;

	public ExportWindow() {
		super("Export");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e);
		}

		ImageIcon icon = new ImageIcon(getClass().getResource("/res/icon.png"), "GJAddr");
		this.setIconImage(icon.getImage());

		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

		JLabel mainHeader = new JLabel("<html><h1>Contacts Export</h1></html>", JLabel.LEFT);
		this.add(mainHeader);

		JLabel firstHeader = new JLabel("<html><h2>Export options</h2></html>", JLabel.LEFT);
		this.add(firstHeader);

		// create the group selection menu
		this.noGroupButton = new JRadioButton("all contacts");
		this.noGroupButton.addActionListener(this);
        this.noGroupButton.setSelected(true);
		this.noGroupButton.setActionCommand("noGroup");

		this.selectGroupButton = new JRadioButton("selected group");
		this.selectGroupButton.addActionListener(this);
		this.selectGroupButton.setActionCommand("selectedGroup");

		this.newGroupButton = new JRadioButton("selected contacts");
		this.newGroupButton.addActionListener(this);
		this.newGroupButton.setActionCommand("newGroup");

		this.groupButtonGroup = new ButtonGroup();
		this.groupButtonGroup.add(this.noGroupButton);
		this.groupButtonGroup.add(this.selectGroupButton);
		this.groupButtonGroup.add(this.newGroupButton);

		DatabaseGroups dbGroups = new DatabaseGroups();
		List<Group> dbGroupsAsList = dbGroups.getAllGroups();
		String[] dbGroupsAsArray = new String[dbGroupsAsList.size()];
		for (int i = 0; i < dbGroupsAsList.size(); i++) {
			dbGroupsAsArray[i] = dbGroupsAsList.get(i).getName();
		}
		this.groupsList = new JComboBox(dbGroupsAsArray);
		this.groupsList.addActionListener(this);
		JPanel groupsSelectionPanel = new JPanel();
		groupsSelectionPanel.add(this.selectGroupButton);
		groupsSelectionPanel.add(this.groupsList);

		this.add(this.noGroupButton);
		this.add(groupsSelectionPanel);
		this.add(this.newGroupButton);

		JLabel secondHeader = new JLabel("<html><h2>Export to format</h2></html>", JLabel.LEFT);
		this.add(secondHeader);

		// create the format selection menu
		this.vCardButton = new JRadioButton("vCard");
		this.vCardButton.addActionListener(this);
		this.vCardButton.setSelected(true);
		this.vCardButton.setActionCommand("vcard");
		
		this.csvButton = new JRadioButton("CSV");
		this.csvButton.addActionListener(this);
		this.csvButton.setActionCommand("csv");

		this.exportFormatButtonGroup = new ButtonGroup();
		this.exportFormatButtonGroup.add(this.vCardButton);
		this.exportFormatButtonGroup.add(this.csvButton);

		this.add(this.vCardButton);
		this.add(this.csvButton);

		this.openButton = new JButton("Select file");
		this.openButton.addActionListener(this);
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(this);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(this.openButton);
		buttonsPanel.add(this.cancelButton);
		this.add(buttonsPanel);
		this.fileChooser = new JFileChooser();

		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setPreferredSize(new Dimension(400, 500));
		this.pack();
		this.setVisible(true);
	}

	/**
	 *
	 * @param ae
	 */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == this.openButton) {
			int result = this.fileChooser.showSaveDialog(ExportWindow.this);
			if (result == JFileChooser.APPROVE_OPTION) {
                File file = this.fileChooser.getSelectedFile();
				String actionCommand = this.groupButtonGroup.getSelection().getActionCommand();
				LoggerFactory.getLogger(this.getClass()).debug(actionCommand);
				String format = this.exportFormatButtonGroup.getSelection().getActionCommand();
				LoggerFactory.getLogger(this.getClass()).debug(format);
				String option = (String) this.groupsList.getSelectedItem();
				LoggerFactory.getLogger(this.getClass()).debug(option);
                VCard vcardImport = new VCard();
				try {
					if (actionCommand.equals("noGroup")) {
						Database db = new Database();
						List<Contact> allContacts = db.getAllContacts();
						if (format.equals("vcard")) {
							try {
								vcardImport.exportContacts(file, allContacts);
							} catch (FileNotFoundException ex) {
								LoggerFactory.getLogger(this.getClass()).error(ex.toString());
							} catch (IOException ex) {
								LoggerFactory.getLogger(this.getClass()).error(ex.toString());
							}
							this.dispose();
						} else {
							LoggerFactory.getLogger(this.getClass()).error("Export to CSV isn't yet supported.");
						}
					} else if (actionCommand.equals("selectedGroup")) {
						Database db = new Database();
						Group selectedGroup = null;
						for (Group g : db.getAllGroups()) {
							if (g.getName().equals(option)) {
								selectedGroup = g;
							}
						}
						List<Group> selectedGroups = new ArrayList<Group>();
						selectedGroups.add(selectedGroup);
						List<Contact> allContactsFromGroup = db.getAllContactsFromGroup(selectedGroups);
						try {
							vcardImport.exportContacts(file, allContactsFromGroup);
						} catch (FileNotFoundException ex) {
							LoggerFactory.getLogger(this.getClass()).error(ex.toString());
						} catch (IOException ex) {
							LoggerFactory.getLogger(this.getClass()).error(ex.toString());
						}
					} else {
						// TODO
					}
				} catch (VCardException ex) {
					LoggerFactory.getLogger(this.getClass()).error(ex.toString());
				}
                LoggerFactory.getLogger(this.getClass()).info("Opening file [" + file.getName() + "] for import.");
				this.dispose();
            } else {
                LoggerFactory.getLogger(this.getClass()).info("Opening file canceled by user.");
            }
		} else if (ae.getSource() == this.cancelButton) {
			this.dispose();
		}
	}

}
