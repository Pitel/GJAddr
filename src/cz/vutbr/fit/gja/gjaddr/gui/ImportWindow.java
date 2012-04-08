
package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.importexport.CsvIE;
import cz.vutbr.fit.gja.gjaddr.importexport.VCardIE;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import org.slf4j.LoggerFactory;

/**
 * Window for import management.
 *
 * TODO
 *  - messages in case of errors
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class ImportWindow extends JFrame implements ActionListener {

	/**
	 * List of action commands for radio buttons selection.
	 */
	private enum ActionCommands {
		NO_GROUP("no_group"),
		SELECTED_GROUP("selected_group"),
		NEW_GROUP("new_group"),
		V_CARD("v_card"),
		CSV("csv"),
		FACEBOOK("facebook"),
		GOOGLE("google");

		private String command;

		private ActionCommands(String command) {
			this.command = command;
		}
	}

	/**
	 * Radio buttons with selection of import group.
	 */
	private JRadioButton noGroupButton, selectGroupButton, newGroupButton;

	/**
	 * Radio buttons with selection of import format.
	 */
	private JRadioButton vCardButton, csvButton, facebookButton, googleButton;
	
	/**
	 * Group of radio buttons with selection of import group.
	 */
	private ButtonGroup groupOptionsButtonGroup, importFormatsButtonGroup;

	/**
	 * List of groups -- for exporting one specific group.
	 */
	private JComboBox groupsList;

	/**
	 * File chooser button and cancel button.
	 */
	private JButton openButton, cancelButton;

	/**
	 * File chooser for choosing file from which to import contacts.
	 */
	private JFileChooser fileChooser = new JFileChooser();

	/**
	 * Application database;
	 */
	private Database database = new Database();

	/**
	 * Constructor. Initializes the window.
	 */
	public ImportWindow() {
		super("Import");
		LoggerFactory.getLogger(this.getClass()).debug("Opening import window.");

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

		// import options header
		this.add(this.createImportOptionsHeader());

		// import options buttons
		JPanel noGroupsButtonPanel = this.createNoGroupOptionButton();
		JPanel groupsSelectionPanel = this.createSelectGroupOptionButton();
		JPanel newGroupButtonPanel = this.createNewGroupOptionButton();

		// create the group selection menu
		this.groupOptionsButtonGroup = new ButtonGroup();
		this.groupOptionsButtonGroup.add(this.noGroupButton);
		this.groupOptionsButtonGroup.add(this.selectGroupButton);
		this.groupOptionsButtonGroup.add(this.newGroupButton);

		// add all radio buttons to window
		this.add(noGroupsButtonPanel);
		this.add(groupsSelectionPanel);
		this.add(newGroupButtonPanel);

		// import formats header
		this.add(this.createImportFormatsHeader());

		// import formats options
		JPanel vCardButtonPanel = this.createVcardImportOptionButton();
		JPanel csvButtonPanel = this.createCsvImportOptionButton();
		JPanel facebookButtonPanel = this.createFacebookImportOptionButton();
		JPanel googleButtonPanel = this.createGoogleImportOptionButton();

		// create group for import formats
		this.importFormatsButtonGroup = new ButtonGroup();
		this.importFormatsButtonGroup.add(this.vCardButton);
		this.importFormatsButtonGroup.add(this.csvButton);
		this.importFormatsButtonGroup.add(this.facebookButton);
		this.importFormatsButtonGroup.add(this.googleButton);

		// add all format radio buttons to window
		this.add(vCardButtonPanel);
		this.add(csvButtonPanel);
		this.add(facebookButtonPanel);
		this.add(googleButtonPanel);

		// button panel
		this.add(createButtonPanel());

		// make window visible
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
		JLabel mainHeader = new JLabel("<html><h1>Contacts Import</h1></html>", JLabel.LEFT);
		mainHeaderPanel.add(mainHeader);
		mainHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return mainHeaderPanel;
	}

	/**
	 * Create import options header.
	 *
	 * @return
	 */
	private JPanel createImportOptionsHeader() {
		// import options header
		JPanel firstHeaderPanel = new JPanel(new GridLayout());
		JLabel firstHeader = new JLabel("<html><h2>Import to</h2></html>", JLabel.LEFT);
		firstHeaderPanel.add(firstHeader);
		firstHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return firstHeaderPanel;
	}

	/**
	 * Create no group option button for importing to no group.
	 *
	 * @return
	 */
	private JPanel createNoGroupOptionButton() {
		// no group button -- import to no group
		JPanel noGroupsButtonPanel = new JPanel(new GridLayout());
		this.noGroupButton = new JRadioButton("no group");
		this.noGroupButton.addActionListener(this);
		this.noGroupButton.setSelected(true);
		this.noGroupButton.setActionCommand(ActionCommands.NO_GROUP.toString());
		noGroupsButtonPanel.add(this.noGroupButton);
		noGroupsButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return noGroupsButtonPanel;
	}

	/**
	 * Create select group option button -- for importing to selected group.
	 *
	 * @return
	 */
	private JPanel createSelectGroupOptionButton() {
		// select group button -- import to one specific group
		this.selectGroupButton = new JRadioButton("selected group");
		this.selectGroupButton.addActionListener(this);
		this.selectGroupButton.setActionCommand(ActionCommands.SELECTED_GROUP.toString());
		// create combo box with list of groups
		this.groupsList = new JComboBox(this.getAllGroups());
		this.groupsList.addActionListener(this);
		// create panel with radio button for group selection and with groups
		JPanel groupsSelectionPanel = new JPanel(new GridLayout());
		groupsSelectionPanel.add(this.selectGroupButton);
		groupsSelectionPanel.add(this.groupsList);
		groupsSelectionPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return groupsSelectionPanel;
	}

	/**
	 * Import contacts to new group.
	 *
	 * @return
	 */
	private JPanel createNewGroupOptionButton() {
		// export only selected contacts button
		JPanel newGroupButtonPanel = new JPanel(new GridLayout());
		this.newGroupButton = new JRadioButton("new group");
		this.newGroupButton.addActionListener(this);
		this.newGroupButton.setActionCommand(ActionCommands.NEW_GROUP.toString());
		newGroupButtonPanel.add(this.newGroupButton);
		newGroupButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return newGroupButtonPanel;
	}

	/**
	 * Get list of all groups as string array.
	 *
	 * @return
	 */
	private String[] getAllGroups() {
		List<Group> allGroups = this.database.getAllGroups();
		String[] groupsArray = new String[allGroups.size()];
		for (int i = 0; i < allGroups.size(); i++) {
			groupsArray[i] = allGroups.get(i).getName();
		}
		return groupsArray;
	}

	/**
	 * Create import formats header.
	 *
	 * @return
	 */
	private JPanel createImportFormatsHeader() {
		// create second header -- for format selection
		JPanel secondHeaderPanel = new JPanel(new GridLayout());
		JLabel secondHeader = new JLabel("<html><h2>Import from</h2></html>", JLabel.LEFT);
		secondHeaderPanel.add(secondHeader);
		secondHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return secondHeaderPanel;
	}

	/**
	 * Create CSV import option -- for importing contacts from CSV.
	 *
	 * @return
	 */
	private JPanel createCsvImportOptionButton() {
		JPanel csvButtonPanel = new JPanel(new GridLayout());
		this.csvButton = new JRadioButton("GJAddr CSV");
		this.csvButton.addActionListener(this);
		this.csvButton.setActionCommand(ActionCommands.CSV.toString());
		csvButtonPanel.add(this.csvButton);
		csvButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return csvButtonPanel;
	}

	/**
	 * Create vCard import option -- for importing contacts from vCard format.
	 *
	 * @return
	 */
	private JPanel createVcardImportOptionButton() {
		JPanel vCardButtonPanel = new JPanel(new GridLayout());
		this.vCardButton = new JRadioButton("vCard");
		this.vCardButton.addActionListener(this);
		this.vCardButton.setSelected(true);
		this.vCardButton.setActionCommand(ActionCommands.V_CARD.toString());
		vCardButtonPanel.add(this.vCardButton);
		vCardButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return vCardButtonPanel;
	}

	/**
	 * Create Facebook import option -- for importing contacts from Facebook.
	 *
	 * @return
	 */
	private JPanel createFacebookImportOptionButton() {
		JPanel facebookButtonPanel = new JPanel(new GridLayout());
		this.facebookButton = new JRadioButton("Facebook");
		this.facebookButton.addActionListener(this);
		this.facebookButton.setActionCommand(ActionCommands.FACEBOOK.toString());
		facebookButtonPanel.add(this.facebookButton);
		facebookButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return facebookButtonPanel;
	}

	/**
	 * Create Google import option -- for importing contacts from Google contacts.
	 *
	 * @return
	 */
	private JPanel createGoogleImportOptionButton() {
		JPanel googleButtonPanel = new JPanel(new GridLayout());
		this.googleButton = new JRadioButton("Google");
		this.googleButton.addActionListener(this);
		this.googleButton.setActionCommand(ActionCommands.GOOGLE.toString());
		googleButtonPanel.add(this.googleButton);
		googleButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return googleButtonPanel;
	}

	/**
	 * Create button panel.
	 *
	 * @return
	 */
	private JPanel createButtonPanel() {
		// file chooser button
		this.openButton = new JButton("Import");
		this.openButton.addActionListener(this);
		this.openButton.setIcon(new ImageIcon(getClass().getResource("/res/folder.png")));
		this.openButton.setIconTextGap(10);
		// cancel button
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(this);
		this.cancelButton.setIcon(new ImageIcon(this.getClass().getResource("/res/cancel.png")));
		this.cancelButton.setIconTextGap(10);
		// panel for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPanel.add(this.openButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(this.cancelButton);
		buttonPanel.add(Box.createGlue());
		return buttonPanel;
	}

	/**
	 * Import contacts from file.
	 * 
	 * @throws HeadlessException
	 */
	private void importFromFile() {
		File file = this.fileChooser.getSelectedFile();
		LoggerFactory.getLogger(this.getClass()).info("Opening file [" + file.getName() + "] for import.");
		
		String importOption = this.groupOptionsButtonGroup.getSelection().getActionCommand();
		String importFormat = this.importFormatsButtonGroup.getSelection().getActionCommand();
		String importGroup = (String) this.groupsList.getSelectedItem();

		VCardIE vcardImport = new VCardIE();
		CsvIE csvImport = new CsvIE();
		
		try {
			if (importOption.equals(ActionCommands.NO_GROUP.toString())) {
				if (importFormat.equals(ActionCommands.V_CARD.toString())) {
					vcardImport.importContacts(file);
				} else {
					csvImport.importContacts(file);
				}
			} else if (importOption.equals(ActionCommands.SELECTED_GROUP.toString())) {
				if (importFormat.equals(ActionCommands.V_CARD.toString())) {
					vcardImport.importContactsToGroup(file, importGroup);
				} else {
					csvImport.importContacts(file, importGroup);
				}
			} else if (importOption.equals(ActionCommands.NEW_GROUP.toString())) {
				String s = (String) JOptionPane.showInputDialog(this, "New group name:",
						"New group creation", JOptionPane.PLAIN_MESSAGE, null, null, null);
				if ((s != null) && (s.length() > 0)) {
					if (importFormat.equals(ActionCommands.V_CARD.toString())) {
						vcardImport.importContactsToGroup(file, s);
					} else {
						csvImport.importContacts(file, s);
					}
				} else {
					if (importFormat.equals(ActionCommands.V_CARD.toString())) {
						vcardImport.importContacts(file);
					} else {
						csvImport.importContacts(file);
					}
				}
			}
		} catch (IOException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
		}
		
		this.dispose();
	}

	/**
	 * Import contacts from selected service.
	 */
	private void importFromService() {
		// TODO
	}

	/**
	 * Assign components actions.
	 * 
	 * @param ae
	 */
	public void actionPerformed(ActionEvent ae) {
		// Change import option if user clicks on groups list.
		if (ae.getSource() == this.groupsList) {
			this.noGroupButton.setSelected(false);
			this.selectGroupButton.setSelected(true);
		}
		// import button was clicked
		else if(ae.getSource() == this.openButton) {
			String exportFormat = this.importFormatsButtonGroup.getSelection().getActionCommand();
			// import from file
			if (exportFormat.equals(ActionCommands.V_CARD.toString())
					|| exportFormat.equals(ActionCommands.CSV.toString())) {
				int result = this.fileChooser.showOpenDialog(ImportWindow.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					this.importFromFile();
				} else {
					LoggerFactory.getLogger(this.getClass()).info("Opening file canceled by user.");
				}
			}
			// import from service
			else {
				this.importFromService();
			}
		}
		// cancel button was clicked -- dispose the window
		else if (ae.getSource() == this.cancelButton) {
			this.dispose();
		}
	}
}
