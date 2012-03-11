/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.util.List;

/**
 *
 * @author Ragaj
 */
public interface IDatabase 
{
  // CONTACTS
  List<Contact> getAllContacts();  
  
  // GROUPS
  List<Group> getAllGroups();    
}
