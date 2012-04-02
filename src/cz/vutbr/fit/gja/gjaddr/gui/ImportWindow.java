
package cz.vutbr.fit.gja.gjaddr.gui;

import a_vcard.android.syncml.pim.vcard.VCardException;
import cz.vutbr.fit.gja.gjaddr.importexport.VCard;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.DatabaseGroups;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
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
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class ImportWindow extends JFrame implements ActionListener {

	/**
	 * Radio buttons with selection of import group.
	 */
	private JRadioButton noGroupButton, selectGroupButton, newGroupButton;
	
	/**
	 * Group of radio buttons with selection of import group.
	 */
	private ButtonGroup buttonGroup;

	private JComboBox groupsList;

	private JButton openButton, cancelButton;

	private JFileChooser fileChooser;

	public ImportWindow() {
		super("Import");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e);
		}

		ImageIcon icon = new ImageIcon(getClass().getResource("/res/icon.png"), "GJAddr");
		this.setIconImage(icon.getImage());

		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

		JLabel mainHeader = new JLabel("<html><h1>Contacts Import</h1></html>", JLabel.LEFT);
		this.add(mainHeader);

		JLabel firstHeader = new JLabel("<html><h2>Import to group</h2></html>", JLabel.LEFT);
		this.add(firstHeader);

		// create the group selection menu
		this.noGroupButton = new JRadioButton("all contacts");
		this.noGroupButton.addActionListener(this);
        this.noGroupButton.setSelected(true);
		this.noGroupButton.setActionCommand("noGroup");

		this.selectGroupButton = new JRadioButton("selected group");
		this.selectGroupButton.addActionListener(this);
		this.selectGroupButton.setActionCommand("selectedGroup");

		this.newGroupButton = new JRadioButton("new group");
		this.newGroupButton.addActionListener(this);
		this.newGroupButton.setActionCommand("newGroup");

		this.buttonGroup = new ButtonGroup();
		this.buttonGroup.add(this.noGroupButton);
		this.buttonGroup.add(this.selectGroupButton);
		this.buttonGroup.add(this.newGroupButton);

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

		JLabel secondHeader = new JLabel("<html><h2>Import file format</h2></html>", JLabel.LEFT);
		this.add(secondHeader);

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
			int result = this.fileChooser.showOpenDialog(ImportWindow.this);
			if (result == JFileChooser.APPROVE_OPTION) {
                File file = this.fileChooser.getSelectedFile();
				String actionCommand = this.buttonGroup.getSelection().getActionCommand();
				LoggerFactory.getLogger(this.getClass()).debug(actionCommand);
				String option = (String) this.groupsList.getSelectedItem();
				LoggerFactory.getLogger(this.getClass()).debug(option);
                VCard vcardImport = new VCard();
				try {
					if (actionCommand.equals("noGroup")) {
						vcardImport.importContacts(file);
					} else if (actionCommand.equals("selectedGroup")) {
						LoggerFactory.getLogger(this.getClass()).debug("Importing contacts to: " + option);
						vcardImport.importContactsToGroup(file, option);
					} else {
						String s = (String) JOptionPane.showInputDialog(this, "New group name:",
								"New group creation", JOptionPane.PLAIN_MESSAGE, null, null, null);
						if ((s != null) && (s.length() > 0)) {
							vcardImport.importContactsToGroup(file, s);
						} else {
							vcardImport.importContacts(file);
						}
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
