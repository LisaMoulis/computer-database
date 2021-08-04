package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mapper.CompanyMapper;
import model.Company;
import model.exceptions.RollbackHappened;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

/**
 * Class CompanyRequesthandler :
 * Manage the SQL requests for the companies
 * @author Lisa
 */

@Repository
public class CompanyRequestHandler {

	/**
	 * @param id	Identifier of a company
	 * @return The company found
	 */
	
	private DBConnection dbConnection;
	
	@Autowired
	public CompanyRequestHandler(DBConnection dbConnection)
	{
		this.dbConnection = dbConnection;
	}
	
	public Company getCompany(int id)
	{
		try (Connection connection = dbConnection.getConnection();){
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `company` WHERE id=?");
			
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			connection.commit();
			result.next();
			return CompanyMapper.mapToCompany(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Company(-1,null);
		}
	}
	
	public Company getCompany(String name)
	{
		try (Connection connection = dbConnection.getConnection();){
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `company` WHERE name=?");
			
			query.setString(1, name);
			ResultSet result = query.executeQuery();
			connection.commit();
			result.next();
			return CompanyMapper.mapToCompany(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Company(-1,null);
		}
	}
	
	/**
	 * @return All the companies in the database
	 */
	public ArrayList<Company> getAllCompanies()
	{
		ArrayList<Company> companies = new ArrayList<Company>();
	
		try (Connection connection = dbConnection.getConnection();) {
			//Send the request to get all the companies
			PreparedStatement query = connection.prepareStatement("SELECT * FROM `company`");
			ResultSet result = query.executeQuery();
			connection.commit();
			//Put all the companies into a list
			while (result.next())
			{
				Company c = CompanyMapper.mapToCompany(result);
				c.setId(result.getInt("id"));
				companies.add(c);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
	}
	
	public void deleteCompany(int id)
	{
		Connection connection = dbConnection.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement("DELETE FROM `computer` WHERE company_id=?");
			
			dbConnection.getLogger().debug("DELETE FROM `computer` WHERE company_id="+ id);
			query.setInt(1, id);
			query.executeUpdate();
			
			query.execute("DELETE FROM `company` WHERE id="+id);
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
