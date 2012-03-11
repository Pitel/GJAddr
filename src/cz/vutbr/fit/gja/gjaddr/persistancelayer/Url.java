/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

/**
 *
 * @author Ragaj
 */
public class Url 
{
  private int id;
  
  private int type;
  private String value;  
  
  public int getType()
  {
    return this.type;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public Url (int id, int type, String value)
  {
    this.id = id;
    this.type = type;
    this.value = value;
  }
}
