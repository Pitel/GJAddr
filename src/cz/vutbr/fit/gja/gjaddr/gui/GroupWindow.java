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
	static final long serialVersionUID = 0;
	private Database db = Database.getInstance();

	static enum Action {
    NEW, RENAME, REMOVE
	}

	public GroupWindow(Action action) {

		boolean update = false;
    while (!update) {
      switch(action) {
        case NEW:
          update = this.addNewGroup();
          break;
        case RENAME:
          update = this.renameGroup();
          break;
        case REMOVE:
          update = this.removeGroups();
          break;
      }
    }

		GroupsPanel.fillList();
	}
  
  /**
   * Display user info, that group with the same name exists.
   */
	private void showGroupExistsDialog(String name) {
		JOptionPane.showMessageDialog(this,
			"Group with name \" "+ name + "\" is already exists!",
			"Group exists",
			JOptionPane.WARNING_MESSAGE);
	}
  
  /**
   * Display user info, that can't create or update group with empty name.
   */
	private void showEmptyNameDialog() {
		JOptionPane.showMessageDialog(this,
			"Can't create group with empty name, please enter the name",
			"Empty group name",
			JOptionPane.WARNING_MESSAGE);
	}  

	/**
	 * Function for adding new group
	 */
	private boolean addNewGroup() {
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

			List<Group> result = this.db.addNewGroup(name);
			if (result == null) {
				this.showGroupExistsDialog(name);
        return false;
			}
		}
    else  {
      this.showEmptyNameDialog();
      return false;
    }

		return true;
	}

	/**
	 * Function for rename group
	 */
	private boolean renameGroup() {
		Group[] groups = GroupsPanel.getSelectedGroups();
		String name = (String) JOptionPane.showInputDialog(
			this,
			"Group name:",
			"Rename group",
			JOptionPane.QUESTION_MESSAGE,
			new ImageIcon(getClass().getResource("/res/edit_g.png"), "e"),
			null,
			groups[0].getName()
		);

		if (name != null && !name.isEmpty()) {

			List<Group> result = this.db.renameGroup(groups[0], name);
			if (result == null) {
				this.showGroupExistsDialog(name);
        return false;
			}
		}
    else  {
      this.showEmptyNameDialog();
      return false;
    }

		return true;
	}

	/**
	 * Function for removing group
	 */
	 private boolean removeGroups() {
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
