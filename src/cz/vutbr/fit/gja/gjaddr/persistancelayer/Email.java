package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.TypesEnum;
import java.io.Serializable;

/**
 * One email from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Email implements Serializable {

  static private final long serialVersionUID = 6L;
  /**
   * Email type.
   */
  private TypesEnum type;
  /**
   * Email value.
   */
  private String email;

  /**
   * Get email.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set email value.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Get email type.
   */
  public TypesEnum getType() {
    return type;
  }

  /**
   * Set email type.
   */
  public void setType(TypesEnum type) {
    this.type = type;
  }

  /**
   * Constructor.
   */
  public Email(TypesEnum type, String email) {
    this.type = type;
    this.email = email;
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
    final Email other = (Email) obj;

    if (this.type != other.type) {
      return false;
    }
    if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
      return false;
    }
    return true;
  }

  /**
   * Custom toString method implementation.
   */
  @Override
  public String toString() {
    return "Email{type=" + type + ", email=" + email + '}';
  }
}
