package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;
import java.util.List;

/**
 * Interface for database layer.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public interface IDatabase {

    /**
     * Get all contacts from the database.
     *
     * @return list of all contacts
     */
    List<Contact> getAllContacts();

    /**
     * Add new contacts.
     *
     * @param newContacts contact that will be added to the DB
     * @return list of all contacts
     */
    List<Contact> addNewContacts(List<Contact> newContacts);

    /**
     * Update one contact in the DB.
     *
     * @param contact updated contact
     * @return list of all contacts
     */
    List<Contact> updateContact(Contact contact);

    /**
     * Remove contacts from the DB.
     *
     * @param contactsToRemove list of contacts to remove
     * @return list of all contacts.
     */
    List<Contact> removeContacts(List<Contact> contactsToRemove);

    /**
     * Get all contacts from required groups.
     *
     * @param requiredGroups
     * @return list of contacts from specific groups.
     */
    List<Contact> getAllContactsFromGroups(List<Group> requiredGroups);

    /**
     * Associate contacts with group.
     *
     * @param group group for association.
     * @param contactsToAdd list of contacts
     * @return list of all contacts from associated group.
     */
    List<Contact> addContactsToGroup(Group group, List<Contact> contactsToAdd);

    /**
     * Remove contacts from group.
     *
     * @param group group for remove contacts.
     * @param contactsToRemove list of contacts to remove.
     * @return list of contacts for group.
     */
    List<Contact> removeContactsFromGroup(Group group, List<Contact> contactsToRemove);

    /**
     * Get all groups for specific contact.
     *
     * @param contact specific contact
     * @return list of all group associated with contact.
     */
    List<Group> getAllGroupsForContact(Contact contact);

    /**
     * Get grrou according to the name.
     *
     * @param name name of group.
     * @return Group object with the required name.
     */
    Group getGroupByName(String name);

    /**
     * Get all groups in the DB.
     *
     * @return
     */
    List<Group> getAllGroups();

    /**
     * Add new group to the DB.
     *
     * @param name name of the group.
     * @return list of all groups.
     */
    List<Group> addNewGroup(String name);

    /**
     * Rename existing group in the DB. If group is not in the DB, renaming has no effect.
     *
     * @param group group to remove.
     * @param newName new name for group.
     * @return list of all groups.
     */
    List<Group> renameGroup(Group group, String newName);

    /**
     * Remove specific groups from the DB
     *
     * @param groupsToRemove groups to remove.
     * @return list of all groups.
     */
    List<Group> removeGroups(List<Group> groupsToRemove);

    /**
     * Get token specified by service.
     *
     * @param service
     * @return
     */
    AuthToken getToken(ServicesEnum service);

    /**
     * Get token specified by service.
     *
     * @param service
     * @return
     */
    AuthToken getToken(Integer service);

    /**
     * Save new token to database.
     *
     * @param token
     * @return
     */
    AuthToken addToken(AuthToken token);

    /**
     * Remove token from database.
     *
     * @param service
     */
    void removeToken(Integer service);

    /**
     * Remove token from database.
     *
     * @param service
     */
    void removeToken(ServicesEnum service);

    /**
     * Retrieve contacts with birthday within one month.
     *
     * @return
     */
    List<Contact> getContactsWithBirtday();

    /**
     * Retrieve contacts with birthday within one month.
     *
     * @return
     */
    List<Contact> getContactsWithNameDay();

    /**
     * Retrieve contacts with birthday within one month.
     *
     * @return
     */
    List<Contact> getContactsWithCelebration();

    /**
     * Retrieve contacts with any event within some time.
     *
     * @return
     */
    List<Contact> getContactsWithEvent();
}
