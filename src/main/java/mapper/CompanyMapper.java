package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;

/**
 * Class CompanyMapper :
 * Map the information between the database and companies
 * @author Lisa
 */
public class CompanyMapper {

	/**
	 * @param result	Representation of the company from the database
	 * @return Name of the company
	 * @throws SQLException
	 */
	public static Company mapToCompany(ResultSet result) throws SQLException
	{
		return new Company(result.getInt("id"),result.getString("name"));
	}
}
