
package cz.vutbr.fit.gja.gjaddr.importexport;

import java.util.ArrayList;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
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

	private String testGroup1 = "testGroup1";
	private String testGroup2 = "testGroup2";

	private String testFile1 = this.getClass().getResource("./testFiles/test01.vcf").getPath();
	private String testFile2 = this.getClass().getResource("./testFiles/test02.vcf").getPath();

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
			fail("method importContacts failed because of IOException");
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
			fail("method importContacts failed because of IOException");
		}
		cs = this.database.getAllContacts();
		assertThat(2, is(cs.size()));
		c = cs.get(0);
		assertThat(2, equalTo(c.getPhoneNumbers().size()));
	}

	/**
	 * Test of importContactsToGroup method, of class VCardImportExport.
	 */
	@Test
	public void testImportContactsToGroup() {
		System.out.println("testing importContacts method");
		// 1. import to existing group
		System.out.println("1. import to existing group");
		Group g = new Group(this.testGroup1);
		this.database.addNewGroup(g.getName());
		File file = new File(this.testFile1);
		try {
			this.vcardIE.importContactsToGroup(file, g.getName());
		} catch (IOException ex) {
			fail("method importContacts failed because of IOException");
		}
		List<Group> gs = new ArrayList<Group>();
		gs.add(g);
		List<Contact> cs = this.database.getAllContacts();
		List<Contact> gcs = this.database.getAllContactsFromGroup(gs);
		assertThat(1, is(cs.size()));
		assertThat(cs.size(), equalTo(gcs.size()));
		// 2. import to new group
		System.out.println("2. import to new group");
		file = new File(this.testFile2);
		try {
			this.vcardIE.importContactsToGroup(file, this.testGroup2);
		} catch (IOException ex) {
			fail("method importContacts failed because of IOException");
		}
		gs.clear();
		g = this.database.getGroupByName(this.testGroup2);
		gs.add(g);
		cs = this.database.getAllContacts();
		gcs = this.database.getAllContactsFromGroup(gs);
		assertThat(3, is(cs.size()));
		assertThat(2, is(gcs.size()));
	}

	/**
	 * Test of exportContacts method, of class VCardImportExport.
	 */
	@Test
	public void testExportContacts() {
		System.out.println("testing exportContacts method");
		File file = new File(this.testFile1);
		try {
			this.vcardIE.importContacts(file);
		} catch (IOException ex) {
			fail("method importContacts failed because of IOException");
		}
		File outFile = new File("test03.vcf");
		try {
			this.vcardIE.exportContacts(outFile, this.database.getAllContacts());
		} catch (IOException ex) {
			fail("method exportContacts failed because of IOException");
		}
		this.database.clearAllData();
		try {
			this.vcardIE.importContacts(outFile);
		} catch (IOException ex) {
			fail("method importContacts failed because of IOException");
		}
		assertThat(1, is(this.database.getAllContacts().size()));
		assertThat("Zboril Frantisek",
				equalTo(this.database.getAllContacts().get(0).getFullName()));
		outFile.delete();
	}
}