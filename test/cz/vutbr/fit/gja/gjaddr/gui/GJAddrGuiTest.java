package cz.vutbr.fit.gja.gjaddr.gui;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * GUI tests using FEST
 *
 * @author Bc. Jan Kaláb <kalab00@stud.fit.vutbr.cz>
 */
public class GJAddrGuiTest {
	private FrameFixture window;

	@BeforeClass public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before public void setUp() {
		MainWindow frame = GuiActionRunner.execute(new GuiQuery<MainWindow>() {
				protected MainWindow executeInEDT() {
					return new MainWindow();
				}
		});
		window = new FrameFixture(frame);
		window.show(); // shows the frame to test
	}

	@Test public void shouldCopyTextInLabelWhenClickingButton() {
		window.textBox("textToCopy").enterText("Some random text");
		window.button("copyButton").click();
		window.label("copiedText").requireText("Some random text");
	}

	@After public void tearDown() {
		window.cleanUp();
	}
}
