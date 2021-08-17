package persistence;

import java.util.List;

import javax.persistence.RollbackException;

import model.Company;
import model.Computer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;


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
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public CompanyRequestHandler(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public Company getCompany(int id)
	{
		Session session = sessionFactory.openSession();
		Company company = session.find(Company.class, id);
		session.close();
		return company;
	}
	
	public Company getCompany(String name)
	{
		Session session = sessionFactory.openSession();
		Query<Company> query = session.createQuery("from Company c where c.name=:name", Company.class);
	    query.setParameter("name", name);
	    Company company = query.uniqueResult();
	    session.close();
		return company;
	}
	
	/**
	 * @return All the companies in the database
	 */
	public List<Company> getAllCompanies()
	{
		Session session = sessionFactory.openSession();
		Query<Company> query = session.createQuery("from Company c", Company.class);
		List<Company> companies = query.getResultList();
		session.close();
		return companies;
	}
	
	@Transactional
	public void deleteCompany(int id)
	{
		//jdbcTemplate.update("DELETE FROM `computer` WHERE company_id=?",new Object[] { id }, new int[] { Types.INTEGER});
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
		  Query<Computer> queryComputer = session.createQuery("delete from Computer where company_id = :id",Computer.class);
		  queryComputer.setParameter("id", id);
		  queryComputer.executeUpdate();
		  Query<Company> queryCompany  = session.createQuery("delete from Company where id = :id",Company.class);
		  queryCompany.setParameter("id", id);
		  queryCompany.executeUpdate();

		  transaction.commit();
		} catch (RollbackException t) {
		  transaction.rollback();
		  throw t;
		}
		//session.remove(id);
		session.close();
	}
}
