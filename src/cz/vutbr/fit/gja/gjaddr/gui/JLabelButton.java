package cz.vutbr.fit.gja.gjaddr.gui;

import java.awt.Insets;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 * JButton which looks like JLabel
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
public class JLabelButton extends JButton {
	static final long serialVersionUID = 0;

	public JLabelButton() {
		super();
		LookLikeLabel();
	}

	public JLabelButton(Action a) {
		super(a);
		LookLikeLabel();
	}

	public JLabelButton(Icon icon) {
		super(icon);
		LookLikeLabel();
	}

	public JLabelButton(String text) {
		super(text);
		LookLikeLabel();
	}

	public JLabelButton(String text, Icon icon) {
		super(text, icon);
		LookLikeLabel();
	}

	private void LookLikeLabel() {
		setFocusPainted(false);
		setMargin(new Insets(0, 0, 0, 0));
		setContentAreaFilled(false);
		setBorderPainted(false);
		setOpaque(false);
		setBackground(null);
		setBorder(new EmptyBorder(0, 0, 0, 0));
	}
}
