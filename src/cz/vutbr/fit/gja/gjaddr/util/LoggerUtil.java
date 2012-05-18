package cz.vutbr.fit.gja.gjaddr.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Logger utilities.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class LoggerUtil {

  /**
   * Method for getting exception stack trace as string.
   *
   * @param aThrowable
   * @return stack trace as string
   */
  public static String getStackTrace(Throwable aThrowable) {
    final Writer result = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(result);
    aThrowable.printStackTrace(printWriter);
    return result.toString();
  }
}
