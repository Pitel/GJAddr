
package cz.vutbr.fit.gja.gjaddr.importexport.util;

/**
 * Class capturing import progress for displaying a progress bar.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class Progress {
	
	private int all = 0;
    private int processed = 0;
    private int successful = 0;

	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
	}

	public int getProcessed() {
		return processed;
	}

	public void setProcessed(int processed) {
		this.processed = processed;
	}

	public int getSuccessful() {
		return successful;
	}

	public void setSuccessful(int successful) {
		this.successful = successful;
	}

    public void incProcessed() {
		this.processed++;
	}
	
	public void incSuccessful() {
		this.successful++;
	}
}
