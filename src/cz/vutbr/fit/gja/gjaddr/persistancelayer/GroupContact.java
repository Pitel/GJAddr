package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 * Groups and contacts association table.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class GroupContact implements Serializable {

  static private final long serialVersionUID = 6L;
  /**
   * Contact id.
   */
  private int contactId;
  /**
   * Group id.
   */
  private int groupId;

  /**
   * Get contact id.
   */
  public int getContactId() {
    return contactId;
  }

  /**
   * Get group id.
   */
  public int getGroupId() {
    return groupId;
  }

  /**
   * Constructor
   *
   * @param groupId group id
   * @param contactId contact id
   */
  public GroupContact(int groupId, int contactId) {
    this.groupId = groupId;
    this.contactId = contactId;
  }
}
