package com.cargoseller.tests.tools;

import java.sql.*;
import java.util.Calendar;
import java.util.Map;

import com.cargoseller.tests.pageobjects.CargoSellerTest;

public class DatabaseUtil {

	public static void saveTestResults(String testID, String username, String projectTitle,
			String mainCategory1, String mainCategory2, String subCategory, String length, String height, String width,
			String weight, String units, String pickUpLocation, String deliveryLocation, String projectDescription,
			double projectBudget, double withdrawalAmount, String packageToReload) {
		try {
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/cstests";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "cstests");

			String query = " insert into testresults (testID, username, projectTitle, mainCategory1, mainCategory2, subCategory, length, height, width, weight, units, pickUpLocation, deliveryLocation, projectDescription, projectBudget, withdrawalAmount, packageToReload)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, testID);
			preparedStmt.setString(2, username);
			preparedStmt.setString(3, projectTitle);
			preparedStmt.setString(4, mainCategory1);
			preparedStmt.setString(5, mainCategory2);
			preparedStmt.setString(6, subCategory);
			preparedStmt.setString(7, length);
			preparedStmt.setString(8, height);
			preparedStmt.setString(9, width);
			preparedStmt.setString(10, weight);
			preparedStmt.setString(11, units);
			preparedStmt.setString(12, pickUpLocation);
			preparedStmt.setString(13, deliveryLocation);
			preparedStmt.setString(14, projectDescription);
			preparedStmt.setDouble(15, projectBudget);
			preparedStmt.setDouble(16, withdrawalAmount);
			preparedStmt.setString(17, packageToReload);

			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
}
