package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import mapper.ComputerMapper;
import model.Computer;
import model.ComputerList;

public class ComputerRequestHandler {

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
	
	public static void createComputer(Computer computer)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("INSERT INTO `computer`"+ ComputerMapper.mapToCreate(computer));
			DBConnection.getLogger().debug("INSERT INTO `computer`"+ ComputerMapper.mapToCreate(computer));
			query.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateComputer(Computer computer)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("UPDATE `computer` SET "+ ComputerMapper.mapToUpdate(computer) + "WHERE id=" + computer.getId());
			DBConnection.getLogger().debug("UPDATE `computer` SET "+ ComputerMapper.mapToUpdate(computer) + "WHERE id=" + computer.getId());
			
			query.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteComputer(int id)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("DELETE FROM `computer` WHERE id="+id);
			DBConnection.getLogger().debug("DELETE FROM `computer` WHERE id="+ id);
			query.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteComputer(String name)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("DELETE FROM `computer` WHERE name='"+ name + "'");
			DBConnection.getLogger().debug("DELETE FROM `computer` WHERE name='"+ name + "'");
			
			query.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
}
