package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import builder.ComputerBuilder;
import mapper.ComputerDAOMapper;
import model.Computer;
import model.exceptions.RollbackHappened;
import service.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

/**
 * Class ComputersRequesthandler :
 * Manage the SQL requests for the computers
 * @author Lisa
 */

@Repository
@Scope("singleton")
public class ComputerRequestHandler {

	private static final String GET_WITH_NAME = "SELECT computer.id, computer.name,`company_id`,`introduced`,`discontinued`, company.name FROM `computer`, `company.name` LEFT JOIN `company` ON company_id = company.id WHERE computer.name=?";
	private static final String GET_WITH_ID = "SELECT computer.id, computer.name,`company_id`,`introduced`,`discontinued`, company.name FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE computer.id=?";
	private static final String GET_PAGE = "SELECT computer.id, computer.name,`company_id`,`introduced`,`discontinued`, company.name FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE LOWER(computer.name) LIKE ? OR LOWER(company.name) LIKE ? ORDER BY "; 
	private static final String GET_NB_COMPUTERS = "SELECT COUNT(computer.id) FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE LOWER(computer.name) LIKE ? OR LOWER(company.name) LIKE ?"; 

	private ComputerDAOMapper computerDAOMapper;
	private DBConnection dbConnection;
	
	@Autowired
	public ComputerRequestHandler(DBConnection dbConnection,ComputerDAOMapper computerDAOMapper)
	{
		this.computerDAOMapper = computerDAOMapper;
		this.dbConnection = dbConnection;
	}
	
	/**
	 * @param id	Identified of a computer
	 * @return The computer found
	 */
	public Computer getComputer(int id)
	{
		try (Connection connection = dbConnection.getConnection();){
			PreparedStatement query = connection.prepareStatement(GET_WITH_ID);
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			connection.commit();
			result.next();
			connection.commit();
			return computerDAOMapper.mapToComputer(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ComputerBuilder().build();
		}
	}
	
	/**
	 * @param name	Name of a computer
	 * @return The computer found
	 */
	public Computer getComputer(String name)
	{			
		try (Connection connection = dbConnection.getConnection();) {
			PreparedStatement query = connection.prepareStatement(GET_WITH_NAME);
			query.setString(1, name);
			ResultSet result = query.executeQuery();
			connection.commit();
			result.next();
			
			return computerDAOMapper.mapToComputer(result);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ComputerBuilder().build();
		}
	}
	
	/**
	 * @return The list of all the computers
	 */
	public HashMap<Integer,Computer> getAllComputers()
	{
		HashMap<Integer,Computer> computers = new HashMap<Integer,Computer>();
		
		try (Connection connection = dbConnection.getConnection();){
			//Send the request to get all the computers
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `computer` LEFT JOIN `company` ON company_id = company.id");
			ResultSet result = query.executeQuery();
			connection.commit();
			//Create the list of all the computers
			while (result.next())
			{
				computers.put(result.getInt("computer.id"),computerDAOMapper.mapToComputer(result));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computers;
	}
	
	public ArrayList<Computer> getPage(int size, int offset, String search, String column)
	{
		ArrayList<Computer> page = new ArrayList<Computer>();
		try (Connection connection = dbConnection.getConnection();) {
			PreparedStatement query = connection.prepareStatement(GET_PAGE + column + " LIMIT ? OFFSET ?");
			dbConnection.getLogger().info("Getting page from database with size "+ size + ", offset "+ offset + ", searched "+search + " and order " + column);
			query.setString(1, "%"+search.toLowerCase()+"%");
			query.setString(2, "%"+search.toLowerCase()+"%");
			//query.setString(3, column);
			query.setInt(3, size);
			query.setInt(4, offset);
			dbConnection.getLogger().debug(query.toString());
			ResultSet result = query.executeQuery();
			connection.commit();
			while (result.next())
			{
				page.add(computerDAOMapper.mapToComputer(result));
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
			dbConnection.getLogger().info("Page gathered : " + page);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	public int getNbComputers(String search)
	{
		try (Connection connection = dbConnection.getConnection();) {
			PreparedStatement query = connection.prepareStatement(GET_NB_COMPUTERS);
			dbConnection.getLogger().debug(GET_NB_COMPUTERS);
			query.setString(1, "%"+search.toLowerCase()+"%");
			query.setString(2, "%"+search.toLowerCase()+"%");
			ResultSet result = query.executeQuery();
			connection.commit();
			result.next();
			dbConnection.getLogger().info("Nb computers : " + result.getInt(1));
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
	
	/**
	 * @param computer	Computer to create
	 */
	public void createComputer(Computer computer, int company_id)
	{
		Connection connection = dbConnection.getConnection();
		try {
			//Use the mapper to get the representation of the computer to insert
			PreparedStatement query = connection.prepareStatement("INSERT INTO `computer`"+ computerDAOMapper.mapToCreate(computer, company_id));
			dbConnection.getLogger().debug("INSERT INTO `computer`"+ computerDAOMapper.mapToCreate(computer, company_id));
			query.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				dbConnection.getLogger().error("Transaction is being rolled back");
		        connection.rollback();
		        connection.close();
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
	public void updateComputer(Computer computer,int company_id)
	{
		Connection connection = dbConnection.getConnection();
		try {
			//Use the mapper to get the representation of the computer to update
			PreparedStatement query = connection.prepareStatement("UPDATE `computer` SET "+ computerDAOMapper.mapToUpdate(computer,company_id) + "WHERE id=?");
			dbConnection.getLogger().debug("UPDATE `computer` SET "+ computerDAOMapper.mapToUpdate(computer, company_id) + "WHERE id=?");
			query.setInt(1, computer.getId());
			query.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				dbConnection.getLogger().error("Transaction is being rolled back");
				connection.rollback();
				connection.close();
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
	public void deleteComputer(int id)
	{
		Connection connection = dbConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("DELETE FROM `computer` WHERE id=?");
			
			dbConnection.getLogger().debug("DELETE FROM `computer` WHERE id="+ id);
			query.setInt(1, id);
			query.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				dbConnection.getLogger().error("Transaction is being rolled back");
				connection.rollback();
				connection.close();
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
	public void deleteComputer(String name)
	{
		Connection connection = dbConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("DELETE FROM `computer` WHERE name=?");
			dbConnection.getLogger().debug("DELETE FROM `computer` WHERE name=?");
			query.setString(1, name);
			query.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				dbConnection.getLogger().error("Transaction is being rolled back");
				connection.rollback();
				connection.close();
				throw new RollbackHappened();
		    } 
			catch (SQLException excep) 
			{
		    	excep.printStackTrace();
		    }
		}
	}
	
}
