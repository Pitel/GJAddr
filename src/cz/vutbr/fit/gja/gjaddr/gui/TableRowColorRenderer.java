package cz.vutbr.fit.gja.gjaddr.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer for Contacts table rows.
 *
 * @author Bc. Radek Gajdušek <xgajdu07@stud.fit,vutbr.cz>
 */
class TableRowColorRenderer extends DefaultTableCellRenderer {

  static final long serialVersionUID = 0;
  // use color from Tango schema:
  // http://tango.freedesktop.org/Tango_Icon_Theme_Guidelines		
  /**
   * Odd line color.
   */
  private final Color colorOddLine = Color.WHITE;
  /**
   * Even line color.
   */
  private final Color colorEvenLine = new Color(211, 215, 207);
  /**
   * Current color.
   */
  private Color currentColor = this.colorOddLine;

  /**
   * Get cell renderer for table.
   *
   * @param table required table
   * @param value
   * @param isSelected is cell selected
   * @param hasFocus has a focus
   * @param row row number
   * @param column column number
   * @return rendered for current cell
   */
  @Override
  public Component getTableCellRendererComponent(JTable table,
          Object value,
          boolean isSelected,
          boolean hasFocus,
          int row,
          int column) {

    if (row % 2 == 0) {
      this.currentColor = this.colorOddLine;
    } else {
      this.currentColor = this.colorEvenLine;
    }
    if (isSelected) {
      super.setForeground(table.getSelectionForeground());
      super.setBackground(table.getSelectionBackground());
    } else {
      super.setForeground(Color.BLACK);
      super.setBackground(this.currentColor);
    }

    setFont(table.getFont());
    setValue(value);

    return this;
  }
}
