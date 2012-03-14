package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class Custom implements Serializable {
	
	static private final long serialVersionUID = 6L;
	
	private int id;
	
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Custom(int id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}	
}
