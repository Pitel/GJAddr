
package cz.vutbr.fit.gja.gjaddr.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author xherrm01
 */
public class MainWindow extends JFrame {
    
    public MainWindow() {
		final JLabel label = new JLabel("Hello World");
		this.getContentPane().add(label);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
    }
    
}
