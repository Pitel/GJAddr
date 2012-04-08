
package cz.vutbr.fit.gja.gjaddr.importexport;

import au.com.bytecode.opencsv.CSVWriter;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for importing and exporting contacts from/to CSV.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class Csv {

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

			CSVWriter writer = new CSVWriter(new FileWriter(file), '\t');
			// TODO correct format

			for (Contact c : contacts) {
				List<String> entries = new ArrayList<String>();
				entries.add(c.getSurName());
				entries.add(c.getFirstName());
				entries.add(c.getNickName());
				entries.add(c.getNote());
				entries.add(c.getAllEmails());
				entries.add(c.getAllPhones());
				entries.add(c.getAllAddresses());
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
