package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.TypesEnum;
import java.io.Serializable;

/**
 * One phone number entry.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class PhoneNumber implements Serializable {

  static private final long serialVersionUID = 6L;
  /**
   * Phone type
   */
  private TypesEnum type;
  /**
   * Phone number
   */
  private String number;

  /**
   * Get phone number type.
   */
  public TypesEnum getType() {
    return type;
  }

  /**
   * Set phone number type.
   */
  public void setType(TypesEnum type) {
    this.type = type;
  }

  /**
   * Get phone number.
   */
  public String getNumber() {
    return this.number;
  }

  /**
   * Set phone number
   */
  public void setNumber(String number) {
    this.number = number;
  }

  /**
   * Phone number constructor.
   *
   * @param type type from enum
   * @param number phone number string
   */
  public PhoneNumber(TypesEnum type, String number) {
    this.type = type;
    this.number = number;
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
    final PhoneNumber other = (PhoneNumber) obj;
    if (this.type != other.type) {
      return false;
    }
    if ((this.number == null) ? (other.number != null) : !this.number.equals(other.number)) {
      return false;
    }
    return true;
  }

  /**
   * Custom toString method implementation.
   */
  @Override
  public String toString() {
    return "PhoneNumber{" + "type=" + type + ", number=" + number + '}';
  }
}
