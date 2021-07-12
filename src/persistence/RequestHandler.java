package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import mapper.CompanyMapper;
import mapper.ComputerMapper;
import model.Computer;

public class RequestHandler {

	public static Computer getComputer(int id)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE computer.id=?");
			
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			result.next();
			
			return ComputerMapper.mapToComputer(result);
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
			result.next();
			
			return ComputerMapper.mapToComputer(result);
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
				computers.put(result.getInt("computer.id"),ComputerMapper.mapToComputer(result));
			}
			return computers;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getCompany(int id)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `company` WHERE id=?");
			
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			result.next();
			
			return CompanyMapper.mapToCompany(result);
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
}
