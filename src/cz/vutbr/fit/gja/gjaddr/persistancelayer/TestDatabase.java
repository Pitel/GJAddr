/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

/**
 *
 * @author Ragaj
 */
public class TestDatabase 
{
  public static void fillTestingData(Database db)
  { 
    System.out.println("Fill db ...");
    
    db.executeUpdate("INSERT INTO contact (firstName, surName, nickName) VALUES ('Radek', 'Gajdusek', 'Speedy');");
    db.executeUpdate("INSERT INTO url (type, value, contactId) VALUES (1, 'www.lahvators.cz', 1);");
   
    fillGroups(db);
  }
  
  private static void fillGroups(Database db)
  {
    db.executeUpdate("INSERT INTO category (name) VALUES ('Lahvators');"); 
    db.executeUpdate("INSERT INTO category (name) VALUES ('Fit');");     
    db.executeUpdate("INSERT INTO category (name) VALUES ('DPMB');");   
    db.executeUpdate("INSERT INTO category (name) VALUES ('Test');");   
  }
}
