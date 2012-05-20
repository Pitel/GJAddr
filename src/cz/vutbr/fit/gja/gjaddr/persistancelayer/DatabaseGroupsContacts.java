package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with group contacts relationship.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class DatabaseGroupsContacts {

  /**
   * GC collection.
   */
  private List<GroupContact> groupsContacts = null;

  /**
   * Constructor.
   */
  public DatabaseGroupsContacts() {
    this.load();
  }

  /**
   * Get current persistance filename.
   */
  private String getFilename() {
    return new File(Settings.instance().getDataDir(), "groupsContacts").getPath();
  }

  /**
   * Add contacts to the group. TODO check if is the number currently not in the list
   *
   * @param group group
   * @param contactsToAdd list of contacts.
   */
  void addContactsToGroup(Group group, List<Contact> contactsToAdd) {
    for (Contact contact : contactsToAdd) {
      GroupContact gc = new GroupContact(group.getId(), contact.getId());
      this.groupsContacts.add(gc);
    }
  }

  /**
   * Update groups contacts.s
   *
   * @param groups list of groups
   * @param contacts list of contacts
   *
   */
  void updateGroupsContacts(List<Group> groups, List<Contact> contacts) {
    this.removeGroupsEntries(groups);
    for (Group group : groups) {
      this.addContactsToGroup(group, contacts);
    }
  }

  /**
   * Update contacts groups
   *
   * @param contacts list of contacts
   * @param groups list of groups
   */
  void updateContactsGroups(List<Contact> contacts, List<Group> groups) {
    this.removeContactsEntries(contacts);
    for (Group group : groups) {
      this.addContactsToGroup(group, contacts);
    }
  }

  /**
   * Remove contacts from group
   *
   * @param group group
   * @param contactsToRemove contacts to remove
   */
  void removeContactsFromGroup(Group group, List<Contact> contactsToRemove) {
    List<GroupContact> entriesToRemove = new ArrayList<GroupContact>();
    List<Integer> contactsToRemoveIds = new ArrayList<Integer>();

    for (Contact contact : contactsToRemove) {
      contactsToRemoveIds.add(contact.getId());
    }

    List<Integer> contactsFromGroup = this.filterByGroupId(group.getId());

    for (GroupContact gc : this.groupsContacts) {
      int contactId = gc.getContactId();

      // remove all entries, that are:
      // 1. assign to the required group
      // 2. delete is required as a second parameter
      if (contactsFromGroup.contains(contactId) && contactsToRemoveIds.contains(contactId)) {
        entriesToRemove.add(gc);
      }
    }

    this.groupsContacts.removeAll(entriesToRemove);
  }

  /**
   * Remove contacts relations.
   *
   * @param contactsToRemove contacts to remove
   */
  void removeContactsEntries(List<Contact> contactsToRemove) {
    List<GroupContact> entriesToRemove = new ArrayList<GroupContact>();
    for (Contact contact : contactsToRemove) {
      for (GroupContact gc : this.groupsContacts) {
        if (gc.getContactId() == contact.getId()) {
          entriesToRemove.add(gc);
        }
      }
    }

    this.groupsContacts.removeAll(entriesToRemove);
  }

  /**
   * Remove groups relations.
   *
   * @param groupsToRemove groups to remove
   */
  void removeGroupsEntries(List<Group> groupsToRemove) {
    List<GroupContact> entriesToRemove = new ArrayList<GroupContact>();
    for (Group group : groupsToRemove) {
      for (GroupContact gc : this.groupsContacts) {
        if (gc.getGroupId() == group.getId()) {
          entriesToRemove.add(gc);
        }
      }
    }

    this.groupsContacts.removeAll(entriesToRemove);
  }

  /**
   * Load data pro the persistance.
   */
  private void load() {
    Persistance per = new Persistance();
    this.groupsContacts = per.loadData(this.getFilename());
  }

  /**
   * Save data to the persistance.
   */
  void save() {
    Persistance per = new Persistance();
    per.saveData(this.getFilename(), this.groupsContacts);
  }

  /**
   * Clear all data.
   */
  void clear() {
    this.groupsContacts.clear();
  }

  /**
   * Filter groups-contacts according to group id.
   *
   * @param id id
   * @return List of contacts ids
   */
  List<Integer> filterByGroupId(int id) {
    return this.filter(true, id);
  }

  /**
   * Filter groups-contacts according to contact id.
   *
   * @param id id
   * @return list of groups ids
   */
  List<Integer> filterByContactId(int id) {
    return this.filter(false, id);
  }

  /**
   * Get contacts ids assign to the groups.
   *
   * @param groups list of groups.
   * @return list of contacts ids
   */
  List<Integer> getContactsIdAssignToGroups(List<Group> groups) {

    List<Integer> contactsId = new ArrayList<Integer>();
    List<Integer> tempList = null;

    for (Group group : groups) {
      tempList = this.filterByGroupId(group.getId());

      for (int contactId : tempList) {

        // add id only in case, that is not already in the list
        if (!contactsId.contains(contactId)) {
          contactsId.add(contactId);
        }
      }
    }

    return contactsId;
  }

  /**
   * Filter groups-contacts table.
   *
   * @param filterGroups filter groups or contacts?
   * @param id id
   * @return list of ids
   */
  private List<Integer> filter(boolean filterGroups, int id) {

    List<Integer> filteredRecords = new ArrayList<Integer>();

    for (GroupContact groupContact : this.groupsContacts) {
      int groupId = groupContact.getGroupId();
      int contactId = groupContact.getContactId();

      if (filterGroups) {
        if (groupId == id) {
          filteredRecords.add(contactId);
        }
      } else {
        if (contactId == id) {
          filteredRecords.add(groupId);
        }
      }
    }

    return filteredRecords;
  }
}
