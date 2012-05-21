package cz.vutbr.fit.gja.gjaddr.importexport;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.AuthToken;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Settings;
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
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 * @see <https://developers.facebook.com/docs/authentication/>
 */
public class FacebookOauth {

  /**
   * Application ID/API key.
   */
  private String appid = Settings.instance().getApplicationId(ServicesEnum.FACEBOOK);
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

  /**
   * Constructor.
   */
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
      String url = "https://www.facebook.com/dialog/oauth";
      url += "?client_id=" + this.appid;
      url += "&redirect_uri=https://www.facebook.com/connect/login_success.html";
      url += "&response_type=token";
      url += "&scope=friends_about_me,friends_birthday,friends_location,friends_status,friends_website";
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
   * Check if token saved in database is still valid.
   *
   * @return
   */
  public boolean isTokenValid() {
    this.token = this.database.getToken(ServicesEnum.FACEBOOK);
    if (this.token == null) {
      return false;
    }
    try {
      this.client = new DefaultFacebookClient(this.token.getToken());
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
