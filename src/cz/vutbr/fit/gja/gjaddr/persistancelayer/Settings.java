package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.gui.ContactsPanel;
import java.io.*;
import java.util.Properties;
import org.slf4j.LoggerFactory;

/**
 * Class with user settings configuration.
 * 
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Settings {
  
  private final String NAME_ORDER = "nameOrder";
  private final String BIN_PERSISTANCE = "binPersistance";  
  
  private final String USER_HOME_DIR = System.getProperty("user.home");   
  private String PROPERTIES_FILENAME = new File(getPropertiesDir(), "gjaddr.properties").toString();
  
  public String getDataDir()	{
		
		File dataDir = new File(USER_HOME_DIR, "/.gjaddr/data");
		
		if (!dataDir.exists()) {
			dataDir.mkdirs();
		}
		
		return dataDir.getPath();
	} 
  
  public String getPropertiesDir()	{
		
		File propertiesDir = new File(USER_HOME_DIR, "/.gjaddr/settings");
		
		if (!propertiesDir.exists()) {
			propertiesDir.mkdirs();
		}
		
		return propertiesDir.getPath();
	}   

  private Properties properties;
  
  public boolean isBinPersistance() {
    return Boolean.parseBoolean(this.properties.getProperty(BIN_PERSISTANCE));
  }
   
  public void changePersistance() {    
    this.properties.setProperty(BIN_PERSISTANCE, String.valueOf(!this.isBinPersistance()));
    String newValue = (isBinPersistance()? "BINARY" : "XML");
    
    log("Persistance changed to " + newValue + ".");      
    this.save();
    
    Database.getInstance().saveAllData();
  }
  
  public boolean isNameFirst() {
    return Boolean.parseBoolean(this.properties.getProperty(NAME_ORDER));
  }

  public void changeNameOrder() {    
    this.properties.setProperty(NAME_ORDER, String.valueOf(!this.isNameFirst()));
    String newValue = (isNameFirst()? "FirstName" : "SurName");
    
    log("Order changed to " + newValue + " first.");      
    this.save();
    
    ContactsPanel.fillTable(true);
  }  
  
  private static Settings instance = null;
  private Settings() {
    try {
      this.properties = new Properties();
      InputStream source = new FileInputStream(new File(PROPERTIES_FILENAME));
      this.properties.load(source);  
      log("Properties file loaded.");
    }
    catch (Exception e) {
      this.setDefaultProperties();
      log("Properties file missing - using default properties."); 
    }
  }  
  
  public static Settings instance() {
    if (instance == null) {
      instance = new Settings();
    }
    
    return instance;
  }  
  
  private void setDefaultProperties() {
    this.properties.setProperty(BIN_PERSISTANCE, "true");
    this.properties.setProperty(NAME_ORDER, "false");    
  }
  
  public void save() {
    try {
      OutputStream output = new FileOutputStream(new File(PROPERTIES_FILENAME));
      this.properties.store(output, "GJAddr 2012");
      log("Properties file saved.");      
    }
    catch (Exception e) {
      e.printStackTrace();
    }    
  }
  
  /**
   * Method for logging actions.
   * @param msg message to log
   */
  private void log(String msg) {
    LoggerFactory.getLogger(this.getClass()).info(msg);
  }  
}