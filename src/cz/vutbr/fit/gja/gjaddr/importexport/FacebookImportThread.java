
package cz.vutbr.fit.gja.gjaddr.importexport;

import cz.vutbr.fit.gja.gjaddr.importexport.exception.FacebookImportException;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;

/**
 *
 * @author xherrm01
 */
public class FacebookImportThread extends ImportThread {

	@Override
	public int runImport(String group) throws FacebookImportException {
		FacebookImport fi = new FacebookImport();
		return fi.importContacts(this.progress, group);
	}
	
}
