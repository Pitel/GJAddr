package cz.vutbr.fit.gja.gjaddr.gui.util;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Class for setting up escape key handler.
 *
 * @author Bc. Radek Gajdusek <xgajdu07@stud.fit,vutbr.cz>
 */
public class EscapeKeyHandler {

  /**
   * Set for obtained JFrame instance the ESCAPE action subroutine.
   *
   * @param frame
   */
  public static void setEscapeAction(final JFrame frame) {
    KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);

    Action escapeAction = new AbstractAction() {

      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    };

    frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
    frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
  }
}
