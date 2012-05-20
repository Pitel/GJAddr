package cz.vutbr.fit.gja.gjaddr.gui;

import java.awt.event.ActionEvent;
import javax.swing.*;
import org.slf4j.LoggerFactory;

/**
 * About window.
 *
 * @author Bc. Jan Kaláb <xkalab00@stud.fit,vutbr.cz>
 */
class AboutWindow extends JDialog {

    static final long serialVersionUID = 0;

    public AboutWindow() {
        super();
        this.setModal(true);
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
        LoggerFactory.getLogger(this.getClass()).info("Opening about window.");
    }
    
    @Override
    protected JRootPane createRootPane() {
        JRootPane rp = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
        Action actionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        };
        InputMap inputMap = rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rp.getActionMap().put("ESCAPE", actionListener);
        return rp;
    }
}
