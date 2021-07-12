package mapper;

import java.sql.*;
import java.util.HashMap;

import model.Computer;
import persistence.DBConnection;

/**
 * Class Mapper
 * Handle the queries and map the information between the database and the models
 */
public class Mapper {

	
	public static Computer getComputer(int id)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE computer.id=?");
			
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			result.first();
			
			return mapToComputer(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Computer getComputer(String name)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE computer.name=?");
			
			query.setString(1, name);
			ResultSet result = query.executeQuery();
			result.first();
			
			return mapToComputer(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static HashMap<Integer,Computer> getAllComputers()
	{
		HashMap<Integer,Computer> computers = new HashMap<Integer,Computer>();
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `computer` LEFT JOIN `company` ON company_id = company.id");
			ResultSet result = query.executeQuery();
			while (result.next())
			{
				computers.put(result.getInt("computer.id"),mapToComputer(result));
			}
			return computers;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static HashMap<Integer,String> getAllCompanies()
	{
		HashMap<Integer,String> companies = new HashMap<Integer,String>();
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `company`");
			ResultSet result = query.executeQuery();
			while (result.next())
			{
				companies.put(result.getInt("id"),result.getString("name"));
			}
			return companies;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static Computer mapToComputer(ResultSet result) throws SQLException
	{
		String name = result.getString("name");
		Date introduced = result.getDate("introduced");
		Date discontinued = result.getDate("discontinued");
		String company = result.getString("company.name");
		
		return new Computer(name,introduced.toLocalDate(),discontinued.toLocalDate(),company);
	}
}
