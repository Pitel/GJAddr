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
   * @return
   * @throws FacebookImportException
   */
  @Override
  public int runImport() throws FacebookImportException {
    FacebookImport fi = new FacebookImport();
    return fi.importContacts(this.group);
  }
}
