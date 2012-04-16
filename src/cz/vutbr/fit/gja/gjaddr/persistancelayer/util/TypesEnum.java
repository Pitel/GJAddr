
package cz.vutbr.fit.gja.gjaddr.persistancelayer.util;

/**
 *
 * @author Ragaj
 */
public enum TypesEnum {
	
	WORK(0),
	HOME(1),
	OTHER(2);
	
	private Integer code;

	private TypesEnum(int code) {
		this.code = code;
	}	
	
	public Integer getCode() {
		return this.code;
	}	
}
