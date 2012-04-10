
package cz.vutbr.fit.gja.gjaddr.importexport;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Test VCardImportExport class.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class VCardImportExportTest {

	private VCardImportExport vcardIE;

	private Database database = Database.getInstance();

	private String testFile1 = this.getClass().getResource("./testFiles/test01.vcf").getPath();
	private String testFile2 = this.getClass().getResource("./testFiles/test02.vcf").getPath();;

    public VCardImportExportTest() {
		this.database.clearAllData();
		this.vcardIE = null;
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.out.println("VCARD_IMPORT_EXPORT TEST START");
		System.out.println("------------------------------");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		System.out.println("----------------------------");
		System.out.println("VCARD_IMPORT_EXPORT TEST END");
	}

    @Before
    public void setUp() {
		this.database.clearAllData();
		this.vcardIE = new VCardImportExport();
    }

    @After
    public void tearDown() {
		this.database.clearAllData();
		this.vcardIE = null;
    }

	/**
	 * Test of importContacts method, of class VCardImportExport.
	 */
	@Test
	public void testImportContacts() {
		System.out.println("testing importContacts method");
		// import just one contact and test it's values
		System.out.println("1. import one contact");
		File file = new File(this.testFile1);
		try {
			this.vcardIE.importContacts(file);
		} catch (IOException ex) {
			fail("method importContacts fail because of IOException");
		}
		List<Contact> cs = this.database.getAllContacts();
		assertThat(1, is(cs.size()));
		Contact c = cs.get(0);
		assertThat("Frantisek", equalTo(c.getFirstName()));
		assertThat("Zboril", equalTo(c.getSurName()));
		assertThat(1, equalTo(c.getEmails().size()));
		assertThat("zborilf@fit.vutbr.cz", equalTo(c.getEmails().get(0).getEmail()));
		assertThat(1, equalTo(c.getAdresses().size()));
		assertThat(1, equalTo(c.getUrls().size()));
		// import multiple contacts
		System.out.println("2. import multiples contacts");
		this.database.clearAllData();
		file = new File(this.testFile2);
		try {
			this.vcardIE.importContacts(file);
		} catch (IOException ex) {
			fail("method importContacts fail because of IOException");
		}
		cs = this.database.getAllContacts();
		assertThat(2, is(cs.size()));
		c = cs.get(0);
		assertThat(2, equalTo(c.getPhoneNumbers().size()));
	}

	/**
	 * Test of importContactsToGroup method, of class VCardImportExport.
	 *//* @Test
	public void testImportContactsToGroup() throws Exception {
		System.out.println("importContactsToGroup");
		File file = null;
		String group = "";
		VCardImportExport instance = new VCardImportExport();
		instance.importContactsToGroup(file, group);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}*/

	/**
	 * Test of exportContacts method, of class VCardImportExport.
	 *//* @Test
	public void testExportContacts() throws Exception {
		System.out.println("exportContacts");
		File file = null;
		List<Contact> contacts = null;
		VCardImportExport instance = new VCardImportExport();
		instance.exportContacts(file, contacts);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}*/

	/**
	 * Test of main method, of class VCardImportExport.
	 *//* @Test
	public void testMain() {
		System.out.println("main");
		String[] args = null;
		VCardImportExport.main(args);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}*/

}