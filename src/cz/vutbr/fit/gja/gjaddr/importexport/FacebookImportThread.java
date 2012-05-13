
package cz.vutbr.fit.gja.gjaddr.importexport;

import cz.vutbr.fit.gja.gjaddr.importexport.exception.FacebookImportException;

/**
 * Thread for import from Facebook.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class FacebookImportThread extends ImportThread {

    /**
     * Run import.
     * 
     * @param group
     * @return
     * @throws FacebookImportException 
     */
	@Override
	public int runImport(String group) throws FacebookImportException {
		FacebookImport fi = new FacebookImport();
		return fi.importContacts(this.progress, group);
	}
	
}
