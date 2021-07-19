package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	public static String mapToCompany(ResultSet result) throws SQLException
	{
		String name = result.getString("name");
		return name;
	}
}
