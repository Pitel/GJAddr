package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Window for manipulating with groups.
 *
 * @author Bc. Radek Gajdusek <xgajdu07@stud.fit,vutbr.cz>
 */
public final class GroupWindow extends JFrame {

  static final long serialVersionUID = 0;
  /**
   * Instance of database.
   */
  private Database db = Database.getInstance();

  /**
   * Possible actions with groups.
   */
  static enum Action {

    NEW, RENAME, REMOVE
  }

  /**
   *
   * Constructor for new window.
   *
   * @param action type of action, that should be done.
   */
  public GroupWindow(Action action) {

    boolean update = false;
    while (!update) {
      switch (action) {
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
            "Group with name \" " + name + "\" is already exists!",
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
   * Method for adding new group
   */
  private boolean addNewGroup() {
    String name = (String) JOptionPane.showInputDialog(
            this,
            "Group name:",
            "Add group",
            JOptionPane.QUESTION_MESSAGE,
            new ImageIcon(getClass().getResource("/res/plus_g.png"), "+"),
            null,
            "");

    // user click to cancel button
    if (name == null) {
      return true;
    }

    if (name.isEmpty()) {
      this.showEmptyNameDialog();
      return false;
    }

    List<Group> result = this.db.addNewGroup(name);
    if (result == null) {
      this.showGroupExistsDialog(name);
      return false;
    }

    return true;
  }

  /**
   * Method for renaming group.
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
            groups[0].getName());

    // user click to cancel button
    if (name == null) {
      return true;
    }

    // empty result - show info -> empty is not allowed
    if (name.isEmpty()) {
      this.showEmptyNameDialog();
      return false;
    }

    List<Group> result = this.db.renameGroup(groups[0], name);
    if (result == null) {
      this.showGroupExistsDialog(name);
      return false;
    }

    return true;
  }

  /**
   * Method for removing group.
   */
  private boolean removeGroups() {
    Group[] groups = GroupsPanel.getSelectedGroups();
    int delete = JOptionPane.showConfirmDialog(
            this,
            "Delete groups " + Arrays.toString(groups) + "?",
            "Delete groups",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            new ImageIcon(getClass().getResource("/res/minus_g.png"), "-"));


    // user click to cancel button or pressed ESC
    if (delete == 1 || delete == -1) {
      return true;
    }    
    
    if (delete == 0) {
      db.removeGroups(Arrays.asList(groups));
      return true;
    }

    return false;
  }
}
