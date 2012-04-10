package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;

/**
 * Panel with groups.
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class GroupsPanel extends JPanel implements KeyListener {
	static final long serialVersionUID = 0;
	private static final Database db = Database.getInstance();
	private static final DefaultListModel listModel = new DefaultListModel();
	private static final JList list = new JList(listModel);
	
	private JPopupMenu contextMenu = new JPopupMenu();	
	private MainWindow mainWindowHandle;	

	/**
	 * Constructor
	 *
	 * @param listSelectionListener Listener to handle actions outside groups panel
	 */
	public GroupsPanel(MainWindow handle, ListSelectionListener listSelectionListener) {
		this.mainWindowHandle = handle;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Groups");
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		fillList();
		
		list.addListSelectionListener(listSelectionListener);
		list.addKeyListener(this);
		
		final JScrollPane listScrollPane = new JScrollPane(list);
		add(listScrollPane);
		
		final JToolBar buttonToolbar = new JToolBar();
		buttonToolbar.setFloatable(false);	
		buttonToolbar.add(this.mainWindowHandle.actions.actionNewGroup);
		buttonToolbar.add(this.mainWindowHandle.actions.actionRenameGroup);			
		buttonToolbar.add(this.mainWindowHandle.actions.actionDeleteGroup);
	
		add(buttonToolbar, BorderLayout.NORTH);
		
		this.initContextMenu();
	}

	/**
	 * Fills list with groups from db
	 */
	static void fillList() {
		listModel.clear();
		listModel.addElement(new Group("My Contacts"));
		for (Group g : db.getAllGroups()) {
			listModel.addElement(g);
		}
		
		list.setSelectedIndex(0);
	}

	/**
	 * Key press listener
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			new GroupWindow(GroupWindow.Action.REMOVE);
			
		}
	}

	/**
	 * Key release listener
	 */
	@Override
	public void keyReleased(KeyEvent e) {}

	/**
	 * Key typed listener
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

	 
	static Group[] getSelectedGroups() {
		return Arrays.copyOf(list.getSelectedValues(), list.getSelectedValues().length, Group[].class);
	}
	
	private void initContextMenu() {
		
		this.contextMenu.add(this.mainWindowHandle.actions.actionNewGroup);
		this.contextMenu.add(this.mainWindowHandle.actions.actionRenameGroup);				
		this.contextMenu.add(this.mainWindowHandle.actions.actionDeleteGroup);
		
		this.contextMenu.addSeparator();
		
		this.contextMenu.add(this.mainWindowHandle.actions.actionImport);
		this.contextMenu.add(this.mainWindowHandle.actions.actionExport);

		MouseListener popupListener = new PopupListener();
		list.addMouseListener(popupListener);	
	}
	
	class PopupListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			showPopup(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			showPopup(e);
		}

		private void showPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {								
				contextMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}	
	}	
}
