package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 * Class for one custom entry representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Custom implements Serializable {

  static private final long serialVersionUID = 6L;
  /**
   * Custom entry name.
   */
  private String name;
  /**
   * Custom entry value.
   */
  private String value;

  /**
   * Get custom name.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Set custom name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get custom value.
   */
  public String getValue() {
    return value;
  }

  /**
   * Set custom value.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Custom entry constructor.
   *
   * @param name name of custom item
   * @param value custom value
   */
  public Custom(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * Custom equals implementation.
   *
   * @param obj
   * @return
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Custom other = (Custom) obj;

    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
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
    return "Custom{name=" + name + ", value=" + value + '}';
  }
}
