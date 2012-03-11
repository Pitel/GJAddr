/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.gja.gjaddr.tests;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.TestDatabase;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.util.List;

/**
 *
 * @author Ragaj
 */
public class TestClass 
{

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) 
  {
    // step 1 - get database instance
    Database db = Database.getInstance();
    
    // step 2 - fill the testing data
    TestDatabase.fillTestingData(db);
    
    // step 3 - get all contacts
    List<Contact> contacts = db.getAllContacts();
    
    // step 4 - get all groups    
    List<Group> groups = db.getAllGroups();
    
    
  }
}
