package cz.vutbr.fit.gja.gjaddr.persistancelayer.util;

/**
 * List of supported services. Used in database (AuthToken class).
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public enum ServicesEnum {

  /**
   * Facebook
   */
  FACEBOOK(0),
  /**
   * Google
   */
  GOOGLE(1);
  /**
   * Service code.
   */
  private Integer code;

  /**
   * Service number constructor.
   *
   * @param code service code.
   */
  private ServicesEnum(int code) {
    this.code = code;
  }

  /**
   * Get code of service.
   *
   * @return
   */
  public Integer getCode() {
    return this.code;
  }

  /**
   * Test if service code exists.
   *
   * @param service
   * @return
   */
  public static boolean contains(Integer service) {
    for (ServicesEnum s : ServicesEnum.values()) {
      if (s.getCode().equals(service)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Test if service name exists.
   *
   * @param service
   * @return
   */
  public static boolean contains(String service) {
    for (ServicesEnum s : ServicesEnum.values()) {
      if (s.toString().equals(service)) {
        return true;
      }
    }
    return false;
  }
}
