package com.cargoseller.tests.tools;

import java.sql.*;
import java.util.Calendar;
import java.util.Map;

import com.cargoseller.tests.pageobjects.CargoSellerTest;

public class DatabaseUtil {

	public static void saveTestResults(String testID, String username, String password, String projectTitle,
			String projectDescription, String projectCategory, String projectSkill, double projectBudget,
			String projectCountry, double withdrawalAmount, String packageToReload) {
		try {
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/cstests";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "cstests");

			String query = " insert into testresults (testID, username, password, projectTitle, projectDescription, projectCategory, projectSkill, projectBudget, projectCountry, withdrawalAmount, packageToReload)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, testID);
			preparedStmt.setString(2, username);
			preparedStmt.setString(3, password);
			preparedStmt.setString(4, projectTitle);
			preparedStmt.setString(5, projectDescription);
			preparedStmt.setString(6, projectCategory);
			preparedStmt.setString(7, projectSkill);
			preparedStmt.setDouble(8, projectBudget);
			preparedStmt.setString(9, projectCountry);
			preparedStmt.setDouble(10, withdrawalAmount);
			preparedStmt.setString(11, packageToReload);
			
			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
}
