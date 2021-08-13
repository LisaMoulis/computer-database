package persistence;

import java.sql.Types;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Expression;
import javax.sql.*;

import builder.ComputerBuilder;
import mapper.ComputerDAOMapper;
import model.Company;
import model.Computer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.*;

/**
 * Class ComputersRequesthandler :
 * Manage the SQL requests for the computers
 * @author Lisa
 */

@Repository
@Scope("singleton")
public class ComputerRequestHandler {

	private static final String GET_WITH_NAME = "from Computer c where c.name=:name";//"SELECT computer.id, computer.name,`company_id`,`introduced`,`discontinued`, company.name FROM `computer`, `company.name` LEFT JOIN `company` ON company_id = company.id WHERE computer.name=?";
	//private static final String GET_WITH_ID = "SELECT computer.id, computer.name,`company_id`,`introduced`,`discontinued`, company.name FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE computer.id=?";
	private static final String GET_PAGE = "from Computer c where lower(c.name) like ?1 or lower(c.company.name) like ?2 order by ";//"SELECT computer.id, computer.name,`company_id`,`introduced`,`discontinued`, company.name FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE LOWER(computer.name) LIKE ? OR LOWER(company.name) LIKE ? ORDER BY "; 
	private static final String GET_NB_COMPUTERS = "SELECT COUNT(computer.id) FROM `computer` LEFT JOIN `company` ON company_id = company.id WHERE LOWER(computer.name) LIKE ? OR LOWER(company.name) LIKE ?"; 

	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SessionFactory sessionFactory;
	private ComputerDAOMapper computerDAOMapper;
	
	@Autowired
	public ComputerRequestHandler(DataSource dataSource,ComputerDAOMapper computerDAOMapper)
	{
		this.computerDAOMapper = computerDAOMapper;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/**
	 * @param id	Identified of a computer
	 * @return The computer found
	 */
	public Computer getComputer(int id)
	{
		
		Session session = sessionFactory.openSession();
		Computer computer = session.find(Computer.class, id);
		session.close();
		return computer;
	}
	
	/**
	 * @param name	Name of a computer
	 * @return The computer found
	 */
	public Computer getComputer(String name)
	{			
		Session session = sessionFactory.openSession();
		Query<Computer> query = session.createQuery(GET_WITH_NAME, Computer.class);
	    query.setParameter("name", name);
	    Computer computer = query.uniqueResult();
	    session.close();
		return computer;
	}
	
	/**
	 * @return The list of all the computers
	 */
	public List<Computer> getAllComputers()
	{
		return jdbcTemplate.query("SELECT * FROM `computer` LEFT JOIN `company` ON company_id = company.id", this.computerDAOMapper);
	}
	
	public List<Computer> getPage(int size, int offset, String search, String column, String sense)
	{
		Session session = sessionFactory.openSession();
		String str = GET_PAGE + column + " " + sense;
		try {
			int id = Integer.valueOf(search);
			str = str+ " OR c.id = " + id;
		}
		catch (Exception e)
		{}
			
		//str = str + " LIMIT ? OFFSET ?";
		//List<Computer> page = jdbcTemplate.query(str, new Object[] { "%"+search.toLowerCase()+"%", "%"+search.toLowerCase()+"%", size, offset}, new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER}, this.computerDAOMapper);
		/*CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Computer> cr = cb.createQuery(Computer.class);
		Root<Computer> root = cr.from(Computer.class);
		cr = cr.select(root);
		if (sense.equals("DESC"))
		{
			cr = cr.orderBy(cb.desc(root.get(column)));
		}
		else
		{
			cr = cr.orderBy(cb.asc(root.get(column)));
		}
		cr.having(new Expression().("LIMIT ? OFFSET ?", new Object[] { "%"+search.toLowerCase()+"%", "%"+search.toLowerCase()+"%", size, offset}, new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER}));*/
		//dbConnection.getLogger().info("Page gathered : " + page);
		Query<Computer> query = session.createQuery(str,Computer.class);
		query.setParameter(1, "%"+search.toLowerCase()+"%");
		query.setParameter(2, "%"+search.toLowerCase()+"%");
		//query.setParameter(3, size);
		//query.setParameter(4, offset);
		query.setFirstResult(offset).setMaxResults(size);
		List<Computer> page = query.getResultList();
		session.close();
		return page;
	}

	public int getNbComputers(String search)
	{
		String str = GET_NB_COMPUTERS;
		try {
			int id = Integer.valueOf(search);
			str = str+ " OR computer.id = " + id;
		}
		catch (Exception e)
		{}
			
		//dbConnection.getLogger().info("Nb computers : " + result.getInt(1));

		return jdbcTemplate.queryForObject(str, new Object[] {"%"+search.toLowerCase()+"%","%"+search.toLowerCase()+"%"}, new int[] {Types.VARCHAR, Types.VARCHAR}, Integer.class);
	}
	
	/**
	 * @param computer	Computer to create
	 */
	public void createComputer(Computer computer, int company_id)
	{
		jdbcTemplate.update("INSERT INTO `computer`"+ computerDAOMapper.mapToCreate(computer, company_id));
	}
	
	/**
	 * @param computer	Computer to update
	 */
	public void updateComputer(Computer computer,int company_id)
	{
		jdbcTemplate.update("UPDATE `computer` SET "+ computerDAOMapper.mapToUpdate(computer,company_id) + "WHERE id=?",new Object[] {computer.getId()}, new int[] {Types.INTEGER});
	}
	
	/**
	 * @param id	Identifier of the computer to delete
	 */
	public void deleteComputer(int id)
	{
		jdbcTemplate.update("DELETE FROM `computer` WHERE id=?",new Object[] { id }, new int[] {Types.INTEGER});
	}
	
	/**
	 * @param name Name of the computer to delete
	 */
	public void deleteComputer(String name)
	{
		jdbcTemplate.update("DELETE FROM `computer` WHERE name=?",new Object[] { name }, new int[] {Types.VARCHAR});
	}
	
}
