package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mapper.ComputerDAOMapper;
import model.Computer;
import model.exceptions.RollbackHappened;
import service.Validator;

/**
 * Class ComputersRequesthandler :
 * Manage the SQL requests for the computers
 * @author Lisa
 */
public class ComputerRequestHandler {

	private static final String GET_WITH_NAME = "SELECT computer.id, computer.name,`company_id`,`introduced`,`discontinued`, company.name FROM `computer`, `company.name` LEFT JOIN `company` ON company_id = company.id WHERE computer.name=?";
	private static final String GET_WITH_ID = "SELECT computer.id, computer.name,`company_id`,`introduced`,`discontinued`, company.name FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE computer.id=?";
	private static final String GET_PAGE = "SELECT computer.id, computer.name,`company_id`,`introduced`,`discontinued`, company.name FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE LOWER(computer.name) LIKE ? OR LOWER(company.name) LIKE ? ORDER BY "; 
	private static final String GET_NB_COMPUTERS = "SELECT COUNT(computer.id) FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE LOWER(computer.name) LIKE ? OR LOWER(company.name) LIKE ?"; 
	
	/**
	 * @param id	Identified of a computer
	 * @return The computer found
	 */
	public static Computer getComputer(int id)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement(GET_WITH_ID);
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
			PreparedStatement query = connection.prepareStatement(GET_WITH_NAME);
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
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DBConnection.getLogger().error("Transaction is being rolled back");
		        connection.rollback();
		        throw new RollbackHappened();
		    } 
			catch (SQLException excep) 
			{
		    	excep.printStackTrace();
		    }
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
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DBConnection.getLogger().error("Transaction is being rolled back");
				connection.rollback();
				throw new RollbackHappened();
		    } 
			catch (SQLException excep) 
			{
		    	excep.printStackTrace();
		    }
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
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DBConnection.getLogger().error("Transaction is being rolled back");
				connection.rollback();
				throw new RollbackHappened();
		    } 
			catch (SQLException excep) 
			{
		    	excep.printStackTrace();
		    }
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
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DBConnection.getLogger().error("Transaction is being rolled back");
				connection.rollback();
				throw new RollbackHappened();
		    } 
			catch (SQLException excep) 
			{
		    	excep.printStackTrace();
		    }
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
	
	public static List<Computer> getPage(int size, int offset, String search, String column)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement(GET_PAGE + column + " LIMIT ? OFFSET ?");
			DBConnection.getLogger().info("Getting page from database with size "+ size + ", offset "+ offset + ", searched "+search + " and order " + column);
			query.setString(1, "%"+search+"%");
			query.setString(2, "%"+search+"%");
			//query.setString(3, column);
			query.setInt(3, size);
			query.setInt(4, offset);
			DBConnection.getLogger().debug(query.toString());
			ResultSet result = query.executeQuery();
			
			List<Computer> page = new ArrayList<Computer>();
			while (result.next())
			{
				page.add(ComputerDAOMapper.mapToComputer(result));
			}
			try {
				int id = Integer.valueOf(search);
				Computer toAdd = getComputer(id);
				try {
					Validator.validate(toAdd);
					page.add(toAdd);
				}
				catch(RuntimeException re)
				{}
			}
			catch (Exception e)
			{}
			DBConnection.getLogger().info("Page gathered : " + page);
			return page;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

	
	public static int getNbComputers(String search)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement(GET_NB_COMPUTERS);
			DBConnection.getLogger().debug(GET_NB_COMPUTERS);
			query.setString(1, "%"+search+"%");
			query.setString(2, "%"+search+"%");
			ResultSet result = query.executeQuery();
			result.next();
			DBConnection.getLogger().info("Nb computers : " + result.getInt(1));
			int plusOne = 0;
			
			try {
				int id = Integer.valueOf(search);
				Computer toAdd = getComputer(id);
				try {
					Validator.validate(toAdd);
					plusOne = 1;
				}
				catch(RuntimeException re)
				{}
			}
			catch (Exception e)
			{}
			return result.getInt(1) + plusOne;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
