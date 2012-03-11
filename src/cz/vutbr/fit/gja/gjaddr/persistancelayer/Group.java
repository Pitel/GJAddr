/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

/**
 *
 * @author Ragaj
 */
public class Group 
{
  private int id;
  private String name;
  
  public String getName()
  {
    return this.name;
  }
  
  public Group (int id, String name)
  {
    this.id = id;
    this.name = name;
  }
}
