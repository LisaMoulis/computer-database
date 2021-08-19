package persistence;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.User;

@Repository
public class UserDAO {

	private static final String GET_WITH_NAME = "from User u where username = :username";
	private static final String GET_ALL = "from User u";
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public UserDAO(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public User getUser(String u)
	{
		Session session = sessionFactory.openSession();
		Query<User> query = session.createQuery(GET_WITH_NAME, User.class);
	    query.setParameter("username", u);
	    User user = query.uniqueResult();
	    session.close();
		return user;
	}
	
	public List<User> getAllUser()
	{
		Session session = sessionFactory.openSession();
		Query<User> query = session.createQuery(GET_ALL, User.class);
	    List<User> users = query.getResultList();
	    session.close();
		return users;
	}
	
	public void registerUser(User user)
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
		  session.save(user);
		  transaction.commit();
		} catch (RollbackException t) {
		  transaction.rollback();
		  throw t;
		}
		session.close();
	}
}
