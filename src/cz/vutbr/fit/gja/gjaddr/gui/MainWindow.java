package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.swingx.MultiSplitLayout.Divider;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import org.jdesktop.swingx.MultiSplitPane;

/**
 * Main application window.
 *
 * @author xherrm01
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
public class MainWindow extends JFrame implements ActionListener, DocumentListener {
	private final Database db = new Database();
	private JMenuItem menuItemClose, menuItemHelp, menuItemAbout, menuItemImport, menuItemExport;
	private final JTextField searchField = new JTextField();
	private final DefaultListModel groupsListModel = new DefaultListModel();

	/**
	 * Creates the main window.
	 */
	public MainWindow() {
		super("GJAddr");
		//cz.vutbr.fit.gja.gjaddr.persistancelayer.TestData.fillTestingData(db);	//DEBUG
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		this.setIconImage(new ImageIcon(getClass().getResource("/res/icon.png")).getImage());
		this.setJMenuBar(this.createMenu());
		Container container = this.getContentPane();
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.add(new JButton(new ImageIcon(getClass().getResource("/res/plus.png"), "+")));
		searchField.getDocument().addDocumentListener(this);
		toolbar.add(searchField);
		container.add(toolbar, BorderLayout.NORTH);
		Split model = new Split();
		Leaf groupsLeaf = new Leaf("groups");
		Leaf contactsLeaf = new Leaf("contacts");
		Leaf detailLeaf = new Leaf("detail");
		groupsLeaf.setWeight(1.0 / 3);
		contactsLeaf.setWeight(1.0 / 3);
		detailLeaf.setWeight(1.0 / 3);
		model.setChildren(Arrays.asList(groupsLeaf, new Divider(), contactsLeaf, new Divider(), detailLeaf));
		MultiSplitPane multiSplitPane = new MultiSplitPane();
		multiSplitPane.getMultiSplitLayout().setModel(model);
		multiSplitPane.add(groupPanel(), "groups");
		multiSplitPane.add(contactsPanel(), "contacts");
		multiSplitPane.add(new JButton("Detail"), "detail");
		container.add(multiSplitPane, BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	/**
	 * Create and return application menu.
	 *
	 * @return MenuBar
	 */
	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(helpMenu);

		this.menuItemImport = new JMenuItem("Import", KeyEvent.VK_I);
		this.menuItemImport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		this.menuItemImport.addActionListener(this);
		fileMenu.add(this.menuItemImport);
		this.menuItemExport = new JMenuItem("Export", KeyEvent.VK_E);
		this.menuItemExport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		this.menuItemExport.addActionListener(this);
		fileMenu.add(this.menuItemExport);
		fileMenu.addSeparator();
		this.menuItemClose = new JMenuItem("Quit", KeyEvent.VK_Q);
		this.menuItemClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
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
	 * Panel with groups
	 *
	 * @return Panel
	 */
	private JPanel groupPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Groups");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		groupsListModel.addElement(new Group(-1, "All"));
		for (Group g : db.getAllGroups()) {
			groupsListModel.addElement(g);
		}
		JList list = new JList(groupsListModel);
		list.setSelectedIndex(0);
		list.addListSelectionListener(new GroupSelectionListener());
		JScrollPane listScrollPane = new JScrollPane(list);
		panel.add(listScrollPane);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
		JButton add = new JButton(new ImageIcon(getClass().getResource("/res/plus.png"), "+"));
		add.setActionCommand("addGroup");
		add.addActionListener(this);
		buttons.add(add);
		JButton remove = new JButton(new ImageIcon(getClass().getResource("/res/minus.png"), "-"));
		remove.setActionCommand("removeGroup");
		remove.addActionListener(this);
		buttons.add(remove);
		panel.add(buttons);
		return panel;
	}

	/**
	 * Listener class for groups list
	 */
	private class GroupSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {	//React only on final choice
				JList list = (JList) e.getSource();
				Group[] groups = Arrays.copyOf(list.getSelectedValues(), list.getSelectedValues().length, Group[].class);
				System.out.println("Groups: " + Arrays.toString(groups));
			}
		}
	}

	/**
	 * Function for adding group
	 */
	private void addGroup() {
		//System.out.println("addGroup");
		String name = (String) JOptionPane.showInputDialog(
			this,
			"Group name:",
			"Add group",
			JOptionPane.QUESTION_MESSAGE,
			new ImageIcon(getClass().getResource("/res/plus.png"), "+"),
			null,
			""
		);
		if (!name.isEmpty()) {
			System.out.println(name);
			List<Group> group = db.addNewGroup(name);
			groupsListModel.addElement(group.get(0));
		}
	}

	/**
	 * Function for removing group
	 */
	private void removeGroup() {
		System.out.println("removeGroup");
	}

	/**
	 * Panel with contasts table
	 *
	 * @return Panel
	 */
	private JPanel contactsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Contacts");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		String[] columnNames = {"First Name", "Last Name", "Email", "Phone", "Address"};
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
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		//table.setFillsViewportHeight(true);
		panel.add(scrollPane);
		return panel;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		System.out.println(e.toString());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		filterContacts(searchField.getText());
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		filterContacts(searchField.getText());
	}

	/**
	 * Filter contacts
	 *
	 * @param filter Text to filer
	 */
	public void filterContacts(String filter) {
		System.out.println("Filtering: " + filter);
	}

	/**
	 * Method binding functionality to difference actions performed (e.g. if button
	 * was pressed etc.).
	 *
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.toString());
		if (e.getSource() == this.menuItemClose) {
			dispose();
		} else if ("addGroup".equals(e.getActionCommand())) {
			addGroup();
		} else if ("removeGroup".equals(e.getActionCommand())) {
			removeGroup();
		}
	}
}
