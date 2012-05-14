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
  
  /**
   * Key value for name order property.
   */
  private final String NAME_ORDER = "nameOrder";
  
  /**
   * Key value for persistance type property.
   */
  private final String BIN_PERSISTANCE = "binPersistance";  
  
  /**
   * User home directory constant.
   */
  private final String USER_HOME_DIR = System.getProperty("user.home");   
  
  /**
   * Properties file name location.
   */
  private String PROPERTIES_FILENAME = new File(getPropertiesDir(), "gjaddr.properties").toString();
  
  /**
   * File with czech name days.
   */
  private String NAME_DAYS_FILE = new File("res", "svatky.xml").toString();
  
  /**
   * Get data directory according to user home dir.
   * @return 
   */  
  public String getDataDir()	{
		
		File dataDir = new File(USER_HOME_DIR, "/.gjaddr/data");
		
		if (!dataDir.exists()) {
			dataDir.mkdirs();
		}
		
		return dataDir.getPath();
	} 
  
  /**
   * Get properties directory according to user home dir.
   * @return 
   */
  public String getPropertiesDir()	{
		
		File propertiesDir = new File(USER_HOME_DIR, "/.gjaddr/settings");
		
		if (!propertiesDir.exists()) {
			propertiesDir.mkdirs();
		}
		
		return propertiesDir.getPath();
	}

    /**
     * Get name of file with name days.
     * 
     * @return 
     */
    public String getNameDaysFile() {
        return NAME_DAYS_FILE;
    }
  
    

  /**
   * Current properties.
   */
  private Properties properties;
  
  /**
   * Check if current persistance is set to binary.
   * @return true if is binary, otherwise false (XML persistance).
   */
  public boolean isBinPersistance() {
    return Boolean.parseBoolean(this.properties.getProperty(BIN_PERSISTANCE));
  }
   
  /**
   * Method for change current persistance. Switch between binary and XML.
   * Always changed one type to the other.
   */
  public void changePersistance() {    
    this.properties.setProperty(BIN_PERSISTANCE, String.valueOf(!this.isBinPersistance()));
    String newValue = (isBinPersistance()? "BINARY" : "XML");
    
    log("Persistance changed to " + newValue + ".");      
    this.save();
    
    Database.getInstance().saveAllData();
  }
  
  /**
   * Gets current settings for showing contact name.
   * @return true if firstname should be show as first, false for first surname.
   */
  public boolean isNameFirst() {
    return Boolean.parseBoolean(this.properties.getProperty(NAME_ORDER));
  }

  /**
   * Changed displaying first firstname or surname.
   * Method changed the current state to the second value.
   */
  public void changeNameOrder() {    
    this.properties.setProperty(NAME_ORDER, String.valueOf(!this.isNameFirst()));
    String newValue = (isNameFirst()? "FirstName" : "SurName");
    
    log("Order changed to " + newValue + " first.");      
    this.save();
    
    ContactsPanel.fillTable(true);
  }  
  
  /** 
   * Singleton implementation, holds the settings instance.
   */
  private static Settings instance = null;
  
  /**
   * Private constructor, that is called only when is that class instantiated.
   */
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
  
  /**
   * Static accessor for access to the singleton instance.
   */
  public static Settings instance() {
    if (instance == null) {
      instance = new Settings();
    }
    
    return instance;
  }  
  
  /**
   * Sets the default properties values, if properties file missing.
   */
  private void setDefaultProperties() {
    this.properties.setProperty(BIN_PERSISTANCE, "true");
    this.properties.setProperty(NAME_ORDER, "false");    
  }
  
  /**
   * Save properties file. Method is called always when is some property changed.
   */
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
   * Method for messages logging.
   * @param msg message to log
   */
  private void log(String msg) {
    LoggerFactory.getLogger(this.getClass()).info(msg);
  }  
}