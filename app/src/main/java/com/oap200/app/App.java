package com.oap200.app;

import com.oap200.app.views.LoginPanel;
import javax.swing.SwingUtilities;

public class App {
	public static void main(String[] args) {
		// Launch the LoginPanel on the Event Dispatch Thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginPanel().setVisible(true);
			}
		});
	}
}
