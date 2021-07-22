package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import mapper.CompanyMapper;
import model.Company;


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
	
	/**
	 * @return All the companies in the database
	 */
	public static HashMap<Integer,Company> getAllCompanies()
	{
		HashMap<Integer,Company> companies = new HashMap<Integer,Company>();
		Connection connection = DBConnection.getConnection();
		try {
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
}
