package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Ragaj
 */
public final class GroupWindow extends JFrame{
	
	private Database db = Database.getInstance();
	
	public GroupWindow(boolean newGroup) {
		
		boolean update;
		
		if (newGroup) {
			update = this.addNewGroup();
		}
		else {
			update = this.removeGroups();
		}

		if (update) {
			GroupsPanel.fillList();
		}
	}

	/**
	 * Function for adding new group
	 */	
	boolean addNewGroup() {
		boolean update = false;
		//System.out.println("addGroup");
		String name = (String) JOptionPane.showInputDialog(
			this,
			"Group name:",
			"Add group",
			JOptionPane.QUESTION_MESSAGE,
			new ImageIcon(getClass().getResource("/res/plus_g.png"), "+"),
			null,
			""
		);
		
		if (name != null && !name.isEmpty()) {
			//System.out.println(name);
			List<Group> result = this.db.addNewGroup(name);
			
			if (result == null) {
				JOptionPane.showMessageDialog(this, 
																			"Group with name \" "+ name + "\" is already exists!", 
																			"Group exists", 
																			JOptionPane.WARNING_MESSAGE);
			}
			
			update = true;
		}
		
		return update;
	}

	/**
	 * Function for removing group
	 */
	 boolean removeGroups() {
		Group[] groups = GroupsPanel.getSelectedGroups();
		int delete = JOptionPane.showConfirmDialog(
			this,
			"Delete groups " + Arrays.toString(groups) + "?",
			"Delete groups",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			new ImageIcon(getClass().getResource("/res/minus_g.png"), "-")
		);

		if (delete == 0) {
			db.removeGroups(Arrays.asList(groups));
			return true;
		}
		
		return false;
	}		
}
