package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import javax.swing.*;

/**
 * Panel with contact detail
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class DetailPanel extends JPanel {
	static final long serialVersionUID = 0;
	static final JLabel name = new JLabel();

	/**
	 * Constructor
	 */
	public DetailPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Detail");
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		name.setAlignmentX(CENTER_ALIGNMENT);
		add(name);
		/*
		label.setBackground(java.awt.Color.blue);
		label.setOpaque(true);
		name.setBackground(java.awt.Color.red);
		name.setOpaque(true);
		*/
	}

	void show(Contact contact) {
		if (contact != null) {
			name.setText(String.format("<html><h1>%s %s</h1></html>", contact.getFirstName(), contact.getSurName()));
		}
	}
}
