package cz.vutbr.fit.gja.gjaddr.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * Class for menu, toolbar and context menu actions.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit,vutbr.cz>
 */
public class UserActions {
	
	AddContactAction actionNewContact;	
	DeleteContactAction actionDeleteContact;		
	
	AddGroupAction actionNewGroup;
	DeleteGroupAction actionDeleteGroup;
	RenameGroupAction actionRenameGroup;	
	
	ImportAction actionImport;	
	ExportAction actionExport;
	PreferencesAction actionPreferences;
	
	HelpAction actionHelp;
	AboutAction actionAbout;
	
	public UserActions() {
		this.actionNewContact = new AddContactAction();
		this.actionDeleteContact = new DeleteContactAction();
		
		this.actionNewGroup = new AddGroupAction();
		this.actionDeleteGroup = new DeleteGroupAction();
		this.actionRenameGroup = new RenameGroupAction();
		
		this.actionImport = new ImportAction();
		this.actionExport = new ExportAction();
		this.actionPreferences = new PreferencesAction();
		
		this.actionHelp = new HelpAction();
		this.actionAbout = new AboutAction();
	}
	
	/**
	 * Action for importing contacts
	 */
	private class ImportAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "Import";
		private static final String icon = "/res/import.png";
		private final Integer mnemonic = KeyEvent.VK_I;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK);

		public ImportAction() {
			super(name);
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void setEnabled(boolean newValue) {
			super.setEnabled(newValue);
		}			

		@Override
		public void actionPerformed(ActionEvent e) {
			new ImportWindow();
		}
	}	
	
	/**
	 * Action for exporting contacts
	 */	
	private class ExportAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "Export";
		private static final String icon = "/res/export.png";
		private final Integer mnemonic = KeyEvent.VK_E;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK);

		public ExportAction() {
			super(name);
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new ExportWindow();
		}
	}	
	
	/**
	 * Action for preferences
	 */	
	private class PreferencesAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "Preferences";
		private static final String icon = "/res/preferences.png";
		private final Integer mnemonic = KeyEvent.VK_P;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK);

		public PreferencesAction() {
			super(name);
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new PreferencesWindow();
		}
	}
	
	/**
	 * Action for add new contact.
	 */	
	private class AddContactAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "Add new contact";
		private static final String icon = "/res/plus.png";
		private final Integer mnemonic = KeyEvent.VK_N;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK);

		public AddContactAction() {
			super(name);
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new ContactWindow(true); 	
		}
	}	
	
	/**
	 * Action for delete contact.
	 */		
	private class DeleteContactAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "Delete contact";
		private static final String icon = "/res/minus.png";
		private final Integer mnemonic = KeyEvent.VK_D;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK);

		public DeleteContactAction() {
			super(name);
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new ContactWindow(false); 			
		}
	}		
	
	/**
	 * Action for add new group.
	 */	
	class AddGroupAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "Add new group";
		private static final String icon = "/res/plus_g.png";
		private final Integer mnemonic = KeyEvent.VK_G;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK);

		public AddGroupAction() {
			super(name);
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new GroupWindow(GroupWindow.Action.NEW);			
		}
	}		
	
	/**
	 * Action for delete group.
	 */	
	class DeleteGroupAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "Delete group";
		private static final String icon = "/res/minus_g.png";
		private final Integer mnemonic = KeyEvent.VK_D;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK);

		public DeleteGroupAction() {
			super(name);
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new GroupWindow(GroupWindow.Action.REMOVE);
		}
	}	
	
	/**
	 * Action for delete group.
	 */	
	class RenameGroupAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "Rename group";
		private static final String icon = "/res/edit_g.png";
		private final Integer mnemonic = KeyEvent.VK_R;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK);

		public RenameGroupAction() {
			super(name);
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new GroupWindow(GroupWindow.Action.RENAME);
		}
	}		
	
	/**
	 * Action for help action.
	 */		
	private class HelpAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "Help";
		// private static final String icon = "/res/help.png";
		private final Integer mnemonic = KeyEvent.VK_H;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke("F1");

		public HelpAction() {
			super(name);
			//putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Desktop.getDesktop().browse(new URI("http://pitel.github.com/GJAddr"));
			} 
			catch (URISyntaxException ex) {
				System.err.println(ex);
			} 
			catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}			
	
	/**
	 * Action for showing about dialog.
	 */		
	private class AboutAction extends AbstractAction {
		static final long serialVersionUID = 0;
		private static final String name = "About";
		// private static final String icon = "/res/about.png";
		private final Integer mnemonic = KeyEvent.VK_A;
		private final KeyStroke accelerator = KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK);

		public AboutAction() {
			super(name);
			//putValue(SMALL_ICON, new ImageIcon(getClass().getResource(icon), name));
			putValue(MNEMONIC_KEY, mnemonic);
			putValue(ACCELERATOR_KEY, accelerator);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new AboutWindow();
		}
	}		
}
