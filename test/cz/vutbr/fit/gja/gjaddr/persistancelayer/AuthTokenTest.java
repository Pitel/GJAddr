
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.rules.ExpectedException;
import org.junit.Rule;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Test AuthToken class.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class AuthTokenTest {

	private AuthToken token;

	private String testToken1 = "dhasjh3j4h32j4hk";
	private String testToken2 = "das78da9da8duwa8";
	private String testToken3 = "4hj32k4h23jkh42j";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

    public AuthTokenTest() {
		this.token = null;
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.out.println("AUTH_TOKEN TEST START");
		System.out.println("---------------------");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		System.out.println("-------------------");
		System.out.println("AUTH_TOKEN TEST END");
	}

    @Before
    public void setUp() {
		this.token = new AuthToken(ServicesEnum.FACEBOOK, this.testToken1);
    }

    @After
    public void tearDown() {
		this.token = null;
    }

	/**
	 * Test of getService method, of class AuthToken.
	 */
	@Test
	public void testGetService() {
		System.out.println("testing getService method");
		assertThat(ServicesEnum.FACEBOOK.getCode(),
				equalTo(this.token.getService()));
		try {
			this.token.setService(ServicesEnum.GOOGLE);
		} catch (Exception ex) {
			fail("This should never happen.");
		}
		assertThat(ServicesEnum.GOOGLE.getCode(),
				equalTo(this.token.getService()));
		try {
			this.token.setService(ServicesEnum.FACEBOOK.getCode());
		} catch (Exception ex) {
			fail("This should never happen.");
		}
		assertThat("Tokens don't match.", ServicesEnum.FACEBOOK.getCode(),
				equalTo(this.token.getService()));
	}

	/**
	 * Test of setService method, of class AuthToken.
	 */
	@Test
	public void testSetService_ServicesEnum() throws Exception {
		System.out.println("testing setService(ServicesEnum) method");
		// these are OK
		try {
			this.token.setService(ServicesEnum.GOOGLE);
		} catch (Exception ex) {
			fail("This should never happen.");
		}
		try {
			this.token.setService(ServicesEnum.FACEBOOK);
		} catch (Exception ex) {
			fail("This should never happen.");
		}
	}

	/**
	 * Test of setService method, of class AuthToken.
	 */
	@Test
	public void testSetService_Integer() throws Exception {
		System.out.println("testing setService(Integer) method");
		// these are OK
		try {
			this.token.setService(ServicesEnum.GOOGLE.getCode());
		} catch (Exception ex) {
			fail("This should never happen.");
		}
		try {
			this.token.setService(ServicesEnum.FACEBOOK.getCode());
		} catch (Exception ex) {
			fail("This should never happen.");
		}
		// this will throw exception
		this.thrown.expect(Exception.class);
		this.token.setService(3);
	}

	/**
	 * Test of getToken method, of class AuthToken.
	 */
	@Test
	public void testGetToken() {
		System.out.println("testing getToken method");
		assertThat(this.testToken1, equalTo(this.token.getToken()));
		this.token.setToken(this.testToken2);
		assertThat(this.testToken2, equalTo(this.token.getToken()));
	}

	/**
	 * Test of setToken method, of class AuthToken.
	 */
	@Test
	public void testSetToken() {
		System.out.println("testing setToken method");
		assertThat(this.testToken1, equalTo(this.token.getToken()));
		this.token.setToken(this.testToken2);
		assertThat(this.testToken2, equalTo(this.token.getToken()));
		this.token.setToken(this.testToken3);
		assertThat(this.testToken3, equalTo(this.token.getToken()));
	}

	/**
	 * Test of equals method, of class AuthToken.
	 */
	@Test
	public void testEquals() {
		System.out.println("testing equals method");
		AuthToken equal = new AuthToken(ServicesEnum.FACEBOOK, this.testToken1);
		AuthToken equal2 = new AuthToken(ServicesEnum.FACEBOOK, this.testToken2);
		AuthToken notEqual = new AuthToken(ServicesEnum.GOOGLE, this.testToken1);
		AuthToken notEqual2 = new AuthToken(ServicesEnum.GOOGLE, this.testToken3);
		assertThat(true, is(this.token.equals(equal)));
		assertThat(true, is(this.token.equals(equal2)));
		assertThat(true, is(not(this.token.equals(notEqual))));
		assertThat(true, is(not(this.token.equals(notEqual2))));
	}

}