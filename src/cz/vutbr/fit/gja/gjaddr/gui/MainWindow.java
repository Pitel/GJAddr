
package cz.vutbr.fit.gja.gjaddr.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Main application window.
 *
 * @author xherrm01
 */
public class MainWindow extends JFrame {
	
	// TODO presunout do nejake tridy s konstantami?
	private String appTitle = "GJAddr";
  
	/**
	 * Class constructor. Will initialize the window.
	 */
	public MainWindow() {
		super();
		init();
	}
  
	/**
	 * Initialize the application window -- set layout and place components inside it.
	 */
	private void init() {
		this.setSize(700, 400);
		this.setLocationRelativeTo(getRootPane());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(this.appTitle);
		this.setJMenuBar(this.createMenu());

		Container container = this.getContentPane();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		container.setLayout(gridbag);
		constraints.ipady = 30;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.2;
		constraints.weighty = 0.0;
		constraints.fill = GridBagConstraints.BOTH;
		Panel groupsLabelPanel = new Panel();
		groupsLabelPanel.setLayout(new GridLayout());
		groupsLabelPanel.add(new Label("Groups"));
		gridbag.setConstraints(groupsLabelPanel, constraints);
		container.add(groupsLabelPanel);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.ipady = 0;
		constraints.weightx = 0.2;
		constraints.weighty = 1.0;
		Panel groupsPanel = new Panel();
		groupsPanel.setLayout(new GridLayout());
		gridbag.setConstraints(groupsPanel, constraints);
		DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("Work");
        listModel.addElement("Family");
        listModel.addElement("Friends");
		listModel.addElement("Acquaintances");
		listModel.addElement("School");
		listModel.addElement("Tennis class");
		listModel.addElement("Others");
		JList list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
		JScrollPane groupsColumn = new JScrollPane(list);
		groupsPanel.add(groupsColumn);
		container.add(groupsPanel);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.ipady = 30;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		Panel contactsLabelPanel = new Panel();
		contactsLabelPanel.setLayout(new GridLayout());
		contactsLabelPanel.add(new Label("Contacts"));
		gridbag.setConstraints(contactsLabelPanel, constraints);
		container.add(contactsLabelPanel);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.ipady = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		Panel contactsPanel = new Panel();
		contactsPanel.setLayout(new GridLayout());
		gridbag.setConstraints(contactsPanel, constraints);
		DefaultListModel listModelContacts = new DefaultListModel();
		listModelContacts.addElement("Sonia Newman");
        listModelContacts.addElement("Anthony Davenport");
        listModelContacts.addElement("Thomal Ullman");
		listModelContacts.addElement("Isabella Distinto");
		listModelContacts.addElement("Gioele Barabucci");
		listModelContacts.addElement("Hassan Saif");
		listModelContacts.addElement("Miriam Fernandez");
		listModelContacts.addElement("Magdalena Krygiel");
		listModelContacts.addElement("Tomas Korec");
		listModelContacts.addElement("Carlos Pedrinaci");
		listModelContacts.addElement("Ana DeLido");
		listModelContacts.addElement("Vojtech Robotka");
		listModelContacts.addElement("Chris Valentine");
		listModelContacts.addElement("Peter Scott");
		listModelContacts.addElement("Bogdan Kostov");
		listModelContacts.addElement("Robbie Bays");
		listModelContacts.addElement("Harriet Cornish");
		listModelContacts.addElement("Peter Knoth");
		JList listContacts = new JList(listModelContacts);
        listContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listContacts.setSelectedIndex(0);
		JScrollPane contactsColumn = new JScrollPane(listContacts);
		contactsPanel.add(contactsColumn);
		container.add(contactsPanel);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridheight = 2;
		constraints.weighty = 1.0;
		constraints.weightx = 0.5;
		Button buttonDetails = new Button("Details");
		gridbag.setConstraints(buttonDetails, constraints);
		container.add(buttonDetails);
	}
	
	/**
	 * 
	 * @return 
	 */
	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem closeItem = new JMenuItem("Quit GJArrd", KeyEvent.VK_Q);
		closeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO is this correct?
				System.exit(0);
			}
		});
		fileMenu.add(closeItem);
		menuBar.add(fileMenu);
		return menuBar;
	}
	
	/**
	 * 
	 * @param title
	 * @return 
	 */
	private JPanel createColumn(String title) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new Label(title));
		JScrollPane column = new JScrollPane();
		panel.add(column);
		return panel;
	}
}
