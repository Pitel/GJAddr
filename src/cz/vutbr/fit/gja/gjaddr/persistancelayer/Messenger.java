package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.MessengersEnum;
import java.io.Serializable;

/**
 *
 * @author Ragaj
 */
public class Messenger implements Serializable {

	static private final long serialVersionUID = 6L;

	private MessengersEnum type;
	private String value;

	public MessengersEnum getType() {
		return type;
	}

	public void setType(MessengersEnum type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Messenger(MessengersEnum type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Messenger other = (Messenger) obj;

		if (this.type != other.type) {
			return false;
		}
		if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Messenger{type=" + type + ", value=" + value + '}';
	}
}
