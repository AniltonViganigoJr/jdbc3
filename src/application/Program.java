package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = DB.getConnection();
			preparedStatement = connection.prepareStatement(
					  "INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES"
					+ "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
					);
			
			preparedStatement.setString(1, "Seller Name");
			preparedStatement.setString(2, "Seller Email");
			preparedStatement.setDate(3, new java.sql.Date(sdf.parse("Seller BirthDate").getTime()));
			preparedStatement.setDouble(4, 0.0);
			preparedStatement.setInt(5, 3);
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				while(resultSet.next()) {
					System.out.println("Done! New ID was Generated with Successfull: " 
							+ resultSet.getInt(1));
				}
			}else {
				System.out.println("No rows affected!");
			}
			
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
		finally {
			DB.closeStatement(preparedStatement);
			DB.closeConnection();
		}
	}
}