package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class Messenger implements Serializable {
	
	static private final long serialVersionUID = 6L;	
	
	private int id;
	
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Messenger(int id, int type, String value) {
		this.id = id;
		this.type = type;
		this.value = value;
	}
}
