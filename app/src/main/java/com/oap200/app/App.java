/*
 * Created by Sindre
 */

package com.oap200.app;

import com.oap200.app.Utils.DbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;

public class App {
	public static void main(String[] args) {
		try {
			DbConnect db = new DbConnect();
			Connection myConnection = db.getConnection();
			Statement myStmt = myConnection.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from employees");

			// Example: Print out the result set
			while (myRs.next()) {
				System.out.println(myRs.getString("email"));
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
}
