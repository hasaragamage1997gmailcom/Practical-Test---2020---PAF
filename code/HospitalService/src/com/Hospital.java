package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Hospital {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospital", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readHospital() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Hospital Code</th> <th>Hospital Name</th><th>Number of Rooms in Hospital</th>"
					+ "<th>Hospital Description</th> <th>Update</th><th>Remove</th></tr>";
			String query = "select * from hospital";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String HospitalID = Integer.toString(rs.getInt("HospitalID"));
				String HospitalCode = rs.getString("HospitalCode");
				String HospitalName = rs.getString("HospitalName");
				String HospitalRoom = Double.toString(rs.getDouble("HospitalRoom"));
				String HospitalDesc = rs.getString("HospitalDesc"); 
				// Add into the html table
				output += "<tr><td><input id='hidHospitalIDUpdate' name='hidHospitalIDUpdate' type='hidden' value='" + HospitalID
						+ "'>" + HospitalCode + "</td>";
				output += "<td>" + HospitalName + "</td>";
				output += "<td>" + HospitalRoom + "</td>";
				output += "<td>" + HospitalDesc + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-hospitalid='"+ HospitalID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Hospital.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertHospital(String code, String name, String room, String desc) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into hospital (`HospitalID`,`HospitalCode`,`HospitalName`,`HospitalRoom`,`HospitalDesc`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(room));
			preparedStmt.setString(5, desc);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newHospital = readHospital();
			output = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the Hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateHospital(String ID, String code, String name, String room, String desc) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE hospital SET HospitalCode=?,HospitalName=?,HospitalRoom=?,HospitalDesc=? WHERE HospitalID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(room));
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newHospital = readHospital();
			output = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the Hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteHospital(String HospitalID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from hospital where HospitalID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(HospitalID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newHospital = readHospital();
			output = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the Hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
