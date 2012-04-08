package cz.vutbr.fit.gja.gjaddr.gui;

import com.community.xanadu.components.table.BeanReaderJTable;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Contact;
import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

/**
 * Panel with contacts
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class ContactsPanel extends JPanel {
	static final long serialVersionUID = 0;
	
	private JPopupMenu contextMenu = new JPopupMenu();
	private MainWindow mainWindowHandle;

	
	private static final Database db = new Database();
	private static final BeanReaderJTable<Contact> table = new BeanReaderJTable<Contact>(new String[] {"FullName", "AllEmails", "AllPhones"}, new String[] {"Name", "Emails", "Phones"});
	private static final TableRowSorter<BeanReaderJTable.GenericTableModel> sorter = new TableRowSorter<BeanReaderJTable.GenericTableModel>(table.getModel());

	/**
	 * Constructor
	 */
	public ContactsPanel(MainWindow handle, ListSelectionListener listSelectionListener) {
		this.mainWindowHandle = handle;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Contacts");
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		fillTable(db.getAllContacts());
		table.getSelectionModel().addListSelectionListener(listSelectionListener);
		table.setRowSorter(sorter);
		table.setDefaultRenderer(Object.class, new TableRowColorRenderer());
		JScrollPane scrollPane = new JScrollPane(table);
		filter("");
		add(scrollPane);
		
		this.initContextMenu();
	}

	/**
	 * Fill table with data from list
	 */
	void fillTable(List<Contact> contacts) {
		final RowFilter filter = sorter.getRowFilter();	//Warnings!
		sorter.setRowFilter(null);
		table.clear();
		table.addRow(contacts);
		//System.out.println(model.getDataVector());
		sorter.setRowFilter(filter);
	}

	/**
	 * Filter contacts
	 *
	 * @param f String to filter
	 */
	void filter(String f) {
		//System.out.println("Filtering: " + f);
		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + f));
	}

	/**
	 * Get selected contact
	 */
	Contact getSelectedContact() {
		return table.getSelectedObject();
	}

	private void initContextMenu() {
		
		this.contextMenu.add(this.mainWindowHandle.actions.actionNewContact);
		this.contextMenu.add(this.mainWindowHandle.actions.actionDeleteContact);
		
		this.contextMenu.addSeparator();
		
		this.contextMenu.add(this.mainWindowHandle.actions.actionImport);
		this.contextMenu.add(this.mainWindowHandle.actions.actionExport);

		MouseListener popupListener = new PopupListener();
		table.addMouseListener(popupListener);	
	}
	
	class PopupListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			showPopup(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			showPopup(e);
		}

		private void showPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				contextMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}	
	}		
}
