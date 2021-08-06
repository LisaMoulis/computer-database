package persistence;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import mapper.CompanyMapper;
import model.Company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.*;

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
	
	private JdbcTemplate jdbcTemplate;
	private DBConnection dbConnection;
	
	@Autowired
	public CompanyRequestHandler(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		//this.dbConnection = dbConnection;
	}
	
	public Company getCompany(int id)
	{
		List<Company> companies =  jdbcTemplate.query("SELECT * FROM `company` WHERE id=?", new Object[] {id}, new int[] { Types.INTEGER}, new CompanyMapper());
		if (companies.isEmpty())
		{
			return new Company(-1,null);
		}
		return companies.get(0);
	}
	
	public Company getCompany(String name)
	{
		List<Company> companies =  jdbcTemplate.query("SELECT * FROM `company` WHERE name=?", new Object[] { name }, new int[] { Types.VARCHAR}, new CompanyMapper());	
		if (companies.isEmpty())
		{
			return new Company(-1,null);
		}
		return companies.get(0);
	}
	
	/**
	 * @return All the companies in the database
	 */
	public List<Company> getAllCompanies()
	{
		return jdbcTemplate.query("SELECT * FROM `company`",new CompanyMapper());
	}
	
	@Transactional
	public void deleteCompany(int id)
	{
		jdbcTemplate.update("DELETE FROM `computer` WHERE company_id=?",new Object[] { id }, new int[] { Types.INTEGER});
		jdbcTemplate.update("DELETE FROM `company` WHERE id=?",new Object[] { id }, new int[] { Types.INTEGER});
	}
}
