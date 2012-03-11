package cz.vutbr.fit.gja.gjaddr.persistancelayer;

/**
 * One group from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Group {
	private int id;
	private String name;

	public String getName() {
		return this.name;
	}

	public Group (int id, String name) {
		this.id = id;
		this.name = name;
	}
}
