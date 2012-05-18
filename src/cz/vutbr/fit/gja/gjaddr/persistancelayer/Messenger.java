package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.MessengersEnum;
import java.io.Serializable;

/**
 * One messenger entry.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Messenger implements Serializable {

  static private final long serialVersionUID = 6L;
  /**
   * Messenger type.
   */
  private MessengersEnum type;
  /**
   * Messenger value.
   */
  private String value;

  /**
   * Get messenger type.
   */
  public MessengersEnum getType() {
    return type;
  }

  /**
   * Set messenger type.
   */
  public void setType(MessengersEnum type) {
    this.type = type;
  }

  /**
   * Get messenger value.
   *
   * @return
   */
  public String getValue() {
    return value;
  }

  /**
   * Set messenger value.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Messenger constructor.
   *
   * @param type type from enum
   * @param value value
   */
  public Messenger(MessengersEnum type, String value) {
    this.type = type;
    this.value = value;
  }

  /**
   * Custom equals implementation.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Messenger other = (Messenger) obj;

    if (this.type != other.type) {
      return false;
    }
    if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
      return false;
    }
    return true;
  }

  /**
   * Custom toString method implementation.
   */
  @Override
  public String toString() {
    return "Messenger{type=" + type + ", value=" + value + '}';
  }
}
