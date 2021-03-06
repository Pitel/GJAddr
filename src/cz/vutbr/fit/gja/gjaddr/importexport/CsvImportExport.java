package cz.vutbr.fit.gja.gjaddr.importexport;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.MessengersEnum;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.TypesEnum;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Class for importing and exporting contacts from/to CSV.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 * @see <http://opencsv.sourceforge.net/>
 */
public class CsvImportExport {

    /**
     * Application database.
     */
    private Database database = Database.getInstance();

    /**
     * Get group by it's name.
     *
     * @param groupName
     * @return
     */
    private Group getGroupByName(String groupName) {
        for (Group g : this.database.getAllGroups()) {
            if (g.getName().equals(groupName)) {
                return g;
            }
        }
        return null;
    }

    /**
     * Import contacts from CSV file.
     *
     * @param file
     */
    public int importContacts(File file) throws IOException {
        return this.importContacts(file, null);
    }

    /**
     * Import contacts from CSV file to specific group.
     *
     * @param file
     * @param group
     */
    public int importContacts(File file, String group) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(file));
        List<Contact> contacts = new ArrayList<Contact>();
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            // each line must have 7 parts
            if (nextLine.length < 7) {
                continue;
            }

            // create new contact
            Contact contact = new Contact(nextLine[1], nextLine[0], nextLine[2], nextLine[3]);

            // set contact emails
            String[] emails = nextLine[4].split(",");
            List<Email> emailsList = new ArrayList<Email>();
            for (String email : emails) {
                emailsList.add(new Email(TypesEnum.HOME, email));
            }
            contact.setEmails(emailsList);

            // set contact phones
            String[] phones = nextLine[5].split(",");
            List<PhoneNumber> phonesList = new ArrayList<PhoneNumber>();
            for (String phone : phones) {
                phonesList.add(new PhoneNumber(TypesEnum.HOME, phone.trim()));
            }
            contact.setPhoneNumbers(phonesList);

            // set contact addresses
            String[] addresses = nextLine[6].split(";");
            List<Address> addressesList = new ArrayList<Address>();
            for (String address : addresses) {
                addressesList.add(new Address(TypesEnum.HOME, address.replaceAll("(\\r|\\n)", ", ")));
            }
            contact.setAdresses(addressesList);
            
            // set contact messengers
            String[] messengers = nextLine[7].split(",");
            LoggerFactory.getLogger(this.getClass()).debug(String.valueOf(messengers.length));
            List<Messenger> messengerList = new ArrayList<Messenger>();
            for (String messenger : messengers) {
                LoggerFactory.getLogger(this.getClass()).debug(messenger);
                String[] m = messenger.split(":");
                if (m.length < 2) {
                    messengerList.add(new Messenger(MessengersEnum.OTHER, messenger.trim()));
                } else {
                    if (m[0].trim().equalsIgnoreCase(MessengersEnum.ICQ.toString())) {
                        messengerList.add(new Messenger(MessengersEnum.ICQ, m[1].trim()));
                    } else if (m[0].trim().equalsIgnoreCase(MessengersEnum.JABBER.toString())) {
                        messengerList.add(new Messenger(MessengersEnum.JABBER, m[1].trim()));
                    } else if (m[0].trim().equalsIgnoreCase(MessengersEnum.SKYPE.toString())) {
                        messengerList.add(new Messenger(MessengersEnum.SKYPE, m[1].trim()));
                    } else {
                        messengerList.add(new Messenger(MessengersEnum.OTHER, m[1].trim()));
                    }
                }                
            }
            contact.setMessenger(messengerList);
            
            // set contact URLs
            String[] urls = nextLine[8].split(",");
            List<Url> urlList = new ArrayList<Url>();
            for (String url : urls) {
                urlList.add(new Url(TypesEnum.OTHER, url.trim()));
            }
            contact.setUrls(urlList);

            // add contact to list to be imported
            contacts.add(contact);
            LoggerFactory.getLogger(this.getClass()).debug("Adding contact ["
                    + nextLine[1] + " " + nextLine[0] + "].");
        }

        // save contacts in database
        if (group == null || group.isEmpty()) {
            LoggerFactory.getLogger(this.getClass()).debug("Group is empty.");
        } else {
            LoggerFactory.getLogger(this.getClass()).debug("Adding contacts to : " + group);
        }

        // first add contacts to database
        this.database.addNewContacts(contacts);

        // then add to group
        if (group != null) {
            this.database.addNewGroup(group);
            Group dbGroup = this.getGroupByName(group);
            if (dbGroup != null) {
                this.database.addContactsToGroup(dbGroup, contacts);
            }
        }

        return contacts.size();
    }

    /**
     * Export contacts to CSV file.
     *
     * @param file
     * @param contacts
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void exportContacts(File file, List<Contact> contacts)
            throws FileNotFoundException, IOException {

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);

            CSVWriter writer = new CSVWriter(new FileWriter(file));

            for (Contact c : contacts) {
                List<String> entries = new ArrayList<String>();
                entries.add(c.getSurName());
                entries.add(c.getFirstName());
                entries.add(c.getNickName());
                entries.add(c.getNote());
                entries.add(c.getAllEmails());
                entries.add(c.getAllPhones());
                entries.add(c.getAllAddresses());
                entries.add(c.getAllMessengersWithType());
                entries.add(c.getAllUrls());
                writer.writeNext(entries.toArray(new String[entries.size()]));
            }

            writer.close();
        } finally {
            try {
                osw.close();
            } finally {
                fos.close();
            }
        }
    }
}
