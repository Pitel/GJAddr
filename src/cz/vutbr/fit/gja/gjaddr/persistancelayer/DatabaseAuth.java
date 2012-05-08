
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Database class for managing authorization tokens.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class DatabaseAuth {

	/**
	 *
	 */
	private final String FILENAME = new File(Settings.instance().getDataDir(), "auth").toString();

	/**
	 *
	 */
	private ArrayList<AuthToken> tokens;

	/**
	 * 
	 */
	public DatabaseAuth() {
		this.load();
	}	

	/**
	 * Initialize database.
	 */
	private void load()	{
    Persistance per = new Persistance();
		this.tokens = (ArrayList<AuthToken>) per.loadData(FILENAME);
	}

	/**
	 * Save tokens in database.
	 */
	void save()	{
    Persistance per = new Persistance();    
		per.saveData(FILENAME, this.tokens);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private AuthToken filterItem(int service) {
		for (AuthToken token : this.tokens) {
			if (token.getService() == service) {
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
	}

	/**
	 * Add new token to database.
	 * 
	 * @param token
	 */
	void add(AuthToken token) {
		AuthToken oldToken = null;
		for (AuthToken t : this.tokens) {
			if (t.getService().equals(token.getService())) {
				oldToken = t;
			}
		}
		if (oldToken != null) {
			this.tokens.remove(oldToken);
		}
		this.tokens.add(token);
	}

	/**
	 * Update token.
	 * 
	 * @param token
	 */
	void update(AuthToken token) {
		AuthToken updatedToken = this.filterItem(token.getService());
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
		if (token == null) {
			return;
		}
		AuthToken toBeRemoved = null;
		for (AuthToken t : this.tokens) {
			if (t.getService() == token.getService()) {
				toBeRemoved = t;
			}
		}
		if (toBeRemoved != null) {
			this.tokens.remove(toBeRemoved);
		}
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
