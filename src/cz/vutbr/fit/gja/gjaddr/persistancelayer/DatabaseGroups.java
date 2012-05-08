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
	
	private int idCounter = 0;
	
	private final String FILENAME = new File(Settings.getDataDir(), "groups").toString();
	private ArrayList<Group> groups = null;

	public DatabaseGroups() {		
		this.load();
		this.setLastIdNumber();
	}	
	
	private void load()	{
    Persistance per = new Persistance();    
		this.groups = (ArrayList<Group>) per.loadData(FILENAME);
	}
	
	void save()	{		
    Persistance per = new Persistance();    
		per.saveData(FILENAME, this.groups);
	}	
	
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
	
	private boolean checkNameIfExists(String name) {
		for (Group group: this.groups) {
			if (group.getName().equals(name)) {
				return true;
			}
		}
		
		return false;
	}
	
	void clear() {
		this.groups.clear();
		this.idCounter = 0;		
	}	
	
	public List<Group> getAllGroups() {
		return new ArrayList<Group>(this.groups);
	}

	/**
	 * Get group by it's name.
	 * 
	 * @param name
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
	
	boolean addNew(String name) {
		
		if (this.checkNameIfExists(name)) {
			return false;
		}
		
		Group newGroup = new Group(++this.idCounter, name);
		this.groups.add(newGroup);
		
		return true;
	}
	
	void updateGroup(Group group) {
		Group updatedGroup = this.filterItem(group.getId());
		int index = this.groups.indexOf(updatedGroup);
		if (index != -1) {
			this.groups.set(index, group);
		}
	}
	
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
	
	void removeGroup(List<Group> groupsToRemove) {
		this.groups.removeAll(groupsToRemove);
	}
	
	private Group filterItem(int id) {
		for (Group group : this.groups) {			
			if (group.getId() == id) 
				return group;
			}

		return null;
	}	
	
	List<Group> filter(List<Integer> reguiredIdList) {
		
		List<Group> filteredGroups = new ArrayList<Group>();
		
		for (Group group : this.groups) {
			
			if (reguiredIdList.contains(group.getId()))
				filteredGroups.add(group);
			}

		return filteredGroups;
	}	
}
