package cz.vutbr.fit.gja.gjaddr.gui;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Settings;
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
        setModal(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e);
        }
        final ImageIcon icon = new ImageIcon(getClass().getResource("/res/icon.png"), "GJAddr");
        setIconImage(icon.getImage());
        setTitle("About GJAddr");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        String programVersion = Settings.instance().PROGRAM_VERSION;
        final JLabel header = new JLabel("<html><h1>GJAddr " + programVersion + "</h1></html>", JLabel.CENTER);
        header.setAlignmentX(CENTER_ALIGNMENT);
        add(header);
        final JLabel logo = new JLabel(icon, JLabel.CENTER);
        logo.setAlignmentX(CENTER_ALIGNMENT);
        add(logo);
        final JLabel details = new JLabel("<html><center><p>Projekt do předmětu Grafická uživatelská rozhraní v Javě<br>FIT VUTBR 2012</p><h2>Authors</h2>Bc. Radek Gajdušek<br>Bc. Drahomíra Herrmannová<br>Bc. Jan Kaláb</center></html>", JLabel.CENTER);
        details.setAlignmentX(CENTER_ALIGNMENT);
        details.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(details);
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
            static final long serialVersionUID = 0;
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
