package com.mavendemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class JDBCMySQLDBTutorial {
 
	static Connection connection = null;
	static PreparedStatement preparedStatement = null;
 
	public static void main(String[] argv) {
 
		try {
			log("-------- How to make JDBC connection to MySQL DB locally on macOS ------------");
			makeJDBCConnection();
 
			//log("\n---------- Adding company to DB ----------");
			//addDataToDB("7", "NYC, US");
			//addDataToDB("8", "Mountain View");
 
			log("\n---------- Let's get Data from DB ----------");
			getDataFromDB();
 
			preparedStatement.close();
			connection.close();
 
		} catch (SQLException e) {
 
			e.printStackTrace();
		}
	}
 
	private static void makeJDBCConnection() {
 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			log("Congrats - Seems your MySQL JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}
 
		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "student", "student1");
			if (connection != null) {
				log("Connection Successful! Enjoy. Now it's time to push data");
			} else {
				log("Failed to make connection!");
			}
		} catch (SQLException e) {
			log("MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}
 
	}
 
	private static void addDataToDB(String deptNo, String deptName) {
		try {
			String insertQueryStatement = "INSERT  INTO  departments VALUES (?,?)";
			preparedStatement = connection.prepareStatement(insertQueryStatement);
			preparedStatement.setString(1, deptNo);
			preparedStatement.setString(2, deptName);
			preparedStatement.executeUpdate();
			log(deptName + " added successfully");
		} catch (
		SQLException e) {
			e.printStackTrace();
		}
	}
 
	private static void getDataFromDB() {
		try {
			String getQueryStatement = "SELECT * FROM departments";
			preparedStatement = connection.prepareStatement(getQueryStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String name = rs.getString("dept_no");
				String address = rs.getString("dept_name");
				System.out.format("%s, %s \n", name, address);
			}
		} catch (
				SQLException e) {
			e.printStackTrace();
		}
 
	}
 
	private static void log(String string) {
		System.out.println(string);
	}
}