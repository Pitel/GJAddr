package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import java.awt.Component;
import javax.swing.*;

/**
 * Panel with contact detail
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class DetailPanel extends JPanel {
	static final long serialVersionUID = 0;
	static final JLabel name = new JLabel();
	static final JLabel nameIcon = new JLabel();
	static final JPanel namePanel = new JPanel();

	/**
	 * Constructor
	 */
	public DetailPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Detail");
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);

		// create panel with name and with birthday icon
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
		nameIcon.setIcon(new ImageIcon(getClass().getResource("/res/present.png")));
		nameIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		nameIcon.setVisible(false);
		namePanel.add(nameIcon);
		namePanel.add(name);
		namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		namePanel.setAlignmentY(Component.TOP_ALIGNMENT);
		add(namePanel);
		
		/*
		label.setBackground(java.awt.Color.blue);
		label.setOpaque(true);
		name.setBackground(java.awt.Color.red);
		name.setOpaque(true);
		*/
	}

	void show(Contact contact) {
		if (contact != null) {
			if (contact.hasBirthday()) {
				nameIcon.setVisible(true);
			} else {
				nameIcon.setVisible(false);
			}
			name.setText(String.format("<html><h1>%s %s</h1></html>",
					contact.getFirstName(), contact.getSurName()));
		}
	}
}
