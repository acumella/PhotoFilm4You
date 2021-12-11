package ejb;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.CustomerJPA;
import jpa.UserJPA;

/**
 * EJB Session Bean Class of Customer
 */

@Stateless
public class UserFacadeBean implements UserFacadeRemote {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	public UserJPA login(String email, String password) {
		UserJPA user = null;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<UserJPA> users = entman.createQuery("FROM UserJPA u WHERE u.email = ?1 AND u.password = ?2")
				.setParameter(1, email)
				.setParameter(2, password)
				.getResultList();
			if (!users.isEmpty() || users.size()==1)
			{
				Iterator<UserJPA> iter =users.iterator();
				user = (UserJPA) iter.next();				
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return user;
	}
	
	public String createUser(UserJPA user) {
		String result = "";
		try
		{
			entman.persist(user);
			entman.flush();
			result = "user created";
		}
		catch (PersistenceException e) {
			System.out.println(e);
			result = e.getMessage();
		}
		catch(Exception e) {
			System.out.println(e);
			result = "Exception, user NOT inserted";			
		}
		return result;
	}
	
	public UserJPA getUser(Integer id) {
		UserJPA user = null;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<UserJPA> users = entman.createQuery("FROM UserJPA u WHERE u.id = ?1")
				.setParameter(1, id)
				.getResultList();
			if (!users.isEmpty() || users.size()==1)
			{
				Iterator<UserJPA> iter =users.iterator();
				user = (UserJPA) iter.next();				
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return user;
	}
}
