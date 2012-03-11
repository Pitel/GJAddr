
package cz.vutbr.fit.gja.gjaddr.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.slf4j.LoggerFactory;

/**
 * Main application window.
 *
 * @author xherrm01
 */
public class MainWindow extends JFrame implements ActionListener {
	
	/**
	 * Items of menu "File".
	 */
	private JMenuItem menuItemClose;
	
	/**
	 * Items of menu help.
	 */
	private JMenuItem menuItemHelp, menuItemAbout;
	
	/**
	 * Layouts.
	 */
	GridBagLayout layoutMain, layoutNested;
	
	/**
	 * Constraints for gridBagLayout.
	 */
	GridBagConstraints constraintsMain, constraintsNested;
  
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
		
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
			LoggerFactory.getLogger(MainWindow.class).error("Error setting native LAF: {}", e);
        }
		
		this.setSize(900, 400);
		this.setLocationRelativeTo(getRootPane());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("GJAddr");
		this.setJMenuBar(this.createMenu());

		Container container = this.getContentPane();
		this.layoutMain = new GridBagLayout();
		this.layoutNested = new GridBagLayout();
		this.constraintsMain = new GridBagConstraints();
		this.constraintsMain.fill = GridBagConstraints.BOTH;
		this.constraintsNested = new GridBagConstraints();
		this.constraintsNested.fill = GridBagConstraints.BOTH;
		this.constraintsNested.weightx = this.constraintsNested.weighty = 1.0;
		container.setLayout(this.layoutMain);
		
		container.add(this.createGroupsLabel());
		container.add(this.createGroupsColumn());
		container.add(this.createContactsSearchField());
		container.add(this.createContactsTable());
		container.add(this.createDetailsPanel());
	}
	
	/**
	 * Create and return application menu.
	 * 
	 * @return 
	 */
	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		this.menuItemClose = new JMenuItem("Quit", KeyEvent.VK_Q);
		this.menuItemClose.addActionListener(this);
		fileMenu.add(this.menuItemClose);
		
		this.menuItemHelp = new JMenuItem("Help", KeyEvent.VK_H);
		this.menuItemHelp.addActionListener(this);
		helpMenu.add(this.menuItemHelp);
		
		this.menuItemAbout = new JMenuItem("About", KeyEvent.VK_A);
		this.menuItemAbout.addActionListener(this);
		helpMenu.add(this.menuItemAbout);
		
		return menuBar;
	}
	
	/**
	 * Create label above groups column.
	 * 
	 * @return 
	 */
	private JPanel createGroupsLabel() {
		this.constraintsMain.ipady = 3;
		this.constraintsMain.gridx = 0;
		this.constraintsMain.gridy = 0;
		this.constraintsMain.weightx = 0.1;
		this.constraintsMain.weighty = 0.0;
		final ImageIcon icon = new ImageIcon("img/gradient.jpg");
		JPanel panel = new JPanel()
		{
			@Override
 			protected void paintComponent(Graphics g)
			{
				g.drawImage(icon.getImage(), 0,0, null);
				super.paintComponent(g);
			}
		};
		panel.setLayout(this.layoutNested);
		Label label = new Label("Groups");
		this.layoutNested.setConstraints(label, this.constraintsNested);
		panel.add(label);
		this.layoutMain.setConstraints(panel, constraintsMain);
		return panel;
	}
	
	/**
	 * 
	 * @return 
	 */
	private JPanel createGroupsColumn() {
		this.constraintsMain.gridx = 0;
		this.constraintsMain.gridy = 1;
		this.constraintsMain.ipady = 0;
		this.constraintsMain.weightx = 0.2;
		this.constraintsMain.weighty = 1.0;
		JPanel panel = new JPanel();
		panel.setLayout(this.layoutNested);
		this.layoutMain.setConstraints(panel, constraintsMain);
		DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("All");
		listModel.addElement("Work");
        listModel.addElement("Family");
        listModel.addElement("Friends");
		listModel.addElement("Acquaintances");
		listModel.addElement("School");
		listModel.addElement("Tennis class");
		listModel.addElement("Others");
		JList list = new JList(listModel);
		this.layoutNested.setConstraints(list, this.constraintsNested);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
		panel.add(list);
		return panel;
	}
	
	/**
	 * 
	 * @return 
	 */
	private JPanel createContactsSearchField() {
		this.constraintsMain.gridx = 1;
		this.constraintsMain.gridy = 0;
		this.constraintsMain.ipady = 3;
		this.constraintsMain.weightx = 1.0;
		this.constraintsMain.weighty = 0.0;
		JPanel panel = new JPanel();
		panel.setLayout(this.layoutNested);
		JTextField searchField = new JTextField(100);
		this.constraintsNested.gridx = 0;
		this.constraintsNested.weightx = this.constraintsNested.weighty = 1.0;
		this.layoutNested.setConstraints(searchField, this.constraintsNested);
		JButton search = new JButton("Search");
		this.constraintsNested.gridx = 1;
		this.constraintsNested.weightx = this.constraintsNested.weighty = 0.0;
		this.layoutNested.setConstraints(search, this.constraintsNested);
		JButton add = new JButton("Add");
		this.constraintsNested.gridx = 2;
		this.constraintsNested.weightx = this.constraintsNested.weighty = 0.0;
		this.layoutNested.setConstraints(add, this.constraintsNested);
		this.constraintsNested.gridx = 0;
		this.constraintsNested.weightx = this.constraintsNested.weighty = 1.0;
		panel.add(searchField);
		panel.add(add);
		panel.add(search);
		this.layoutMain.setConstraints(panel, constraintsMain);
		return panel;
	}
	
	/**
	 * 
	 * @return 
	 */
	private JPanel createContactsTable() {
		constraintsMain.gridx = 1;
		constraintsMain.gridy = 1;
		constraintsMain.ipady = 0;
		constraintsMain.weightx = 1.0;
		constraintsMain.weighty = 1.0;
		JPanel panel = new JPanel();
		panel.setLayout(this.layoutNested);
		layoutMain.setConstraints(panel, constraintsMain);
		
		String[] columnNames = {"First Name",
								"Last Name",
								"Email",
								"Phone",
								"Address"};
		
		Object[][] data = {
			{"Kathy", "Smith", "k.smith@gmail.com", "00447239437009", "Milton Keynes"},
			{"Sonia", "Newman", "s.newman@gmail.com", "00447847205234", "London"},
			{"Anthony", "Davenport", "a.davenport@gmail.com", "00447037482354", "Birmingham"},
			{"Isabella", "Distinto", "i.distinto@gmail.com", "00447019283775", "Milton Keynes"},
			{"Gioele", "Barabucci", "g.barabucci@gmail.com", "00447019283937", "Northampton"},
			{"Miriam", "Fernandez", "m.fernandez@gmail.com", "00447847563245", "Leighton Buzzard"},
			{"Hassan", "Saif", "h.saif@gmail.com", "00447039485736", "Bletchley"},
			{"Bogdan", "Kostov", "b.kostov@gmail.com", "00447958575646", "Milton Keynes"},
			{"Robbie", "Bayes", "r.a.b@gmail.com", "00447987654535", "Bletchley"},
			{"Harriet", "Cornish", "h.cornish@gmail.com", "00447887776665", "Milton Keynes"}
		};
		
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		this.layoutNested.setConstraints(scrollPane, this.constraintsNested);
		panel.add(scrollPane);
		return panel;
	}
	
	/**
	 * 
	 * @return 
	 */
	private JScrollPane createDetailsPanel() {
		constraintsMain.gridx = 2;
		constraintsMain.gridy = 0;
		constraintsMain.gridheight = 2;
		constraintsMain.weighty = 1.0;
		constraintsMain.weightx = 0.5;
		this.constraintsMain.anchor = GridBagConstraints.NORTH;
		JPanel panel = new JPanel();
		this.constraintsNested.weightx = this.constraintsNested.weighty = 1.0;
		panel.setLayout(this.layoutNested);
		panel.revalidate();
		
		this.constraintsNested.gridx = 0;	
		this.constraintsNested.gridheight = 2;
		this.constraintsNested.weighty = 0.0;
		this.constraintsNested.anchor = GridBagConstraints.NORTHEAST;
		this.constraintsNested.fill = GridBagConstraints.BOTH;
		JPanel portraitPanel = new JPanel();
		JLabel portrait = new JLabel(createImageIcon("img/portrait.jpg"));
		portraitPanel.add(portrait);
		this.layoutNested.setConstraints(portraitPanel, this.constraintsNested);
		panel.add(portraitPanel);
		panel.revalidate();
		
		this.constraintsNested.gridx = 1;	
		this.constraintsNested.gridheight = 1;
		this.constraintsNested.anchor = GridBagConstraints.NORTHWEST;
		JPanel namePanel = new JPanel();
		JLabel name = new JLabel("Isabella Distinto");
		namePanel.add(name);
		this.layoutNested.setConstraints(namePanel, this.constraintsNested);
		panel.add(namePanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 1;
		this.constraintsNested.gridx = 1;	
		this.constraintsNested.gridheight = 1;
		JLabel nameNote = new JLabel("Study Saturday ... :(");
		JPanel nameNotePanel = new JPanel();
		nameNotePanel.add(nameNote);
		this.layoutNested.setConstraints(nameNotePanel, this.constraintsNested);
		panel.add(nameNotePanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 3;
		this.constraintsNested.gridx = 0;
		this.constraintsNested.anchor = GridBagConstraints.EAST;
		JPanel emailLabelPanel = new JPanel();
		JLabel emailLabel = new JLabel("Personal");
		emailLabelPanel.add(emailLabel);
		this.layoutNested.setConstraints(emailLabelPanel, this.constraintsNested);
		panel.add(emailLabelPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 3;
		this.constraintsNested.gridx = 1;
		this.constraintsNested.anchor = GridBagConstraints.WEST;
		JLabel email = new JLabel("isabella.distinto@gmail.com");
		JPanel emailPanel = new JPanel();
		emailPanel.add(email);
		this.layoutNested.setConstraints(emailPanel, this.constraintsNested);
		panel.add(emailPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 4;
		this.constraintsNested.gridx = 0;
		this.constraintsNested.anchor = GridBagConstraints.EAST;
		JPanel chatLabelPanel = new JPanel();
		JLabel chatLabel = new JLabel("Chat");
		chatLabelPanel.add(chatLabel);
		this.layoutNested.setConstraints(chatLabelPanel, this.constraintsNested);
		panel.add(chatLabelPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 4;
		this.constraintsNested.gridx = 1;
		this.constraintsNested.anchor = GridBagConstraints.WEST;
		JLabel chat = new JLabel("i.distinto@jabber.com");
		JPanel chatPanel = new JPanel();
		chatPanel.add(chat);
		this.layoutNested.setConstraints(chatPanel, this.constraintsNested);
		panel.add(chatPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 5;
		this.constraintsNested.gridx = 0;
		this.constraintsNested.anchor = GridBagConstraints.EAST;
		JPanel workLabelPanel = new JPanel();
		JLabel workLabel = new JLabel("Work");
		workLabelPanel.add(workLabel);
		this.layoutNested.setConstraints(workLabelPanel, this.constraintsNested);
		panel.add(workLabelPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 5;
		this.constraintsNested.gridx = 1;
		this.constraintsNested.anchor = GridBagConstraints.WEST;
		JLabel work = new JLabel("i.distinto@open.ac.uk");
		JPanel workPanel = new JPanel();
		workPanel.add(work);
		this.layoutNested.setConstraints(workPanel, this.constraintsNested);
		panel.add(workPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 6;
		this.constraintsNested.gridx = 0;
		this.constraintsNested.anchor = GridBagConstraints.EAST;
		JPanel homeLabelPanel = new JPanel();
		JLabel homeLabel = new JLabel("Home");
		homeLabelPanel.add(homeLabel);
		this.layoutNested.setConstraints(homeLabelPanel, this.constraintsNested);
		panel.add(homeLabelPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 6;
		this.constraintsNested.gridx = 1;
		this.constraintsNested.anchor = GridBagConstraints.WEST;
		JLabel home = new JLabel("07 907 909 284");
		JPanel homePanel = new JPanel();
		homePanel.add(home);
		this.layoutNested.setConstraints(homePanel, this.constraintsNested);
		panel.add(homePanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 7;
		this.constraintsNested.gridx = 0;
		this.constraintsNested.anchor = GridBagConstraints.EAST;
		JPanel bdayLabelPanel = new JPanel();
		JLabel bdayLabel = new JLabel("Birthday");
		bdayLabelPanel.add(bdayLabel);
		this.layoutNested.setConstraints(bdayLabelPanel, this.constraintsNested);
		panel.add(bdayLabelPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 7;
		this.constraintsNested.gridx = 1;
		this.constraintsNested.anchor = GridBagConstraints.WEST;
		JLabel bday = new JLabel("09 June 2012");
		JPanel bdayPanel = new JPanel();
		bdayPanel.add(bday);
		this.layoutNested.setConstraints(bdayPanel, this.constraintsNested);
		panel.add(bdayPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 8;
		this.constraintsNested.gridx = 0;
		this.constraintsNested.anchor = GridBagConstraints.EAST;
		JPanel addressLabelPanel = new JPanel();
		JLabel addressLabel = new JLabel("Address");
		addressLabelPanel.add(addressLabel);
		this.layoutNested.setConstraints(addressLabelPanel, this.constraintsNested);
		panel.add(addressLabelPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 8;
		this.constraintsNested.gridx = 1;
		this.constraintsNested.anchor = GridBagConstraints.WEST;
		StringBuilder builder = new StringBuilder("<html>");
		builder.append("5 Woburn Road<br />");
		builder.append("Woughton on the Green<br />");
		builder.append("Milton Keynes<br />");
		builder.append("MK8 8AA<br /></html>");
		JLabel address = new JLabel();
		address.setText(builder.toString());
		JPanel addressPanel = new JPanel();
		addressPanel.add(address);
		this.layoutNested.setConstraints(addressPanel, this.constraintsNested);
		panel.add(addressPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 9;
		this.constraintsNested.gridx = 0;
		this.constraintsNested.anchor = GridBagConstraints.EAST;
		JPanel noteLabelPanel = new JPanel();
		JLabel noteLabel = new JLabel("Note");
		noteLabelPanel.add(noteLabel);
		this.layoutNested.setConstraints(noteLabelPanel, this.constraintsNested);
		panel.add(noteLabelPanel);
		panel.revalidate();
		
		this.constraintsNested.gridy = 9;
		this.constraintsNested.gridx = 1;
		this.constraintsNested.anchor = GridBagConstraints.WEST;
		JTextArea note = new JTextArea();
		note.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod "
				+ "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
				+ "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
		note.setLineWrap(true);
		note.setWrapStyleWord(true);
		JPanel notePanel = new JPanel();
		notePanel.add(note);
		this.layoutNested.setConstraints(notePanel, this.constraintsNested);
		panel.add(notePanel);
		panel.revalidate();
		
		JScrollPane panelScroll = new JScrollPane(panel);
		this.constraintsMain.anchor = GridBagConstraints.NORTH;
		layoutMain.setConstraints(panelScroll, constraintsMain);
		return panelScroll;
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
		try {
			//java.net.URL imgURL = MainWindow.class.getResource(path);
			File file = new File(path);
			URI imgUri = file.toURI();
			URL imgURL = imgUri.toURL();
			if (imgURL != null) {
				return new ImageIcon(imgURL);
			} else {
				LoggerFactory.getLogger(MainWindow.class).error("Couldn't find file: " + path);
				return null;
			}
		} catch (MalformedURLException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
    }
	
	/**
	 * Method binding functionality to difference actions performed (e.g. if button 
	 * was pressed etc.).
	 * 
	 * @param e 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/**
		 * Menu item that will close the application.
		 */
		if (e.getSource() == this.menuItemClose) {
			LoggerFactory.getLogger(MainWindow.class).info("Closing application.");
			System.exit(0);
		}
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
