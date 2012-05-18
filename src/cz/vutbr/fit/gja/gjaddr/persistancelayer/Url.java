package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.TypesEnum;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * One URL from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Url implements Serializable {

  static private final long serialVersionUID = 6L;
  /**
   * Url type.
   */
  private TypesEnum type;
  /**
   * Concrete url.
   */
  private URL value;

  /**
   * Get the url type.
   */
  public TypesEnum getType() {
    return type;
  }

  /**
   * Set the url type.
   */
  public void setType(TypesEnum type) {
    this.type = type;
  }

  /**
   * Get the url value.
   *
   * @return
   */
  public URL getValue() {
    return value;
  }

  /**
   * Set the url value.
   *
   * @param value
   */
  public void setValue(URL value) {
    this.value = value;
  }

  /**
   * Url constructor.
   *
   * @param type type of url
   * @param value content
   */
  public Url(TypesEnum type, String value) {
    this.type = type;

    try {
      if (!value.isEmpty()
              && !value.startsWith("http://")
              && !value.startsWith("https://")) {
        value = "http://" + value;
      }

      this.value = new URL(value);
    } catch (MalformedURLException e) {
      this.value = null;
    }
  }

  /**
   * Create representation of one URL.
   *
   * @param type
   * @param value
   */
  public Url(TypesEnum type, URL value) {
    this.type = type;
    this.value = value;
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
    final Url other = (Url) obj;

    if (this.type != other.type) {
      return false;
    }
    if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
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
    return "Url{type=" + type + ", value=" + value + '}';
  }
}
