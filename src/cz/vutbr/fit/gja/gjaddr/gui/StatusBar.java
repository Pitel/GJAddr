
package cz.vutbr.fit.gja.gjaddr.gui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Status bar for displaying progress.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class StatusBar extends JPanel {
    
    /**
     * Status bar message.
     */
    private static JLabel label = new JLabel();
    
    /**
     * Progress bar.
     */
    private static JProgressBar progressBar = new JProgressBar(0, 100);
    
    /**
     * Create instance of status bar.
     */
    public StatusBar() {
        super();
        this.setLayout(new BorderLayout());
        JPanel innerPanel = new JPanel();
        innerPanel.add(StatusBar.progressBar);
        innerPanel.add(StatusBar.label);
        this.add(innerPanel, BorderLayout.LINE_START);
        StatusBar.setMessage("Ready");
        StatusBar.progressBar.setVisible(false);
    }
    
    /**
     * Set status bar message.
     * 
     * @param message 
     */
    public static void setMessage(String message) {
        StatusBar.label.setText(message);   
    }
    
    /**
     * Create progress bar with given bounds.
     * 
     * @param min
     * @param max 
     */
    public static void setProgressBounds(int min, int max) {
        StatusBar.progressBar.setVisible(true);
        StatusBar.progressBar.setStringPainted(true);
        StatusBar.progressBar.setMinimum(min);
        StatusBar.progressBar.setMaximum(max);
    }
    
    /**
     * Update progress bar value.
     * 
     * @param value 
     */
    public static void setProgressValue(int value) {
        StatusBar.progressBar.setValue(value);
    }
    
    /**
     * Hide progress bar.
     */
    public static void setProgressFinished() {
        StatusBar.setMessage("Ready");
        StatusBar.progressBar.setVisible(false);
    }
}