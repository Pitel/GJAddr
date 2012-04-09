
package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.importexport.CsvImportExport;
import cz.vutbr.fit.gja.gjaddr.importexport.VCardImportExport;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import org.slf4j.LoggerFactory;

/**
 * Window for export management.
 *
 * TODO
 *  - messages in case of errors
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class ExportWindow extends JFrame implements ActionListener {

	/**
	 * List of action commands for radio buttons selection.
	 */
	private enum ActionCommands {
		NO_GROUP("no_group"),
		SELECTED_GROUP("selected_group"),
		SELECTED_CONTACTS("selected_contacts"),
		V_CARD("v_card"),
		CSV("csv");

		private String command;

		private ActionCommands(String command) {
			this.command = command;
		}
	}

	/**
	 * Radio buttons with selection of export group.
	 */
	private JRadioButton noGroupButton, selectGroupButton, selectedContactsButton;

	/**
	 * Radio buttons with selection of export format.
	 */
	private JRadioButton vCardButton, csvButton;

	/**
	 * Group of radio buttons with selection of export group.
	 */
	private ButtonGroup groupButtonGroup, exportFormatButtonGroup;

	/**
	 * List of groups -- for exporting one specific group.
	 */
	private JComboBox groupsList;

	/**
	 * File chooser button and cancel button.
	 */
	private JButton openButton, cancelButton;

	/**
	 * File chooser for choosing file where to save the exported contacts.
	 */
	private JFileChooser fileChooser = new JFileChooser();

	/**
	 * Application database;
	 */
	private Database database = Database.getInstance();

	/**
	 * Constructor. Initializes the window.
	 */
	public ExportWindow() {
		super("Export");
		LoggerFactory.getLogger(this.getClass()).debug("Opening export window.");

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

		// export options header
		this.add(this.createExportOptionsHeader());

		// export options buttons
		JPanel noGroupsButtonPanel = this.createNoGroupOptionButton();
		JPanel groupsSelectionPanel = this.createSelectGroupOptionButton();
		JPanel selectedContactsButtonPanel = this.createSelectedContactsOptionButton();

		// create the group selection menu
		this.groupButtonGroup = new ButtonGroup();
		this.groupButtonGroup.add(this.noGroupButton);
		this.groupButtonGroup.add(this.selectGroupButton);
		this.groupButtonGroup.add(this.selectedContactsButton);

		// add all radio buttons to window
		this.add(noGroupsButtonPanel);
		this.add(groupsSelectionPanel);
		this.add(selectedContactsButtonPanel);

		// export formats header
		this.add(this.createExportFormatsHeader());

		// export formats options
		JPanel vCardButtonPanel = createVcardExportOptionButton();
		JPanel csvButtonPanel = createCsvExportOptionButton();

		// create group for export formats
		this.exportFormatButtonGroup = new ButtonGroup();
		this.exportFormatButtonGroup.add(this.vCardButton);
		this.exportFormatButtonGroup.add(this.csvButton);

		// add all format radio buttons to window
		this.add(vCardButtonPanel);
		this.add(csvButtonPanel);

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
		JLabel mainHeader = new JLabel("<html><h1>Contacts Export</h1></html>", JLabel.LEFT);
		mainHeaderPanel.add(mainHeader);
		mainHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return mainHeaderPanel;
	}

	/**
	 * Create export options header.
	 *
	 * @return
	 */
	private JPanel createExportOptionsHeader() {
		// export options header
		JPanel firstHeaderPanel = new JPanel(new GridLayout());
		JLabel firstHeader = new JLabel("<html><h2>Export options</h2></html>", JLabel.LEFT);
		firstHeaderPanel.add(firstHeader);
		firstHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return firstHeaderPanel;
	}

	/**
	 * Create no group option button for exporting all contacts.
	 *
	 * @return
	 */
	private JPanel createNoGroupOptionButton() {
		// no group button -- export all contacts
		JPanel noGroupsButtonPanel = new JPanel(new GridLayout());
		this.noGroupButton = new JRadioButton("all contacts");
		this.noGroupButton.addActionListener(this);
		this.noGroupButton.setSelected(true);
		this.noGroupButton.setActionCommand(ActionCommands.NO_GROUP.toString());
		noGroupsButtonPanel.add(this.noGroupButton);
		noGroupsButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return noGroupsButtonPanel;
	}

	/**
	 * Create select group option button -- for exporting selected group.
	 *
	 * @return
	 */
	private JPanel createSelectGroupOptionButton() {
		// select group button -- export one specific group
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
	 * Create selected contacts option button -- for exporting only selected
	 * contacts.
	 * 
	 * @return
	 */
	private JPanel createSelectedContactsOptionButton() {
		// export only selected contacts button
		JPanel selectedContactsButtonPanel = new JPanel(new GridLayout());
		this.selectedContactsButton = new JRadioButton("selected contacts");
		this.selectedContactsButton.addActionListener(this);
		this.selectedContactsButton.setActionCommand(ActionCommands.SELECTED_CONTACTS.toString());
		selectedContactsButtonPanel.add(this.selectedContactsButton);
		selectedContactsButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return selectedContactsButtonPanel;
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
	 * Create export formats header.
	 * 
	 * @return
	 */
	private JPanel createExportFormatsHeader() {
		// create second header -- for format selection
		JPanel secondHeaderPanel = new JPanel(new GridLayout());
		JLabel secondHeader = new JLabel("<html><h2>Export to format</h2></html>", JLabel.LEFT);
		secondHeaderPanel.add(secondHeader);
		secondHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return secondHeaderPanel;
	}

	/**
	 * Create CSV export option -- for exporting contacts to CSV.
	 *
	 * @return
	 */
	private JPanel createCsvExportOptionButton() {
		// create button for CSV format
		JPanel csvButtonPanel = new JPanel(new GridLayout());
		this.csvButton = new JRadioButton("GJAddr CSV");
		this.csvButton.addActionListener(this);
		this.csvButton.setActionCommand(ActionCommands.CSV.toString());
		csvButtonPanel.add(this.csvButton);
		csvButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return csvButtonPanel;
	}

	/**
	 * Create vCard export option -- for exporting contacts to vCard format.
	 * 
	 * @return
	 */
	private JPanel createVcardExportOptionButton() {
		// create the format selection menu -- vCard option
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
	 * Create button panel.
	 * 
	 * @return
	 */
	private JPanel createButtonPanel() {
		// file chooser button
		this.openButton = new JButton("Export");
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
	 * Export contacts with selected options.
	 */
	private void doExport() {
		File file = this.fileChooser.getSelectedFile();
		LoggerFactory.getLogger(this.getClass()).info("Opening file [" + file.getName() + "] for export.");
		
		String exportOption = this.groupButtonGroup.getSelection().getActionCommand();
		String exportFormat = this.exportFormatButtonGroup.getSelection().getActionCommand();
		String exportGroup = (String) this.groupsList.getSelectedItem();

		VCardImportExport vcardExport = new VCardImportExport();
		CsvImportExport csvExport = new CsvImportExport();

		try {
			if (exportOption.equals(ActionCommands.NO_GROUP.toString())) {
				if (exportFormat.equals(ActionCommands.V_CARD.toString())) {
					vcardExport.exportContacts(file, this.database.getAllContacts());
				} else if (exportFormat.equals(ActionCommands.CSV.toString())) {
					csvExport.exportContacts(file, this.database.getAllContacts());
				}
			} else if (exportOption.equals(ActionCommands.SELECTED_GROUP.toString())) {
				if (exportFormat.equals(ActionCommands.V_CARD.toString())) {
					vcardExport.exportContacts(file, this.getContactsFromGroup(exportGroup));
				} else if (exportFormat.equals(ActionCommands.CSV.toString())) {
					csvExport.exportContacts(file, this.getContactsFromGroup(exportGroup));
				}
			} else {
				LoggerFactory.getLogger(this.getClass()).error("Export option not yet supported!");
			}
		} catch (FileNotFoundException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
		} catch (IOException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
		}

		this.dispose();
	}

	/**
	 * Get all contacts from one group specified by it's name.
	 * 
	 * @param group
	 * @return
	 */
	private List<Contact> getContactsFromGroup(String group) {
		Group selectedGroup = null;
		for (Group g : this.database.getAllGroups()) {
			if (g.getName().equals(group)) {
				selectedGroup = g;
			}
		}
		List<Group> selectedGroups = new ArrayList<Group>();
		selectedGroups.add(selectedGroup);
		return this.database.getAllContactsFromGroup(selectedGroups);
	}

	/**
	 * Assign components actions.
	 *
	 * @param ae
	 */
	public void actionPerformed(ActionEvent ae) {
		// Change export option if user clicks on groups list.
		if (ae.getSource() == this.groupsList) {
			this.noGroupButton.setSelected(false);
			this.selectGroupButton.setSelected(true);
		}
		// export button was clicked
		else if (ae.getSource() == this.openButton) {
			int result = this.fileChooser.showSaveDialog(ExportWindow.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				this.doExport();
            } else {
                LoggerFactory.getLogger(this.getClass()).info("Opening file canceled by user.");
            }
		}
		// cancel button was clicked -- dispose the window
		else if (ae.getSource() == this.cancelButton) {
			this.dispose();
		}
	}
}
