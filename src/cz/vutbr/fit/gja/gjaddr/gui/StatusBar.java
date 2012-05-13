
package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.importexport.util.Progress;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Status bar for displaying progress.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class StatusBar extends JPanel {
    
    private static JLabel label = new JLabel();
    
    private static JProgressBar progressBar = new JProgressBar(0, 100);
    
    /**
     * Create instance of status bar.
     */
    public StatusBar() {
        super();
        this.add(StatusBar.progressBar, BorderLayout.WEST);
        this.add(StatusBar.label, BorderLayout.WEST);
        StatusBar.setMessage("Ready");
        StatusBar.progressBar.setVisible(false);
    }
    
    public static void setMessage(String message) {
        StatusBar.label.setText(message);   
    }
    
    public static void setProgressBounds(int min, int max) {
        StatusBar.progressBar.setVisible(true);
        StatusBar.progressBar.setStringPainted(true);
        StatusBar.progressBar.setMinimum(min);
        StatusBar.progressBar.setMaximum(max);
    }
    
    public static void setProgressValue(int value) {
        StatusBar.progressBar.setValue(value);
    }
    
    public static void setProgressFinished(int total) {
        StatusBar.progressBar.setVisible(false);
    }
}