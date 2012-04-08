
package cz.vutbr.fit.gja.gjaddr.persistancelayer.util;

/**
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public enum ServicesEnum {

	FACEBOOK(0),
	GOOGLE(1);

	private int code;

	private ServicesEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

}
