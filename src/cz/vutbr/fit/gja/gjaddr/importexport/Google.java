package cz.vutbr.fit.gja.gjaddr.importexport;

import com.google.gdata.client.*;
import com.google.gdata.client.contacts.*;
import com.google.gdata.data.*;
import com.google.gdata.data.contacts.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;
import java.io.IOException;
import java.net.URL;

/**
 * Class for importing Google contacts
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit.vutbr.cz>
 * @see <a href="https://code.google.com/apis/contacts">Google Contacts Data API</a>
 * @see <a href="https://code.google.com/p/gdata-java-client">Google Data Java Client Library</a>
 */
public class Google {
	public Google() {
		ContactsService myService = new ContactsService("GJAddr");
	}
}
