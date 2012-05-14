
package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.importexport.FacebookImportThread;
import cz.vutbr.fit.gja.gjaddr.importexport.ImportThread;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Status bar for displaying progress.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class StatusBar extends JPanel implements ActionListener {
    
    /**
     * Status bar message.
     */
    private static JLabel label = new JLabel();
    
    /**
     * Progress bar.
     */
    private static JProgressBar progressBar = new JProgressBar(0, 100);
    
    /**
     * Button for canceling import.
     */
    private static JButton cancelButton = new JButton("Cancel");
    
    /**
     * Create instance of status bar.
     */
    public StatusBar() {
        super();
        this.setLayout(new BorderLayout());
        JPanel innerPanel = new JPanel();
        innerPanel.add(StatusBar.progressBar);
        innerPanel.add(StatusBar.cancelButton);
        innerPanel.add(StatusBar.label);
        this.add(innerPanel, BorderLayout.LINE_START);
        StatusBar.setMessage("Ready");
        StatusBar.progressBar.setVisible(false);
        StatusBar.cancelButton.setVisible(false);
        StatusBar.cancelButton.addActionListener(this);
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
        StatusBar.cancelButton.setVisible(true);
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
        StatusBar.cancelButton.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == StatusBar.cancelButton) {
            ImportThread.interruptThread();
        }
    }
}