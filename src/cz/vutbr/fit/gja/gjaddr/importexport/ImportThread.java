
package cz.vutbr.fit.gja.gjaddr.importexport;

import cz.vutbr.fit.gja.gjaddr.gui.ImportWindow;
import cz.vutbr.fit.gja.gjaddr.importexport.exception.ImportException;
import org.slf4j.LoggerFactory;

/**
 * Thread for contacts import from a service.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public abstract class ImportThread extends Thread {
	
    /**
     * Contacts group for import. Can be a new or existing group.
     */
	protected String group = null;
    
    /**
     * Success status.
     */
	private boolean success = false;
    
    /**
     * True if import thread was interrupted.
     */
	protected static boolean interrupted = false;
	
    /**
     * Was thread interrupted?
     * 
     * @return 
     */
	public static boolean isThreadInterrupted() {
		return ImportThread.interrupted;
	}

    /**
     * Get contacts group for import.
     * 
     * @return 
     */
	public String getGroup() {
		return group;
	}

    /**
     * Set contacts group for import.
     * 
     * @param group 
     */
	public void setGroup(String group) {
		this.group = group;
	}

    /**
     * Get import success status.
     * 
     * @return 
     */
	public boolean getSuccess() {
		return this.success;
	}

    /**
     * Interrupt thread.
     */
    public static void interruptThread() {
        ImportThread.interrupted = true;
        LoggerFactory.getLogger(ImportThread.class).info("Interrupting thread.");
    }
	
    /**
     * Run the import method.
     * 
     * @return
     * @throws ImportException 
     */
	public abstract int runImport() throws ImportException;
	
    /**
     * Run the thread.
     */
	@Override
	public void run() {
		try {
            LoggerFactory.getLogger(this.getClass()).info("Running import thread: {}", this.getClass().getCanonicalName());
            int imported = runImport();
			ImportWindow.performChanges(imported);
		} catch (ImportException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
            ImportWindow.showErrorMessage(ex.getMessage());
		}
	}
}
