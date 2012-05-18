package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 * One group from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Group implements Serializable {

  static private final long serialVersionUID = 6L;
  /**
   * Group id, default is -1, item is generated.
   */
  private int id = -1;
  /**
   * Group name.
   */
  private String name;

  /**
   * Get group id.
   */
  public int getId() {
    return id;
  }

  /**
   * Get group name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Set group name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * New group public constuctor
   *
   * @param name name of group
   */
  public Group(String name) {
    this.name = name;
  }

  /**
   * Database constructor.
   *
   * @param id id
   * @param name name
   */
  Group(int id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Custom equals method implementation.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Group other = (Group) obj;
    if (this.id != other.id) {
      return false;
    }
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    return true;
  }

  /**
   * Custom toString method implementation.
   *
   * @return
   */
  @Override
  public String toString() {
    int size = Database.getInstance().getNumberOfContactsForGroup(this);
    return name + " (" + size + ")";
  }
}
