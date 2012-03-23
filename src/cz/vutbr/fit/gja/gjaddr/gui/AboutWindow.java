package cz.vutbr.fit.gja.gjaddr.gui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

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
