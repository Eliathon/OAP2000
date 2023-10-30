// Created by Sindre

package com.oap200.app;

import com.oap200.app.utils.DbConnect;
import com.oap200.app.views.MainFrame;

import javax.swing.SwingUtilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//test2, Kristian
public class App {
	public static void main(String[] args) {
		List<String> emails = new ArrayList<>();

		try {
			DbConnect db = new DbConnect();
			Connection myConnection = db.getConnection();
			Statement myStmt = myConnection.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from employees");

			// Fetch emails
			while (myRs.next()) {
				emails.add(myRs.getString("email"));
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		// Display the emails in the Swing frame
		SwingUtilities.invokeLater(() -> {
			MainFrame frame = new MainFrame();
			frame.displayEmails(emails);
			frame.setVisible(true);
		});
	}
} 