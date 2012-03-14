package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with groups collection.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class DatabaseGroups {
	
	private int idCounter = 1;
	
	private final String FILENAME = "groups.gja";
	private List<Group> groups = null;

	public DatabaseGroups() {		
		this.load();
		this.setLastIdNumber();
	}	
	
	private void load()	{
		
		this.groups = null;
		
		if ((new File(FILENAME)).exists()) {
			try {
				FileInputStream flinpstr = new FileInputStream(FILENAME);
				ObjectInputStream objinstr= new ObjectInputStream(flinpstr);

				try {	
					this.groups = (List<Group>) objinstr.readObject(); 
				} 
				finally {
					try {
						objinstr.close();
					} 
					finally {
						flinpstr.close();
					}
				}
			} 
			catch(IOException ioe) {
				ioe.printStackTrace();
			} 
			catch(ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
		}		
		
		// create empty DB
		if (this.groups == null) {
			this.groups = new ArrayList<Group>();
		}			
	}
	
	private void setLastIdNumber() {
		
		int counter = 1;
		
		for (Group group: this.groups) {
			int id = group.getId();
			if (id > counter) {
				counter = id;
			}
		}
		
		this.idCounter = counter;
	}	
	
	void save()	{
		
		if (this.groups == null || this.groups.isEmpty()) {
			return;
		}
		
		try {
			FileOutputStream flotpt = new FileOutputStream(FILENAME);
			ObjectOutputStream objstr= new ObjectOutputStream(flotpt);
			
			try {
				objstr.writeObject(this.groups); 
				objstr.flush();
			} 
			finally {				
				try {
					objstr.close();
				} 
				finally {
					flotpt.close();
				}
			}
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
		}		
	}
	
	public List<Group> getAllGroups() {
		return this.groups;
	}
	
	void addNew(String name) {
		Group newGroup = new Group(++this.idCounter, name);
		this.groups.add(newGroup);	
	}
	
	void updateGroup(Group group) {
		Group updatedGroup = this.filterItem(group.getId());
		int index = this.groups.indexOf(updatedGroup);
		if (index != -1) {
			this.groups.set(index, group);
		}
	}
	
	void removeGroup(List<Integer> groupsId) {
		List<Group> groupsToRemove = this.filter(groupsId);		
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
