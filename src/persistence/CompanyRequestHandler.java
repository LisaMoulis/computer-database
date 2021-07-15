package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import mapper.CompanyMapper;

public class CompanyRequestHandler {

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