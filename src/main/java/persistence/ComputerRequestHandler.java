package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import mapper.ComputerDAOMapper;
import model.Computer;

/**
 * Class ComputersRequesthandler :
 * Manage the SQL requests for the computers
 * @author Lisa
 */
public class ComputerRequestHandler {

	/**
	 * @param id	Identified of a computer
	 * @return The computer found
	 */
	public static Computer getComputer(int id)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE computer.id=?");
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			result.next();
			
			return ComputerDAOMapper.mapToComputer(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param name	Name of a computer
	 * @return The computer found
	 */
	public static Computer getComputer(String name)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE computer.name=?");
			query.setString(1, name);
			ResultSet result = query.executeQuery();
			result.next();
			
			return ComputerDAOMapper.mapToComputer(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param computer	Computer to create
	 */
	public static void createComputer(Computer computer)
	{
		Connection connection = DBConnection.getConnection();
		try {
			//Use the mapper to get the representation of the computer to insert
			PreparedStatement query = connection.prepareStatement("INSERT INTO `computer`"+ ComputerDAOMapper.mapToCreate(computer));
			DBConnection.getLogger().debug("INSERT INTO `computer`"+ ComputerDAOMapper.mapToCreate(computer));
			query.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param computer	Computer to update
	 */
	public static void updateComputer(Computer computer)
	{
		Connection connection = DBConnection.getConnection();
		try {
			//Use the mapper to get the representation of the computer to update
			PreparedStatement query = connection.prepareStatement("UPDATE `computer` SET "+ ComputerDAOMapper.mapToUpdate(computer) + "WHERE id=?");
			DBConnection.getLogger().debug("UPDATE `computer` SET "+ ComputerDAOMapper.mapToUpdate(computer) + "WHERE id=?");
			query.setInt(1, computer.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param id	Identifier of the computer to delete
	 */
	public static void deleteComputer(int id)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("DELETE FROM `computer` WHERE id=?");
			
			DBConnection.getLogger().debug("DELETE FROM `computer` WHERE id="+ id);
			query.setInt(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param name Name of the computer to delete
	 */
	public static void deleteComputer(String name)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("DELETE FROM `computer` WHERE name=?");
			DBConnection.getLogger().debug("DELETE FROM `computer` WHERE name=?");
			query.setString(1, name);
			query.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return The list of all the computers
	 */
	public static HashMap<Integer,Computer> getAllComputers()
	{
		HashMap<Integer,Computer> computers = new HashMap<Integer,Computer>();
		Connection connection = DBConnection.getConnection();
		try {
			//Send the request to get all the computers
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `computer` LEFT JOIN `company` ON company_id = company.id");
			ResultSet result = query.executeQuery();
			//Create the list of all the computers
			while (result.next())
			{
				computers.put(result.getInt("computer.id"),ComputerDAOMapper.mapToComputer(result));
			}
			return computers;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
