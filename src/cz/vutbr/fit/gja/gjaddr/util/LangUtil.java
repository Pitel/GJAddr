package cz.vutbr.fit.gja.gjaddr.util;

import java.text.Normalizer;

/**
 * Language utilities.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class LangUtil {

  /**
   * Remove diacritics.
   *
   * @param s
   * @return
   */
  public static String removeDiacritics(String s) {
    return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
  }

  /**
   * Test the class.
   *
   * @param args
   */
  public static void main(String[] args) {
    System.out.println(LangUtil.removeDiacritics("Příliš žluťoučký kůň"));
  }
}
