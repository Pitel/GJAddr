package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.gui.ContactsPanel;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.NotificationsEnum;
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
   * Current program version.
   */
  public final String PROGRAM_VERSION = "1.1";
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
   * Properties file location.
   */
  private final String PROPERTY_FILE = new File(System.getProperty("user.home"),
          "/.gjaddr/gjaddr.properties").getPath();
  /**
   * File with czech name days.
   */
  private String NAME_DAYS_FILE = new File("res", "svatky.xml").toString();
  /**
   * When to display notifications.
   */
  private final String NOTIFICATION_SETTINGS = "notificationSettings";
  /**
   * Application folder.
   */
  private final String APPLICATION_FOLDER = "applicationFolder";

  /**
   * Get data directory according to user home dir.
   *
   * @return
   */
  public String getDataDir() {
    File dataDir = new File(this.properties.getProperty(this.APPLICATION_FOLDER), "/.gjaddr/data");

    if (!dataDir.exists()) {
      dataDir.mkdirs();
    }

    return dataDir.getPath();
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
   *
   * @return true if is binary, otherwise false (XML persistance).
   */
  public boolean isBinPersistance() {
    return Boolean.parseBoolean(this.properties.getProperty(BIN_PERSISTANCE));
  }

  /**
   * Method for change current persistance. Switch between binary and XML. Always changed
   * one type to the other.
   */
  public void changePersistance() {
    this.properties.setProperty(BIN_PERSISTANCE, String.valueOf(!this.isBinPersistance()));
    String newValue = (isBinPersistance() ? "BINARY" : "XML");

    log("Persistance changed to " + newValue + ".");
    this.save();

    Database.getInstance().saveAllData();
  }

  /**
   * Gets current settings for showing contact name.
   *
   * @return true if firstname should be show as first, false for first surname.
   */
  public boolean isNameFirst() {
    return Boolean.parseBoolean(this.properties.getProperty(NAME_ORDER));
  }

  /**
   * Changed displaying first firstname or surname. Method changed the current state to
   * the second value.
   */
  public void changeNameOrder() {
    this.properties.setProperty(NAME_ORDER, String.valueOf(!this.isNameFirst()));
    String newValue = (isNameFirst() ? "FirstName" : "SurName");

    log("Order changed to " + newValue + " first.");
    this.save();

    ContactsPanel.fillTable(true, false);
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
      InputStream source = new FileInputStream(new File(PROPERTY_FILE));
      this.properties.load(source);
      log("Properties file loaded.");
    } catch (Exception e) {
      log("Properties file missing - create default properties.");
      this.setDefaultProperties();
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
    this.properties = new Properties();
    this.properties.setProperty(BIN_PERSISTANCE, "true");
    this.properties.setProperty(NAME_ORDER, "false");
    this.properties.setProperty(NOTIFICATION_SETTINGS, NotificationsEnum.MONTH.toString());
    this.properties.setProperty(APPLICATION_FOLDER, USER_HOME_DIR);
    this.save();
  }

  /**
   * Save properties file. Method is called always when is some property changed.
   */
  public void save() {
    try {
      OutputStream output = new FileOutputStream(PROPERTY_FILE);
      this.properties.store(output, "GJAddr 2012");
      log("Properties file saved.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Method for messages logging.
   *
   * @param msg message to log
   */
  private void log(String msg) {
    LoggerFactory.getLogger(this.getClass()).info(msg);
  }

  /**
   * Set when to display notifications.
   *
   * @param newSetting
   */
  public void setNotificationsSettings(NotificationsEnum newSetting) {
    LoggerFactory.getLogger(this.getClass()).info("Notification settings changed to : " + newSetting.toString());
    this.properties.setProperty(this.NOTIFICATION_SETTINGS, newSetting.toString());
    this.save();
  }

  /**
   * When to display notifications?
   *
   * @return
   */
  public NotificationsEnum getNotificationsSettings() {
    String setting = this.properties.getProperty(this.NOTIFICATION_SETTINGS);
    if (setting == null) {
      return NotificationsEnum.MONTH;
    } else if (setting.equalsIgnoreCase("month")) {
      return NotificationsEnum.MONTH;
    } else if (setting.equalsIgnoreCase("week")) {
      return NotificationsEnum.WEEK;
    } else if (setting.equalsIgnoreCase("day")) {
      return NotificationsEnum.DAY;
    } else {
      return NotificationsEnum.NEVER;
    }
  }

  /**
   * Set application folder.
   *
   * @param folder
   */
  public void setApplicationFolder(String folder) {
    File newFolder = new File(folder);

    if (!newFolder.exists()) {
      newFolder.mkdirs();
    }

    LoggerFactory.getLogger(this.getClass()).info("Changed application folder to " + folder + ".");
    this.properties.setProperty(this.APPLICATION_FOLDER, folder);
    this.save();
    Database.getInstance().saveAllData();
  }

  /**
   * Set default application folder (user.home).
   */
  public void setDefaultApplicationFolder() {
    LoggerFactory.getLogger(this.getClass()).info("Changed application folder to default.");
    this.properties.setProperty(this.APPLICATION_FOLDER, this.USER_HOME_DIR);
    this.save();
    Database.getInstance().saveAllData();
  }

  /**
   * Is application folder set to default?
   *
   * @return
   */
  public boolean isDefaultAppFolder() {
    String folder = this.properties.getProperty(this.APPLICATION_FOLDER);
    if (folder == null) {
      return true;
    } else if (folder.equals(this.USER_HOME_DIR)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Get path to application folder.
   *
   * @return
   */
  public String getAppFolder() {
    return this.properties.getProperty(this.APPLICATION_FOLDER);
  }
}