package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
		
		// fill list and enable/disable buttons
		fillList();
		list.setSelectedIndex(0);		
		this.mainWindowHandle.handleGroupActionsVisibility(getSelectedGroups());
				
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
	 * Fills list with groups from the DB
	 */
	static void fillList() {
		
		Group selectedGroup = (Group)list.getSelectedValue();
		
		listModel.clear();
		listModel.addElement(new Group(MainWindow.ROOT_GROUP));
		
		List<Group> listOfGroups = db.getAllGroups();
		
		Collections.sort(listOfGroups, new Comparator(){
 			@Override
			public int compare(Object o1, Object o2) {
					Group p1 = (Group) o1;
					Group p2 = (Group) o2;
					return p1.getName().compareToIgnoreCase(p2.getName());
			}
    });		
		
		for (Group g : listOfGroups ) {
			listModel.addElement(g);
		}

		// if is group missing, we have to set the group to my_contacts
		if (!listOfGroups.contains(selectedGroup))
			list.setSelectedIndex(0);
		else	
			list.setSelectedValue(selectedGroup, true);
	}


	/**
	 * Key press listener, delete key action is possible only when is delete group
	 * action enabled.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE 
			  && this.mainWindowHandle.actions.actionDeleteGroup.isEnabled()) {
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
