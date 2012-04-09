
package cz.vutbr.fit.gja.gjaddr.importexport;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.AuthToken;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;

import java.awt.Desktop;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.LoggerFactory;

/**
 * Do Facebook authentication and save returned authentication token in database.
 *
 * TODO:
 *  - read application ID from settings file/table
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 * @see <https://developers.facebook.com/docs/authentication/>
 */
public class FacebookOauth {

	/**
	 * Application ID/API key.
	 */
	private String appid = "192960620820878";

	/**
	 * Facebook client for reading contacts.
	 */
	private FacebookClient client;

	/**
	 * Authentication token for Facebook.
	 */
	private AuthToken token;

	/**
	 * Local database.
	 */
	private Database database;

	public FacebookOauth() {
		this.database = Database.getInstance();
		this.token = this.database.getToken(ServicesEnum.FACEBOOK);
		if (this.token != null) {
			this.client = new DefaultFacebookClient(this.token.getToken());
		} else {
			this.client = null;
		}
	}

	/**
	 * Authenticate user with Facebook and save returned token.
	 */
	public void authenticate() {
		try {
			String url = "https://graph.facebook.com/oauth/authorize?";
			url += "client_id=" + this.appid;
			url += "&redirect_uri=https://www.facebook.com/connect/login_success.html";
			url += "&response_type=code";
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(new URI(url));
		} catch (URISyntaxException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
		} catch (MalformedURLException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
		} catch (IOException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
		} 
	}

	/**
	 * 
	 * @return
	 */
	public boolean isTokenValid() {
		this.token = this.database.getToken(ServicesEnum.FACEBOOK);
		if (this.token == null) {
			return false;
		}
		try {
			User user = this.client.fetchObject("me", User.class);
		} catch (FacebookOAuthException ex) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new FacebookOauth().isTokenValid());
	}

}
