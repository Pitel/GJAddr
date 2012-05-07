
package cz.vutbr.fit.gja.gjaddr.persistancelayer.util;

/**
 * List of supported date types.
 *
 * @author Bc. Radek Gajdusek <xgajdu07@stud.fit.vutbr.cz>
 */
public enum DatesEnum {
	
	NAMEDAY(0),
	BIRTHDAY(1),
	CELEBRATION(2),
  OTHER(3);
	
	private Integer code;

	private DatesEnum(int code) {
		this.code = code;
	}	
	
	public Integer getCode() {
		return this.code;
	}	
}
