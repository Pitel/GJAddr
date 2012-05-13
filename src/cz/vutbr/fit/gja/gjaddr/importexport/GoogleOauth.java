
package cz.vutbr.fit.gja.gjaddr.importexport;

import com.google.gson.Gson;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.AuthToken;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;

/**
 * OAuth authentication for Google Contacts.
 *
 * TODO:
 *  - read settings from settings file/table
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class GoogleOauth {

	/**
	 * Class for processing Google authentication response.
	 */
	private class AuthResponse {

		private String access_token;
		private String token_type;
		private Integer expires_in;
		private String refresh_token;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public Integer getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(Integer expires_in) {
			this.expires_in = expires_in;
		}

		public String getRefresh_token() {
			return refresh_token;
		}

		public void setRefresh_token(String refresh_token) {
			this.refresh_token = refresh_token;
		}

		public String getToken_type() {
			return token_type;
		}

		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}

		@Override
		public String toString() {
			return String.format("access_token:%s,token_type:%s,expires_in:%s,refresh_token:%s",
					this.access_token, this.token_type, this.expires_in.toString(), this.refresh_token);
		}
	}

	/**
	 * Application ID/API key.
	 */
	private String appid = "946930126424.apps.googleusercontent.com";

	/**
	 * Application redirect URI.
	 */
	private String redirectUri = "urn:ietf:wg:oauth:2.0:oob";

	/**
	 * Application secret.
	 * TODO HIDE!!!!
	 */
	private String secret = "9PcfBWb_jYdgyZyHt61JvMdY";

	/**
	 * Local database.
	 */
	private Database database;

	/**
	 * Constructor.
	 */
	public GoogleOauth() {
		this.database = Database.getInstance();
	}

	/**
	 * Authenticate user with Facebook and save returned token.
	 */
	public void authenticate() {
		try {
			String url = "https://accounts.google.com/o/oauth2/auth?";
			url += "response_type=code";
			url += "&client_id=" + this.appid;
			url += "&redirect_uri=" + this.redirectUri;
			url += "&scope=https://www.google.com/m8/feeds";
			LoggerFactory.getLogger(this.getClass()).info("Redirecting user to {}", url);
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
	 * URL encode given data.
	 * 
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String encodeData(Map<String, String> data) throws UnsupportedEncodingException {
		String encoded = "";
		for (String key : data.keySet()) {
			encoded += "&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(data.get(key), "UTF-8");
		}
		return encoded.substring(1, encoded.length());
	}

	/**
	 * Authenticate user with Google and return token.
	 *
	 * @param code
	 * @return
	 */
	public AuthToken authenticate(String code) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("code", code);
		data.put("client_id", this.appid);
		data.put("client_secret", this.secret);
		data.put("redirect_uri", this.redirectUri);
		data.put("grant_type", "authorization_code");

		OutputStreamWriter wr = null;
		BufferedReader rd = null;

		try {
			// send the request
			URL url = new URL("https://accounts.google.com/o/oauth2/token");
			LoggerFactory.getLogger(this.getClass()).info("Sending request to {}", url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(this.encodeData(data));
			wr.flush();

			// get the response
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String json = "";
			while ((line = rd.readLine()) != null) {
				json += line;
			}

			// check response code
			if (conn.getResponseCode() != 200) {
				LoggerFactory.getLogger(this.getClass()).error("Request was unsuccessful. Status code: {}",
						String.valueOf(conn.getResponseCode()));
				return null;
			}

			// parse the response
			AuthResponse response = new Gson().fromJson(json, AuthResponse.class);
			AuthToken token = new AuthToken(ServicesEnum.GOOGLE, response.getAccess_token());
			LoggerFactory.getLogger(this.getClass()).info("Received new token.");
			return token;
		} catch (MalformedURLException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
			return null;
		} catch (IOException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
			return null;
		} finally {
			try {
				wr.close();
				rd.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Check if token is still valid.
	 * 
	 * @return
	 */
	public boolean isTokenValid() {
		LoggerFactory.getLogger(this.getClass()).info("Checking Google token validity.");

		AuthToken token = this.database.getToken(ServicesEnum.GOOGLE);
		if (token == null) {
			return false;
		}

		String stringUrl = "https://www.google.com/m8/feeds/contacts/default/full?access_token=";
		stringUrl += token.getToken();
		Integer responseCode = null;

		LoggerFactory.getLogger(this.getClass()).info("Requesting {}", stringUrl);

		try {
			// send the request
			URL url = new URL(stringUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			responseCode = conn.getResponseCode();
		} catch (MalformedURLException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
		} catch (IOException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
		}

		LoggerFactory.getLogger(this.getClass()).info("Response code {}", String.valueOf(responseCode));

		if (responseCode != null && responseCode.equals(200)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test the class.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new GoogleOauth().isTokenValid();
	}
}
