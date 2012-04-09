package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * One group from database representation.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Group  implements Serializable {

	static private final long serialVersionUID = 6L;

	private int id = -1;
	private String name;

	public int getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Group (String name) {
		this.name = name;
	}

	// Database constructor
	Group (int id, String name) {
		this.id = id;		
		this.name = name;
	}	

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Group other = (Group) obj;
		if (this.id != other.id) {
			return false;
		}
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		List<Group> groups = new ArrayList<Group>();
		groups.add(this);		
		int size = Database.getInstance().getAllContactsFromGroup(groups).size();
		return name + " (" + size +")";
	}
}
