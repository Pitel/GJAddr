package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.gui.util.EscapeKeyHandler;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.slf4j.LoggerFactory;

/**
 * Display upcoming birthday.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class NotificationsWindow extends JFrame implements ActionListener {

  static final long serialVersionUID = 0;
  /**
   * Window buttons.
   */
  JButton noShowButton, okButton;
  /**
   * Contacts shown in notifications window.
   */
  List<Contact> contacts;
  /**
   * Instance of database.
   */
  private Database db = Database.getInstance();

  /**
   * Constructor.
   *
   * @param contacts
   */
  public NotificationsWindow(List<Contact> contacts) {
    super("Notifications");
    LoggerFactory.getLogger(this.getClass()).debug("Opening notifications window.");

    // save the list of contacts with event
    this.contacts = contacts;

    // set window apearance
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      System.err.println(e);
    }

    // set application icon
    ImageIcon icon = new ImageIcon(getClass().getResource("/res/icon.png"), "GJAddr");
    this.setIconImage(icon.getImage());

    // set page layout
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

    // main header
    this.add(this.createMainHeader());

    List<JPanel> birthdays = new ArrayList<JPanel>();
    List<JPanel> namedays = new ArrayList<JPanel>();
    List<JPanel> celebrations = new ArrayList<JPanel>();

    // add all contacts
    for (Contact c : contacts) {
      JPanel panelBday = this.createLineBday(c);
      if (panelBday != null) {
        birthdays.add(panelBday);
      }
      JPanel panelNameday = this.createLineNameday(c);
      if (panelNameday != null) {
        namedays.add(panelNameday);
      }
      JPanel panelCeleb = this.createLineCelebration(c);
      if (panelCeleb != null) {
        celebrations.add(panelCeleb);
      }
    }

    // add birthdays
    if (!birthdays.isEmpty()) {
      this.add(this.createBdayHeader());
      for (JPanel p : birthdays) {
        this.add(p);
      }
    }

    // add namedays
    if (!namedays.isEmpty()) {
      this.add(this.createNdayHeader());
      for (JPanel p : namedays) {
        this.add(p);
      }
    }

    // add celebrations
    if (!celebrations.isEmpty()) {
      this.add(this.createCelebHeader());
      for (JPanel p : celebrations) {
        this.add(p);
      }
    }

    // add buttons
    this.add(this.createButtonPanel());

    // make window escapable
    EscapeKeyHandler.setEscapeAction(this);
    
    // make window visible
    this.setAlwaysOnTop(true);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Create main window header.
   *
   * @return
   */
  private JPanel createMainHeader() {
    JPanel mainHeaderPanel = new JPanel(new GridLayout());
    JLabel mainHeader = new JLabel("<html><h3>Notifications</h3></html>", JLabel.LEFT);
    mainHeaderPanel.add(mainHeader);
    mainHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    return mainHeaderPanel;
  }

  /**
   * Create birthday header.
   *
   * @return
   */
  private JPanel createBdayHeader() {
    JPanel bdayHeaderPanel = new JPanel(new GridLayout());
    JLabel bdayHeader = new JLabel("<html><h4>Birthdays</h4></html>", JLabel.LEFT);
    bdayHeaderPanel.add(bdayHeader);
    bdayHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    return bdayHeaderPanel;
  }

  /**
   * Create nameday header.
   *
   * @return
   */
  private JPanel createNdayHeader() {
    JPanel namedayHeaderPanel = new JPanel(new GridLayout());
    JLabel namedayHeader = new JLabel("<html><h4>Name days</h4></html>", JLabel.LEFT);
    namedayHeaderPanel.add(namedayHeader);
    namedayHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    return namedayHeaderPanel;
  }

  /**
   * Create birthday header.
   *
   * @return
   */
  private JPanel createCelebHeader() {
    JPanel celebHeaderPanel = new JPanel(new GridLayout());
    JLabel celebHeader = new JLabel("<html><h4>Anniversaries</h4></html>", JLabel.LEFT);
    celebHeaderPanel.add(celebHeader);
    celebHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    return celebHeaderPanel;
  }

  /**
   * Create line consisting of name and event.
   *
   * @param c
   * @return
   */
  private JPanel createLineBday(Contact c) {
    if (c.getBirthday() == null || !c.getBirthday().shouldBeNotified() || c.getBirthday().isShowingDisabled()) {
      return null;
    }
    JPanel panel = new JPanel(new GridLayout());
    Format formatter = new SimpleDateFormat("d.M.yyyy");
    JLabel name = new JLabel(c.getFullName());
    panel.add(name);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    JLabel event = new JLabel(formatter.format(c.getBirthday().getDate()));
    panel.add(event);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    return panel;
  }

  /**
   * Create line consisting of name and event.
   *
   * @param c
   * @return
   */
  private JPanel createLineNameday(Contact c) {
    if (c.getNameDay() == null || !c.getNameDay().shouldBeNotified() || c.getNameDay().isShowingDisabled()) {
      return null;
    }
    JPanel panel = new JPanel(new GridLayout());
    Format formatter = new SimpleDateFormat("d.M.");
    JLabel name = new JLabel(c.getFullName());
    panel.add(name);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    JLabel event = new JLabel(formatter.format(c.getNameDay().getDate()));
    panel.add(event);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    return panel;
  }

  /**
   * Create line consisting of name and event.
   *
   * @param c
   * @return
   */
  private JPanel createLineCelebration(Contact c) {
    if (c.getCelebration() == null || !c.getCelebration().shouldBeNotified() || c.getCelebration().isShowingDisabled()) {
      return null;
    }
    JPanel panel = new JPanel(new GridLayout());
    Format formatter = new SimpleDateFormat("d.M.yyyy");
    JLabel name = new JLabel(c.getFullName());
    panel.add(name);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    JLabel event = new JLabel(formatter.format(c.getCelebration().getDate()));
    panel.add(event);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    return panel;
  }

  /**
   * Create button panel.
   *
   * @return
   */
  private JPanel createButtonPanel() {
    this.noShowButton = new JButton("Don't show again");
    this.noShowButton.addActionListener(this);
    this.noShowButton.setIcon(new ImageIcon(getClass().getResource("/res/cancel.png")));
    this.noShowButton.setIconTextGap(10);
    // OK button
    this.okButton = new JButton("OK");
    this.okButton.addActionListener(this);
    this.okButton.setIcon(new ImageIcon(getClass().getResource("/res/confirm.png")));
    this.okButton.setIconTextGap(10);
    // panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    buttonPanel.add(this.okButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    buttonPanel.add(this.noShowButton);
    buttonPanel.add(Box.createGlue());
    return buttonPanel;
  }

  /**
   * Assign components actions.
   *
   * @param ae
   */
  @Override
  public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == this.noShowButton) {
      for (Contact c : this.contacts) {
        if (c.getBirthday() != null && c.getBirthday().shouldBeNotified()) {
          c.disableBirthdayShowing();
        }
        if (c.getNameDay() != null && c.getNameDay().shouldBeNotified()) {
          c.disableNameDayShowing();
        }
        if (c.getCelebration() != null && c.getCelebration().shouldBeNotified()) {
          c.disableCelebrationShowing();
        }
        this.db.updateContact(c);
      }
      this.dispose();
    } else if (ae.getSource() == this.okButton) {
      this.dispose();
    }
  }
}
