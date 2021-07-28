package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import mapper.CompanyMapper;
import model.Company;
import model.exceptions.RollbackHappened;


/**
 * Class CompanyRequesthandler :
 * Manage the SQL requests for the companies
 * @author Lisa
 */
public class CompanyRequestHandler {

	/**
	 * @param id	Identifier of a company
	 * @return The company found
	 */
	public static Company getCompany(int id)
	{
		try (Connection connection = DBConnection.getConnection();){
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
	
	public static Company getCompany(String name)
	{
		try (Connection connection = DBConnection.getConnection();){
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `company` WHERE name=?");
			
			query.setString(1, name);
			ResultSet result = query.executeQuery();
			result.next();
			
			return CompanyMapper.mapToCompany(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @return All the companies in the database
	 */
	public static HashMap<Integer,Company> getAllCompanies()
	{
		HashMap<Integer,Company> companies = new HashMap<Integer,Company>();
	
		try (Connection connection = DBConnection.getConnection();) {
			//Send the request to get all the companies
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `company`");
			ResultSet result = query.executeQuery();
			//Put all the companies into a list
			while (result.next())
			{
				companies.put(result.getInt("id"),CompanyMapper.mapToCompany(result));
			}
			return companies;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void deleteCompany(int id)
	{
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("DELETE FROM `computer` WHERE company_id=?");
			
			DBConnection.getLogger().debug("DELETE FROM `computer` WHERE company_id="+ id);
			query.setInt(1, id);
			query.executeUpdate();
			
			query.execute("DELETE FROM `company` WHERE id="+id);
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DBConnection.getLogger().error("Transaction is being rolled back");
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
