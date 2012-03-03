
package cz.vutbr.fit.gja.gjaddr;

import cz.vutbr.fit.gja.gjaddr.gui.MainWindow;
import javax.swing.SwingUtilities;
import org.slf4j.LoggerFactory;

/**
 * Project main class.
 * 
 * @author xgajdu07, xherrm01, xkalab00
 */
public class Main {
	
	/**
	 * Open the main window of the application.
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LoggerFactory.getLogger(Main.class).info("Opening main application window.");
				MainWindow mw = new MainWindow();
				mw.setVisible(true);
			}
		});
	} 
}
