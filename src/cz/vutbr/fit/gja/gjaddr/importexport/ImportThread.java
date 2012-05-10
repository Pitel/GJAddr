
package cz.vutbr.fit.gja.gjaddr.importexport;

import cz.vutbr.fit.gja.gjaddr.importexport.exception.ImportException;
import cz.vutbr.fit.gja.gjaddr.importexport.util.Progress;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xherrm01
 */
public abstract class ImportThread extends Thread {
	
	private String group = null;
	private boolean success = false;
	private boolean interrupted = false;
	protected Progress progress = new Progress();
	
	public boolean getInterrupted() {
		return this.interrupted;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public boolean getSuccess() {
		return this.success;
	}

	public Progress getProgress() {
		return progress;
	}

	@Override
    public void interrupt() {
        super.interrupt();
        this.interrupted = true;
        LoggerFactory.getLogger(this.getClass()).info("Interrupting thread: " + this.toString());
    }
	
	public abstract int runImport(String group) throws ImportException;
	
	@Override
	public void run() {
		try {
			runImport(this.group);
			this.success = true;
		} catch (ImportException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.toString());
			this.success = false;
		}
	}
}
