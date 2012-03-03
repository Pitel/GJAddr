package cz.vutbr.fit.gja.gjaddr.importexport;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

/**
 * Class for importing Facebook contacts
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit.vutbr.cz>
 * @see <a href="http://restfb.com">RestFB</a>
 * @see <a href="https://developers.facebook.com/docs/reference/api/user">Facebook Graph API</a>
 */
public class Facebook {
	public Facebook() {
		FacebookClient facebookClient = new DefaultFacebookClient("XXXXXX");
	}
}
