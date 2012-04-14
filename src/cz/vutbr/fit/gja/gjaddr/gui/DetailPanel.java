package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.*;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

/**
 * Panel with contact detail
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class DetailPanel extends JPanel {
	static final long serialVersionUID = 0;
	static final JLabel name = new JLabel();
	static final JLabel address = new JLabel();
	static final JLabel emails = new JLabel();
	static final JLabel phones = new JLabel();
	static final JLabel webs = new JLabel();
	static final JLabel nameIcon = new JLabel();
	JScrollPane detailScrollPane;

	/**
	 * Constructor
	 */
	public DetailPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		final JLabel label = new JLabel("Detail");
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);

		// create panel with name and with birthday icon
		final JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
		nameIcon.setIcon(new ImageIcon(getClass().getResource("/res/present.png")));
		nameIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		nameIcon.setVisible(false);
		namePanel.add(nameIcon);
		namePanel.add(name);
		/*
		namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		namePanel.setAlignmentY(Component.TOP_ALIGNMENT);
		*/
		add(namePanel);

		final JPanel detailPanel = new JPanel(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 0;
		c.gridy = 0;
		detailPanel.add(new JLabel("<html><b>Address:</b></html>"), c);
		c.gridx = 1;
		detailPanel.add(address, c);
		c.gridx = 0;
		c.gridy++;
		detailPanel.add(new JLabel("<html><b>Email:</b></html>"), c);
		c.gridx = 1;
		detailPanel.add(emails, c);
		c.gridx = 0;
		c.gridy++;
		detailPanel.add(new JLabel("<html><b>Phone:</b></html>"), c);
		c.gridx = 1;
		detailPanel.add(phones, c);
		c.gridx = 0;
		c.gridy++;
		detailPanel.add(new JLabel("<html><b>Webs:</b></html>"), c);
		c.gridx = 1;
		detailPanel.add(webs, c);
		c.gridy++;
		c.weighty = 1;
		detailPanel.add(Box.createVerticalGlue(), c);
		detailScrollPane = new JScrollPane(detailPanel);
		detailScrollPane.setBorder(BorderFactory.createEmptyBorder());
		detailScrollPane.setVisible(false);
		add(detailScrollPane);
	}

	void show(Contact contact) {
		if (contact != null) {
			if (contact.hasBirthday()) {
				nameIcon.setVisible(true);
			} else {
				nameIcon.setVisible(false);
			}
			name.setText(String.format("<html><h1>%s %s</h1></html>", contact.getFirstName(), contact.getSurName()));
			//address.setText();
			emails.setText("<html>" + contact.getAllEmails().replaceAll(", ", "<br>") + "</html>");
			phones.setText("<html>" + contact.getAllPhones().replaceAll(", ", "<br>") + "</html>");
			detailScrollPane.setVisible(true);
		}
	}
}
