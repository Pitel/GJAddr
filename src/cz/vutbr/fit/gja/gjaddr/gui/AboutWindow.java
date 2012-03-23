package cz.vutbr.fit.gja.gjaddr.gui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

/*
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Group;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jdesktop.swingx.MultiSplitLayout.Divider;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import org.jdesktop.swingx.MultiSplitPane;
*/

/**
 * About window.
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class AboutWindow extends JFrame {
	static final long serialVersionUID = 0;

	public AboutWindow() {
		super("About");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e);
		}
		ImageIcon icon = new ImageIcon(getClass().getResource("/res/icon.png"), "GJAddr");
		setIconImage(icon.getImage());
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		JLabel header = new JLabel("<html><h1>GJAddr</h1></html>", JLabel.CENTER);
		header.setAlignmentX(CENTER_ALIGNMENT);
		add(header);
		JLabel logo = new JLabel(icon, JLabel.CENTER);
		logo.setAlignmentX(CENTER_ALIGNMENT);
		add(logo);
		JLabel authors = new JLabel("<html><h2>Authors</h2><ul><li>Bc. Radek Gajdušek</li><li>Bc. Drahomíra Herrmannová</li><li>Bc. Jan Kaláb</li></ul></html>", JLabel.CENTER);
		authors.setAlignmentX(CENTER_ALIGNMENT);
		add(authors);
		setResizable(false);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}
}
