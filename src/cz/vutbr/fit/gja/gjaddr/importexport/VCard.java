package cz.vutbr.fit.gja.gjaddr.importexport;

import a_vcard.android.syncml.pim.VDataBuilder;
import a_vcard.android.syncml.pim.VNode;
import a_vcard.android.syncml.pim.vcard.VCardException;
import a_vcard.android.syncml.pim.vcard.VCardParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Class for importing and exporting vCards
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit.vutbr.cz>
 */
public class VCard {
	/**
	 * Import vCard file
	 *
	 * @param file File to import
	 * @exception FileNotFoundException vCard file not found
	 * @exception IOException vCard file could not be read
	 * @exception VCardException vCard could not be parsed
	 */
	public void im(File file) throws FileNotFoundException, VCardException, IOException {
		final VCardParser parser = new VCardParser();
		final VDataBuilder builder = new VDataBuilder();

		final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		final StringBuilder vcardStringBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {	//Maybe there is some method to read the whole file
			vcardStringBuilder.append(line + "\n");
		}
		reader.close();

		if (!parser.parse(vcardStringBuilder.toString(), "UTF-8", builder)) {
			throw new VCardException("Could not parse vCard file: " + file);
		}

		for (VNode contact : builder.vNodeList) {
			//do something here
		}

		return;
	}

	/**
	 * Export to vCard file
	 */
	public void ex() {
		return;
	}
}
