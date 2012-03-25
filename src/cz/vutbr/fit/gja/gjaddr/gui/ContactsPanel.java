package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Database;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Panel with contacts
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class ContactsPanel extends JPanel {
	static final long serialVersionUID = 0;
	private final Database db = new Database();

	/**
	 * Constructor
	 *
	 * @param listener Listener to handle actions outside goups panel
	 */
	public ContactsPanel(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Contacts");
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		String[] columnNames = {"First Name", "Last Name", "Email", "Phone", "Address"};
		Object[][] data = {
			{"Kathy", "Smith", "k.smith@gmail.com", "00447239437009", "Milton Keynes"},
			{"Sonia", "Newman", "s.newman@gmail.com", "00447847205234", "London"},
			{"Anthony", "Davenport", "a.davenport@gmail.com", "00447037482354", "Birmingham"},
			{"Isabella", "Distinto", "i.distinto@gmail.com", "00447019283775", "Milton Keynes"},
			{"Gioele", "Barabucci", "g.barabucci@gmail.com", "00447019283937", "Northampton"},
			{"Miriam", "Fernandez", "m.fernandez@gmail.com", "00447847563245", "Leighton Buzzard"},
			{"Hassan", "Saif", "h.saif@gmail.com", "00447039485736", "Bletchley"},
			{"Bogdan", "Kostov", "b.kostov@gmail.com", "00447958575646", "Milton Keynes"},
			{"Robbie", "Bayes", "r.a.b@gmail.com", "00447987654535", "Bletchley"},
			{"Harriet", "Cornish", "h.cornish@gmail.com", "00447887776665", "Milton Keynes"}
		};
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}
}
