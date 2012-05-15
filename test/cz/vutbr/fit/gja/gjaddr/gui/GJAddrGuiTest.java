package cz.vutbr.fit.gja.gjaddr.gui;

import org.uispec4j.*;
import org.uispec4j.interception.*;

public class GJAddrGuiTest extends UISpecTestCase {
	protected void setUp() throws Exception {
		setAdapter(new MainClassAdapter(cz.vutbr.fit.gja.gjaddr.Main.class, new String[0]));
	}

	public void testCreatingAContact() throws Exception {
		Window window = getMainWindow();
	}
}
