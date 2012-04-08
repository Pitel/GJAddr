
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;

/**
 * Database class for managing authorization tokens.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class DatabaseAuth {

	/**
	 *
	 */
	private int idCounter = 0;

	/**
	 *
	 */
	private final String FILENAME = new File(Settings.getDataDir(), "auth.gja").toString();

	/**
	 *
	 */
	private ArrayList<AuthToken> tokens;

	/**
	 * 
	 */
	public DatabaseAuth() {
		this.load();
		this.setLastIdNumber();
	}	

	/**
	 * Initialize database.
	 */
	private void load()	{
		this.tokens = null;

		if ((new File(this.FILENAME)).exists()) {
			try {
				FileInputStream fis = new FileInputStream(FILENAME);
				ObjectInputStream ois = new ObjectInputStream(fis);

				try {
					this.tokens = (ArrayList<AuthToken>) ois.readObject();
				} finally {
					try {
						ois.close();
					} finally {
						fis.close();
					}
				}
			} catch(IOException ioe) {
				LoggerFactory.getLogger(this.getClass()).error(ioe.toString());
			} catch(ClassNotFoundException cnfe) {
				LoggerFactory.getLogger(this.getClass()).error(cnfe.toString());
			}
		}

		// create empty DB
		if (this.tokens == null) {
			this.tokens = new ArrayList<AuthToken>();
		}
	}

	/**
	 * Get the highest ID from database.
	 */
	private void setLastIdNumber() {
		int counter = 0;

		for (AuthToken token : this.tokens) {
			int id = token.getId();
			if (id > counter) {
				counter = id;
			}
		}

		this.idCounter = counter;
	}

	/**
	 * Save tokens in database.
	 */
	void save()	{
		if (this.tokens == null || this.tokens.isEmpty()) {
			return;
		}

		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			try {
				oos.writeObject(this.tokens);
				oos.flush();
			} finally {
				try {
					oos.close();
				} finally {
					fos.close();
				}
			}
		} catch(IOException ioe) {
			LoggerFactory.getLogger(this.getClass()).error(ioe.toString());
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private AuthToken filterItem(int id) {
		for (AuthToken token : this.tokens) {
			if (token.getId() == id) {
				return token;
			}
		}
		return null;
	}

	/**
	 * Clear (remove) all tokens.
	 */
	void clear() {
		this.tokens.clear();
		this.idCounter = 0;
	}

	/**
	 * Add new token to database.
	 * 
	 * @param token
	 */
	void add(AuthToken token) {
		token.id = ++this.idCounter;
		this.tokens.add(token);
	}

	/**
	 * Update token.
	 * 
	 * @param token
	 */
	void update(AuthToken token) {
		AuthToken updatedToken = this.filterItem(token.getId());
		int index = this.tokens.indexOf(updatedToken);

		if (index != -1) {
			this.tokens.set(index, token);
		}
	}

	/**
	 * Remove token from database.
	 * 
	 * @param token
	 */
	void remove(AuthToken token) {
		this.tokens.remove(token);
	}

	/**
	 * Get token specified by service.
	 * 
	 * @param service
	 * @return
	 */
	AuthToken get(Integer service) {
		for (AuthToken token : this.tokens) {
			if (token.getService().equals(service)) {
				return token;
			}
		}
		return null;
	}
}
