package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.CustomerJPA;

/**
 * EJB Session Bean Class of Customer
 */

@Stateless
public class CustomerFacadeBean implements CustomerFacadeRemote {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	public String createCustomer(CustomerJPA customer) {
		String result = "";
		try
		{
			entman.persist(customer);
			entman.persist(customer.getUser());
			entman.flush();
			result = "customer created";
		}
		catch (PersistenceException e) {
			System.out.println(e);
			result = e.getMessage();
			
		}
		catch(Exception e) {
			System.out.println(e);
			result = "Exception, customer NOT inserted";			
		}
		return result;
	}
	
	public String updateCustomer(CustomerJPA customer) {
		String result = "";
		try
		{
			entman.merge(customer);
			// entman.flush();
			result = "customer updated";
		}
		catch (PersistenceException e) {
			System.out.println(e);
			result = e.getMessage();
			
		}
		catch(Exception e) {
			System.out.println(e);
			result = "Exception, customer NOT updated";			
		}
		return result;
	}
}
