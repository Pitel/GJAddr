
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
	 *
	 */
	private Integer service;

	/**
	 *
	 */
	private String token;

	/**
	 *
	 * @param service
	 * @param token
	 */
	public AuthToken(ServicesEnum service, String token) {
		this(service.getCode(), token);
	}

	/**
	 * 
	 * @param service
	 * @param token
	 */
	public AuthToken(Integer service, String token) {
		this.service = service;
		this.token = token;
	}

	/**
	 *
	 * @return
	 */
	public Integer getService() {
		return service;
	}

	/**
	 *
	 * @param service
	 */
	public void setService(ServicesEnum service) throws Exception {
		this.setService(service.getCode());
	}

	/**
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
	 *
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
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

}
