package cz.vutbr.fit.gja.gjaddr.importexport;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Settings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import org.junit.*;

/**
 * Test CsvImportExport class.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class CsvImportExportTest {

    private CsvImportExport csvIE;
    private Database database = Database.getInstance();
    private String testGroup1 = "testGroup1";
    private String testGroup2 = "testGroup2";
    private String testFile1 = this.getClass().getResource("./testFiles/test01.csv").getPath();
    private String testFile2 = this.getClass().getResource("./testFiles/test02.csv").getPath();

    public CsvImportExportTest() {
        this.database.clearAllData();
        this.csvIE = null;
        if (!Settings.instance().isNameFirst()) {
            Settings.instance().changeNameOrder();
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("CSV_IMPORT_EXPORT TEST START");
        System.out.println("----------------------------");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("--------------------------");
        System.out.println("CSV_IMPORT_EXPORT TEST END");
    }

    @Before
    public void setUp() {
        this.database.clearAllData();
        this.csvIE = new CsvImportExport();
    }

    @After
    public void tearDown() {
        this.database.clearAllData();
        this.csvIE = null;
    }

    /**
     * Test of importContacts method, of class CsvImportExport.
     */
    @Test
    public void testImportContacts() throws Exception {
        System.out.println("testing importContacts method");
        // import just one contact and test it's values
        System.out.println("1. import one contact");
        File file = new File(this.testFile1);
        try {
            this.csvIE.importContacts(file);
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
        // TODO URLs
        // assertThat(1, equalTo(c.getUrls().size()));
        // import multiple contacts
        System.out.println("2. import multiples contacts");
        this.database.clearAllData();
        file = new File(this.testFile2);
        try {
            this.csvIE.importContacts(file);
        } catch (IOException ex) {
            fail("method importContacts failed because of IOException");
        }
        cs = this.database.getAllContacts();
        assertThat(2, is(cs.size()));
        c = cs.get(0);
        assertThat(2, equalTo(c.getPhoneNumbers().size()));
    }

    /**
     * Test of importContacts method, of class CsvImportExport.
     */
    @Test
    public void testImportContactsToGroup() throws Exception {
        System.out.println("testing importContacts method");
        // 1. import to existing group
        System.out.println("1. import to existing group");
        Group g = new Group(this.testGroup1);
        this.database.addNewGroup(g.getName());
        File file = new File(this.testFile1);
        try {
            this.csvIE.importContacts(file, g.getName());
        } catch (IOException ex) {
            fail("method importContacts failed because of IOException");
        }
        List<Group> gs = new ArrayList<Group>();
        gs.add(g);
        List<Contact> cs = this.database.getAllContacts();
        List<Contact> gcs = this.database.getAllContactsFromGroups(gs);
        assertThat(1, is(cs.size()));
        assertThat(cs.size(), equalTo(gcs.size()));
        // 2. import to new group
        System.out.println("2. import to new group");
        file = new File(this.testFile2);
        try {
            this.csvIE.importContacts(file, this.testGroup2);
        } catch (IOException ex) {
            fail("method importContacts failed because of IOException");
        }
        gs.clear();
        g = this.database.getGroupByName(this.testGroup2);
        gs.add(g);
        cs = this.database.getAllContacts();
        gcs = this.database.getAllContactsFromGroups(gs);
        assertThat(3, is(cs.size()));
        assertThat(2, is(gcs.size()));
    }

    /**
     * Test of exportContacts method, of class CsvImportExport.
     */
    @Test
    public void testExportContacts() throws Exception {
        System.out.println("testing exportContacts method");
        File file = new File(this.testFile1);
        try {
            this.csvIE.importContacts(file);
        } catch (IOException ex) {
            fail("method importContacts failed because of IOException");
        }
        File outFile = new File("test03.vcf");
        try {
            this.csvIE.exportContacts(outFile, this.database.getAllContacts());
        } catch (IOException ex) {
            fail("method exportContacts failed because of IOException");
        }
        this.database.clearAllData();
        try {
            this.csvIE.importContacts(outFile);
        } catch (IOException ex) {
            fail("method importContacts failed because of IOException");
        }
        assertThat(1, is(this.database.getAllContacts().size()));
        assertThat("Frantisek Zboril",
                equalTo(this.database.getAllContacts().get(0).getFullName()));
        outFile.delete();
    }
}