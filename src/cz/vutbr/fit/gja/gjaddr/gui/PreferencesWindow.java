
package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.importexport.FacebookOauth;
import cz.vutbr.fit.gja.gjaddr.importexport.GoogleOauth;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.AuthToken;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.ServicesEnum;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.slf4j.LoggerFactory;

/**
 * Window with application preferences.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class PreferencesWindow extends JFrame implements ActionListener {

	/**
	 * List of action commands for radio buttons selection.
	 */
	private enum ActionCommands {
		FACEBOOK_CONNECT("fb_conn"),
		FACEBOOK_DISCONNECT("fb_disconn"),
		GOOGLE_CONNECT("goog_conn"),
		GOOGLE_DISCONNECT("goog_disconn");

		private String command;

		private ActionCommands(String command) {
			this.command = command;
		}
	}

	/**
	 * Application database.
	 */
	private Database database = Database.getInstance();

	/**
	 * Service buttons.
	 */
	private JButton fbButton, googButton;

	/**
	 * Constructor. Initializes the window.
	 */
	public PreferencesWindow() {
		super("Preferences");
		LoggerFactory.getLogger(this.getClass()).debug("Opening preferences window.");

		// set window apearance
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e);
		}

		// set application icon
		ImageIcon icon = new ImageIcon(getClass().getResource("/res/icon.png"), "GJAddr");
		this.setIconImage(icon.getImage());

		// set page layout
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

		// main header
		this.add(this.createMainHeader());

		// services header
		this.add(this.createServicesHeader());

		// action buttons
		this.add(this.createFacebookActionButton());
		this.add(this.createGoogleActionButton());

		// make window visible
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Create main window header.
	 *
	 * @return
	 */
	private JPanel createMainHeader() {
		JPanel mainHeaderPanel = new JPanel(new GridLayout());
		JLabel mainHeader = new JLabel("<html><h1>Preferences</h1></html>", JLabel.LEFT);
		mainHeaderPanel.add(mainHeader);
		mainHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return mainHeaderPanel;
	}

	/**
	 * Create services header.
	 *
	 * @return
	 */
	private JPanel createServicesHeader() {
		JPanel firstHeaderPanel = new JPanel(new GridLayout());
		JLabel firstHeader = new JLabel("<html><h2>Connected services</h2></html>", JLabel.LEFT);
		firstHeaderPanel.add(firstHeader);
		firstHeaderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return firstHeaderPanel;
	}

	/**
	 * Create Facebook action button.
	 * 
	 * @return
	 */
	private JPanel createFacebookActionButton() {
		JPanel facebookPanel = new JPanel(new GridLayout());
		// check if facebook token is valid
		boolean valid = new FacebookOauth().isTokenValid();
		// offer connection to facebook
		if (!valid) {
			this.fbButton = new JButton("Connect to Facebook");
			this.fbButton.setIcon(new ImageIcon(this.getClass().getResource("/res/facebook.png")));
			this.fbButton.setIconTextGap(10);
			this.fbButton.addActionListener(this);
			this.fbButton.setSelected(false);
			this.fbButton.setActionCommand(ActionCommands.FACEBOOK_CONNECT.toString());
			this.fbButton.setHorizontalAlignment(SwingConstants.LEFT);
			facebookPanel.add(this.fbButton);
		}
		// if user is connected, he can invalidate the token
		else {
			this.fbButton = new JButton("Disconnect from Facebook");
			this.fbButton.setIcon(new ImageIcon(this.getClass().getResource("/res/facebook.png")));
			this.fbButton.setIconTextGap(10);
			this.fbButton.addActionListener(this);
			this.fbButton.setSelected(false);
			this.fbButton.setActionCommand(ActionCommands.FACEBOOK_DISCONNECT.toString());
			this.fbButton.setHorizontalAlignment(SwingConstants.LEFT);
			facebookPanel.add(this.fbButton);
		}
		facebookPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return facebookPanel;
	}

	/**
	 * Create Google action button.
	 *
	 * @return
	 */
	private JPanel createGoogleActionButton() {
		JPanel googlePanel = new JPanel(new GridLayout());
		// TODO -- check if the token is valid!!
		AuthToken token = this.database.getToken(ServicesEnum.GOOGLE);
		// offer connection to facebook
		if (token == null) {
			this.googButton = new JButton("Connect to Google");
			this.googButton.setIcon(new ImageIcon(this.getClass().getResource("/res/google.png")));
			this.googButton.setIconTextGap(10);
			this.googButton.addActionListener(this);
			this.googButton.setSelected(false);
			this.googButton.setActionCommand(ActionCommands.GOOGLE_CONNECT.toString());
			this.googButton.setHorizontalAlignment(SwingConstants.LEFT);
			googlePanel.add(this.googButton);
		}
		// if user is connected, he can invalidate the token
		else {
			this.googButton = new JButton("Disconnect from Google");
			this.googButton.setIcon(new ImageIcon(this.getClass().getResource("/res/google.png")));
			this.googButton.setIconTextGap(10);
			this.googButton.addActionListener(this);
			this.googButton.setSelected(false);
			this.googButton.setActionCommand(ActionCommands.GOOGLE_DISCONNECT.toString());
			this.googButton.setHorizontalAlignment(SwingConstants.LEFT);
			googlePanel.add(this.googButton);
		}
		googlePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		return googlePanel;
	}

	/**
	 * Disconnect user from Facebook.
	 * 
	 * @throws HeadlessException
	 */
	private void facebookDisconnect() throws HeadlessException {
		int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Disconnect from Facebook",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			this.database.removeToken(ServicesEnum.FACEBOOK);
			this.fbButton.setText("Connect to Facebook");
			this.fbButton.setActionCommand(ActionCommands.FACEBOOK_CONNECT.toString());
		}
	}

	/**
	 * Disconnect user from Google.
	 *
	 * @throws HeadlessException
	 */
	private void googleDisconnect() throws HeadlessException {
		int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Disconnect from Google",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			this.database.removeToken(ServicesEnum.GOOGLE);
			this.googButton.setText("Connect to Google");
			this.googButton.setActionCommand(ActionCommands.GOOGLE_CONNECT.toString());
		}
	}

	/**
	 * Connect user to Facebook and save generated authentication token.
	 * 
	 * @throws HeadlessException
	 */
	private void facebookConnect() throws HeadlessException {
		JOptionPane.showMessageDialog(this, "After you confirm this dialog a window with Facebook\n"
				+ "authentication will open. Please copy generated code\n"
				+ "(from the browser navigation bar) to GJAddr.", "Instructions",
				JOptionPane.INFORMATION_MESSAGE);
		new FacebookOauth().authenticate();
		String s = (String) JOptionPane.showInputDialog(this, "Authentication token:",
						"Authentication token", JOptionPane.PLAIN_MESSAGE, null, null, null);
		AuthToken token = new AuthToken(ServicesEnum.FACEBOOK, s);
		this.database.addToken(token);
		this.dispose();
	}

	/**
	 * Connect user to Google and save generated authentication token.
	 *
	 * @throws HeadlessException
	 */
	private void googleConnect() throws HeadlessException {
		JOptionPane.showMessageDialog(this, "After you confirm this dialog a window with Google\n"
				+ "authentication will open. Please copy generated code to GJAddr.", "Instructions",
				JOptionPane.INFORMATION_MESSAGE);
		GoogleOauth goa = new GoogleOauth();
		goa.authenticate();
		String s = (String) JOptionPane.showInputDialog(this, "Authentication token:",
						"Authentication token", JOptionPane.PLAIN_MESSAGE, null, null, null);
		AuthToken token = goa.authenticate(s);
		this.database.addToken(token);
		this.dispose();
	}

	/**
	 * Assign actions to components.
	 * 
	 * @param ae
	 */
	public void actionPerformed(ActionEvent ae) {
		// facebook button pressed
		if (ae.getSource() == this.fbButton) {
			String action = this.fbButton.getActionCommand();
			if (action.equals(ActionCommands.FACEBOOK_CONNECT.toString())) {
				this.facebookConnect();
			} else {
				this.facebookDisconnect();
			}
		}
		// google button pressed
		else if (ae.getSource() == this.googButton) {
			String action = this.googButton.getActionCommand();
			if (action.equals(ActionCommands.GOOGLE_CONNECT.toString())) {
				this.googleConnect();
			} else {
				this.googleDisconnect();
			}
		}
	}
}
