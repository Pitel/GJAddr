package cz.vutbr.fit.gja.gjaddr.importexport;

import cz.vutbr.fit.gja.gjaddr.importexport.exception.GoogleImportException;

/**
 * Thread for import from Google.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class GoogleImportThread extends ImportThread {

  /**
   * Run import.
   *
   * @return
   * @throws GoogleImportException
   */
  @Override
  public int runImport() throws GoogleImportException {
    GoogleImport gi = new GoogleImport();
    return gi.importContacts(this.group);
  }
}
