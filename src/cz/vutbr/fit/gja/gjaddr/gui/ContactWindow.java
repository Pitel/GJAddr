package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.gui.util.Validators;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.NameDays;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.TypesEnum;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.MessengersEnum;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import org.slf4j.LoggerFactory;

/**
 * Contact editing window.
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 * @author Bc. Radek Gajdusek <xgajdu07@stud.fit,vutbr.cz>
 */
class ContactWindow extends JFrame {

  static final long serialVersionUID = 0;
  /**
   * Database instance.
   */
  private final Database db = Database.getInstance();
  /**
   * Currently edited contact.
   */
  private Contact contact;
  private final JButton button = new JButton();
  private final PhotoButton photo = new PhotoButton();
  private final JTextField nameField = new JTextField();
  private final JTextField surnameField = new JTextField();
  private final JTextField nicknameField = new JTextField();
  private final JTextField addressField = new JTextField();
  private final JTextField workEmailField = new JTextField();
  private final JTextField homeEmailField = new JTextField();
  private final JTextField otherEmailField = new JTextField();
  private final JTextField workUrlField = new JTextField();
  private final JTextField homeUrlField = new JTextField();
  private final JTextField otherUrlField = new JTextField();
  private final JTextField workPhoneField = new JTextField();
  private final JTextField homePhoneField = new JTextField();
  private final JTextField otherPhoneField = new JTextField();
  private final JTextField icqField = new JTextField();
  private final JTextField jabberField = new JTextField();
  private final JTextField skypeField = new JTextField();
  private final JTextArea noteField = new JTextArea();
  private final JXDatePicker birthdayPicker = new JXDatePicker();
  private final JXDatePicker namedayPicker = new JXDatePicker();
  private final JXDatePicker celebrationPicker = new JXDatePicker();

  /**
   * Constructor for adding new contact
   */
  public ContactWindow() {
    super("Add contact");
    setIconImage(new ImageIcon(getClass().getResource("/res/plus.png"), "+").getImage());
    button.setText("Add contact");
    button.addActionListener(new NewContactActionListener());
    contact = new Contact();
    prepare();
    log("Opening new contact window.");
  }

  /**
   * Constructor for editing contact
   */
  public ContactWindow(Contact contact) {
    super("Edit contact");
    this.contact = contact;
    setIconImage(new ImageIcon(getClass().getResource("/res/edit.png"), "Edit").getImage());
    photo.setContact(contact);
    button.setText("Edit contact");
    button.addActionListener(new EditContactActionListener());
    nameField.setText(contact.getFirstName());
    surnameField.setText(contact.getSurName());
    nicknameField.setText(contact.getNickName());
    addressField.setText(contact.getAllAddresses());
    birthdayPicker.setDate(contact.getBirthday() != null ? contact.getBirthday().getDate() : null);
    namedayPicker.setDate(contact.getNameDay() != null ? contact.getNameDay().getDate() : null);
    celebrationPicker.setDate(contact.getCelebration() != null ? contact.getCelebration().getDate() : null);
    noteField.setText(contact.getNote());
    for (Url u : contact.getUrls()) {
      if (u.getValue() != null) {
        switch (u.getType()) {
          case WORK:
            workUrlField.setText(u.getValue().toString());
            break;
          case HOME:
            homeUrlField.setText(u.getValue().toString());
            break;
          case OTHER:
            otherUrlField.setText(u.getValue().toString());
            break;
        }
      }
    }
    for (Email e : contact.getEmails()) {
      switch (e.getType()) {
        case WORK:
          workEmailField.setText(e.getEmail());
          break;
        case HOME:
          homeEmailField.setText(e.getEmail());
          break;
        case OTHER:
          otherEmailField.setText(e.getEmail());
          break;
      }
    }
    for (PhoneNumber p : contact.getPhoneNumbers()) {
      switch (p.getType()) {
        case WORK:
          workPhoneField.setText(p.getNumber());
          break;
        case HOME:
          homePhoneField.setText(p.getNumber());
          break;
        case OTHER:
          otherPhoneField.setText(p.getNumber());
          break;
      }
    }
    for (Messenger m : contact.getMessenger()) {
      if (m.getValue() != null) {
        switch (m.getType()) {
          case ICQ:
            icqField.setText(m.getValue());
            break;
          case JABBER:
            jabberField.setText(m.getValue());
            break;
          case SKYPE:
            skypeField.setText(m.getValue());
            break;
        }
      }
    }
    prepare();
    log("Opening edit contact window.");
  }

  /**
   * Method for creating window content layout.
   */
  private void prepare() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      System.err.println(e);
    }
    final JPanel form = new JPanel(new GridBagLayout());
    form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    final GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(1, 1, 1, 1);
    c.weightx = 0;
    c.anchor = GridBagConstraints.NORTHWEST;
    c.gridx = 0;
    c.gridy = 0;
    form.add(new JLabel("Photo"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(photo, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Name"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(nameField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Surname"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(surnameField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Nickname"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(nicknameField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Address"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(addressField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Birthday"), c);
    c.gridx = 1;
    c.weightx = 1;
    birthdayPicker.setFormats(new String[]{"d. M. yyyy"});
    form.add(birthdayPicker, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Nameday"), c);
    c.gridx = 1;
    c.weightx = 1;
    namedayPicker.setFormats(new String[]{"d. M."});
    form.add(namedayPicker, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Celebration"), c);
    c.gridx = 1;
    c.weightx = 1;
    celebrationPicker.setFormats(new String[]{"d. M. yyyy"});
    form.add(celebrationPicker, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Work E-mail"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(workEmailField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Home E-mail"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(homeEmailField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Other E-mail"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(otherEmailField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Work URL"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(workUrlField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Home URL"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(homeUrlField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Other URL"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(otherUrlField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Work Phone"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(workPhoneField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Home Phone"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(homePhoneField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Other Phone"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(otherPhoneField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("ICQ"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(icqField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Jabber"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(jabberField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Skype"), c);
    c.gridx = 1;
    c.weightx = 1;
    form.add(skypeField, c);
    c.gridy++;
    c.gridx = 0;
    c.weightx = 0;
    form.add(new JLabel("Note"), c);
    c.gridx = 1;
    c.weightx = 1;
    noteField.setLineWrap(true);
    noteField.setWrapStyleWord(true);
    noteField.setRows(3);
    form.add(noteField, c);
    add(form, BorderLayout.CENTER);
    add(button, BorderLayout.PAGE_END);
    setLocationRelativeTo(null);
    pack();
    setVisible(true);
  }

  /**
   * Resolving contact data from user form input.
   *
   * @return true if is all correct, in error case returns false
   */
  private boolean resolvecontact() {
    boolean valid = this.validateData();
    if (valid) {
      contact.setFirstName(nameField.getText());
      contact.setSurName(surnameField.getText());
      contact.setNickName(nicknameField.getText());
      contact.setBirthday(birthdayPicker.getDate());
      if (namedayPicker.getDate() != null) {
        contact.setNameDay(namedayPicker.getDate());
      } else {
        if (!contact.getFirstName().isEmpty()) {
          Calendar nameDay = NameDays.getInstance().getNameDay(contact.getFirstName());
          if (nameDay != null) {
            contact.setNameDay(nameDay.getTime());
          }
        }
      }
      contact.setCelebration(celebrationPicker.getDate());
      contact.setNote(noteField.getText());

      final ArrayList<Address> addresses = new ArrayList<Address>();
      addresses.add(new Address(TypesEnum.HOME, addressField.getText()));
      contact.setAdresses(addresses);

      final ArrayList<Url> urls = new ArrayList<Url>();
      urls.add(new Url(TypesEnum.WORK, workUrlField.getText()));
      urls.add(new Url(TypesEnum.HOME, homeUrlField.getText()));
      urls.add(new Url(TypesEnum.OTHER, otherUrlField.getText()));
      contact.setUrls(urls);

      final ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();
      phones.add(new PhoneNumber(TypesEnum.WORK, workPhoneField.getText()));
      phones.add(new PhoneNumber(TypesEnum.HOME, homePhoneField.getText()));
      phones.add(new PhoneNumber(TypesEnum.OTHER, otherPhoneField.getText()));
      contact.setPhoneNumbers(phones);

      final ArrayList<Email> emails = new ArrayList<Email>();
      emails.add(new Email(TypesEnum.WORK, workEmailField.getText()));
      emails.add(new Email(TypesEnum.HOME, homeEmailField.getText()));
      emails.add(new Email(TypesEnum.OTHER, otherEmailField.getText()));
      contact.setEmails(emails);

      final ArrayList<Messenger> messengers = new ArrayList<Messenger>();
      messengers.add(new Messenger(MessengersEnum.ICQ, icqField.getText()));
      messengers.add(new Messenger(MessengersEnum.JABBER, jabberField.getText()));
      messengers.add(new Messenger(MessengersEnum.SKYPE, skypeField.getText()));
      contact.setMessenger(messengers);
    }
    return valid;
  }

  /**
   * Check if is email and url valid, if is not valid, display a message.
   *
   * @return true if is validation successfull, otherwise false.
   */
  private boolean validateData() {
    StringBuilder message = new StringBuilder();

    // required entry is one of these - nick, name, surname
    if (nameField.getText().isEmpty()
            && surnameField.getText().isEmpty()
            && nicknameField.getText().isEmpty()) {
      message.append("Name, Surname or Nickname shouldn't be empty. Please fill at least one item.");
    }

    if (!Validators.isEmailValid(workEmailField.getText())) {
      message.append("Work email address is not valid\r\n");
    }
    if (!Validators.isEmailValid(homeEmailField.getText())) {
      message.append("Home email address is not valid\r\n");
    }
    if (!Validators.isEmailValid(otherEmailField.getText())) {
      message.append("Other email address is not valid\r\n");
    }
    if (!Validators.isUrlValid(workUrlField.getText())) {
      message.append("Work URL is not valid\r\n");
    }
    if (!Validators.isUrlValid(homeUrlField.getText())) {
      message.append("Home URL is not valid\r\n");
    }
    if (!Validators.isUrlValid(otherUrlField.getText())) {
      message.append("Other URL is not valid\r\n");
    }
    if (!Validators.isPhoneNumberValid(workPhoneField.getText())) {
      message.append("Work phone is not valid\r\n");
    }
    if (!Validators.isPhoneNumberValid(homePhoneField.getText())) {
      message.append("Home phone is not valid\r\n");
    }
    if (!Validators.isPhoneNumberValid(otherPhoneField.getText())) {
      message.append("Other phone is not valid\r\n");
    }

    // display message if is there same error
    if (message.length() > 0) {
      JOptionPane.showMessageDialog(this, message, "Validation failed!", JOptionPane.WARNING_MESSAGE);
    }

    return message.length() == 0;
  }

  /**
   * Submiting new contact action
   */
  private class NewContactActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (resolvecontact()) {
        List<Contact> newContacts = new ArrayList<Contact>();
        newContacts.add(contact);
        db.addNewContacts(newContacts);
        // update tables
        ContactsPanel.fillTable(false, true);
        GroupsPanel.fillList();
        log("Closing new contact window.");
        dispose();
      }
    }
  }

  /**
   * Confirming contact change action
   */
  private class EditContactActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (resolvecontact()) {
        db.updateContact(contact);
        ContactsPanel.fillTable(true, false);
        log("Closing edit contact window.");
        dispose();
      }
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
}
