
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
import java.io.Serializable;

/**
 * Class representing authorization tokens for Facebook and Google services.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class AuthToken implements Serializable {

	/**
	 * Serialization ID.
	 */
	static private final long serialVersionUID = 6L;

	/**
	 * Code of service to which this token belongs.
	 */
	private Integer service;

	/**
	 * Authorization token.
	 */
	private String token;

	/**
     * Constructor. Sets all required parameters.
	 *
	 * @param service
	 * @param token
	 */
	public AuthToken(ServicesEnum service, String token) {
		this(service.getCode(), token);
	}

	/**
     * Constructor. Sets all required parameters. Takes service ID as indentification of service.
	 * 
	 * @param service
	 * @param token
	 */
	public AuthToken(Integer service, String token) {
		this.service = service;
		this.token = token;
	}

	/**
     * Return service ID.
     * 
     * Service IDs are defined in ServicesEnum.
	 *
	 * @return
	 */
	public Integer getService() {
		return service;
	}

	/**
     * Set service using ServicesEnum.
	 *
	 * @param service
	 */
	public void setService(ServicesEnum service) throws Exception {
		this.setService(service.getCode());
	}

	/**
     * Set service by passing service ID.
     * 
     * Service IDs are defined in ServicesEnum.
	 *
	 * @param service
	 */
	public void setService(Integer service) throws Exception {
		if (ServicesEnum.contains(service)) {
			this.service = service;
		} else {
			throw new Exception("Unsupported service.");
		}
	}

	/**
     * Get authorization token.
	 *
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
     * Set authorization token.
	 * 
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * There should be just one token generated for each service.
	 * 
	 * Therefore AuthToken classes are equal if they have same service type.
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AuthToken other = (AuthToken) obj;

		if (this.service != other.service) {
			return false;
		}
		return true;
	}

	/**
	 * Override hashCode. Classes are equal if the have same service type.
	 * 
	 * @return
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + (this.service != null ? this.service.hashCode() : 0);
		return hash;
	}

	/**
	 * To string method prints the token.
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return this.token;
	}

}
