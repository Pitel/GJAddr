package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with groups collection.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class DatabaseGroups {
	
  /**
   * Nex group id counter.
   */
	private int idCounter = 0;
	
  /**
   * Groups database filename.
   */
	private final String FILENAME = new File(Settings.instance().getDataDir(), "groups").toString();
	
  /**
   * Groups database collection.
   */
  private ArrayList<Group> groups = null;

  /**
   * Constructor
   */
	public DatabaseGroups() {		
		this.load();
		this.setLastIdNumber();
	}	
	
  /**
   * Load groups from persistance.
   */
	private void load()	{
    Persistance per = new Persistance();    
		this.groups = (ArrayList<Group>) per.loadData(FILENAME);
	}
	
  /**
   * Save group to the persistance
   */
	void save()	{		
    Persistance per = new Persistance();    
		per.saveData(FILENAME, this.groups);
	}	
	
  /** 
   * Get last id number from groups collection.
   */
	private void setLastIdNumber() {
		
		int counter = 0;
		
		for (Group group: this.groups) {
			int id = group.getId();
			if (id > counter) {
				counter = id;
			}
		}
		
		this.idCounter = counter;
	}	
	
  /**
   * Check if group with name exists.
   * @param name group name
   * @return true if exists, otherwise false
   */
	private boolean checkNameIfExists(String name) {
		for (Group group: this.groups) {
			if (group.getName().equals(name)) {
				return true;
			}
		}
		
		return false;
	}
	
  /**
   * Clear all groups.
   */
	void clear() {
		this.groups.clear();
		this.idCounter = 0;		
	}	
	
  /**
   * Get all groups.
   * @return list of groups.
   */
	public List<Group> getAllGroups() {
		return new ArrayList<Group>(this.groups);
	}

	/**
	 * Get group by it's name.
	 * 
	 * @param name group name
	 * @return
	 */
	public Group getGroupByName(String name) {
		for (Group g : this.groups) {
			if (g.getName().equals(name)) {
				return g;
			}
		}
		return null;
	}
	
  /**
   * Add new group to the DB
   * @param name new group name
   * @return false if group with name exists, otherwise true
   */
	boolean addNew(String name) {
		
		if (this.checkNameIfExists(name)) {
			return false;
		}
		
		Group newGroup = new Group(++this.idCounter, name);
		this.groups.add(newGroup);
		
		return true;
	}
	
  /**
   * Update group.
   * @param group group
   */
	void updateGroup(Group group) {
		Group updatedGroup = this.filterItem(group.getId());
		int index = this.groups.indexOf(updatedGroup);
		if (index != -1) {
			this.groups.set(index, group);
		}
	}
	
  /**
   * Rename specific group.
   * @param group group
   * @param newName new group name
   * @return false if group with name exists, otherwise true
   */
	boolean renameGroup(Group group, String newName) {
		
		if (group.getName().equals(newName)) {
			return true;
		}
		
		if (this.checkNameIfExists(newName)) {
			return false;
		}		
		
		group.setName(newName);	
		this.updateGroup(group);
		
		return true;
	}
	
  /**
   * Remove groups from DB.
   * @param groupsToRemove list of group to remove
   */
	void removeGroup(List<Group> groupsToRemove) {
		this.groups.removeAll(groupsToRemove);
	}
	
  /**
   * Filter groups according to the id.
   * @param id required id
   * @return group or null if doesn't exist
   */
	private Group filterItem(int id) {
		for (Group group : this.groups) {			
			if (group.getId() == id) 
				return group;
			}

		return null;
	}	
	
  /**
   * Filter groups according to list of ids.
   * @param reguiredIdList list of required ids
   * @return list of required groups or empty list
   */
	List<Group> filter(List<Integer> reguiredIdList) {
		
		List<Group> filteredGroups = new ArrayList<Group>();
		
		for (Group group : this.groups) {
			
			if (reguiredIdList.contains(group.getId()))
				filteredGroups.add(group);
			}

		return filteredGroups;
	}	
}
