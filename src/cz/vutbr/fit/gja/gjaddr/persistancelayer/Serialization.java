
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for data binary serialization.
 * That class is used in two parts - import/export and database persistance.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Serialization {

	public static List load(String filename)	{
		
		List<Object> loadedData = new ArrayList<Object>(); 
		
		if ((new File(filename)).exists()) {
			try {
				FileInputStream flinpstr = new FileInputStream(filename);
				ObjectInputStream objinstr= new ObjectInputStream(flinpstr);

				try {	
					return (ArrayList) objinstr.readObject(); 
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
		return loadedData;
	}
	
	public static void save(String filename, List dataToSave)	{
		
		
		if (dataToSave == null || dataToSave.isEmpty()) {
			return;
		}
		
		try {
			FileOutputStream flotpt = new FileOutputStream(filename);
			ObjectOutputStream objstr= new ObjectOutputStream(flotpt);
			
			try {
				objstr.writeObject(dataToSave); 
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
}
