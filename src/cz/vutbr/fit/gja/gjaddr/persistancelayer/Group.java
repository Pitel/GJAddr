package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;

/**
 * One group from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Group implements Serializable {

	static private final long serialVersionUID = 6L;

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public Group (int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
