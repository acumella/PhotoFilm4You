package ejb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import jpa.CancellationJPA;
import jpa.CategoryJPA;
import jpa.CustomerJPA;
import jpa.ItemJPA;
import jpa.RentItemJPA;
import jpa.RentJPA;
import jpa.ReservationJPA;
import jpa.RentJPA;

/**
 * EJB Session Bean Class of Rent
 */

@Stateless
public class RentFacadeBean implements RentFacadeRemote {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	public String createRent(RentJPA rent, Integer customerId) {
		String pattern = "dd-MM-yyyy";
		String result = "";
		try
		{	
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
			if(session != null) {
				if (session.getAttribute("role").equals("ROLE_CUSTOMER")) {
					DateFormat df = new SimpleDateFormat(pattern);
					String fromDateStr = df.format(rent.getFromDate());
					String toDateStr = df.format(rent.getToDate()); 
					@SuppressWarnings("unchecked")			
					String query = "insert into practicalcase.rent values ('" + rent.getId() + "','"+ fromDateStr +"','" + toDateStr + "','" + 
							customerId + "'," + rent.getStatus() + ", 0.0)";
					entman.createNativeQuery(query).executeUpdate();
					result = "rent inserted";
				}
				else {					
					result = "administrators can't create rents, role:: " + session.getAttribute("role");
				}
			}
			else {
				result = "no user logged found";
			}
		}
		catch (PersistenceException e) {
			System.out.println(e);
			result = e.getMessage();
			
		}
		catch(Exception e) {
			System.out.println(e);
			result = "Exception, rent NOT inserted";			
		}
		finally {
			return result;
		}
	}


	public Collection<RentJPA> listAllActiveRents()
	{
		@SuppressWarnings("unchecked")
		Collection<RentJPA> allRents = entman.createQuery("from RentJPA").getResultList();
		return allRents;
	}
	
	public Collection<RentJPA> listAllActiveRentsById(String id)
	{
		@SuppressWarnings("unchecked")
		Collection<RentJPA> allRents = entman.createQuery("from RentJPA b WHERE b.id LIKE '%:id%'").setParameter("id", id).getResultList();
		return allRents;
	}
	
	public Collection<RentJPA> listRentsByCustomer(CustomerJPA customer)
	{
		try {
			
			@SuppressWarnings("unchecked")
			Collection<RentJPA> allRentsByCustomerID = (Collection<RentJPA>)entman.createQuery("FROM RentJPA b WHERE b.customer.id = :id").setParameter("id", customer.getId()).getResultList();
			return allRentsByCustomerID;
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
		catch (Exception e) {
			System.out.println(e);
		} 
	    return new ArrayList<RentJPA>();
	}
	
	public Collection<RentJPA> listRentsByCustomerAndById(CustomerJPA customer, String id)
	{
		try {
			
			String queryString = "FROM RentJPA b WHERE b.customer.id = ";
			queryString += customer.getId();
			queryString += " AND b.id LIKE '%";
			queryString += id;
			queryString += "%'";
			Query query = entman.createQuery(queryString);
			@SuppressWarnings("unchecked")
			Collection<RentJPA> allRentsByCustomerID = (Collection<RentJPA>) query.getResultList();
			return allRentsByCustomerID;
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
		catch (Exception e) {
			System.out.println(e);
		} 
	    return new ArrayList<RentJPA>();
	}
	
	/**
	 * Method that returns instance of the class RentJPA
	 */
	public RentJPA showRent(String rentId)throws PersistenceException 
	{
		RentJPA rent = null;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<RentJPA> rents = entman.createQuery("FROM RentJPA b WHERE b.id = ?1").setParameter(1, rentId).getResultList();
			if (!rents.isEmpty() || rents.size()==1)
			{
				Iterator<RentJPA> iter =rents.iterator();
				rent = (RentJPA) iter.next();				
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return rent;
	}	
	
	public void updateRent(RentJPA rent) throws PersistenceException 
	{		 
		try
		{
			String query = "UPDATE practicalcase.rent" + 
					" SET status = '" + rent.getStatus() + "'" + 
					" WHERE id = '" + rent.getId() + "'";
			entman.createNativeQuery(query).executeUpdate();
			
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	}
	
	public void createCancellation(RentJPA rent) {
		String pattern = "dd-MM-yyyy";
		String result = "";
		try
		{	
			CancellationJPA cancellation = new CancellationJPA();
			cancellation.calculatePenalization(rent.getFromDate(), rent.getToDate(), rent.getTotalPrice());
			
			DateFormat df = new SimpleDateFormat(pattern);
			String creationDate = df.format(cancellation.getCreationDate());
			@SuppressWarnings("unchecked")
			
			String query = "insert into practicalcase.cancellation values ('" + creationDate + "',"+ cancellation.getPenalization() +",0,'" + 
					rent.getId() + "')";
			entman.createNativeQuery(query).executeUpdate();
			result = "cancellation created";
		}
		catch (PersistenceException e) {
			System.out.println(e);
			result = e.getMessage();
			
		}
		catch(Exception e) {
			System.out.println(e);
			result = "Exception, cancellation NOT inserted";			
		}
		finally {
			//return result;
		}
	}
	
	public Collection<CancellationJPA> listPendingCancellations()
	{
		try {			
			@SuppressWarnings("unchecked")
			Collection<CancellationJPA> pendingCancellations = (Collection<CancellationJPA>)entman.createQuery("FROM CancellationJPA b WHERE b.penalizationStatus = :status").setParameter("status", 0).getResultList();
			return pendingCancellations;
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
		catch (Exception e) {
			System.out.println(e);
		} 
	    return new ArrayList<CancellationJPA>();
	}	

	public CancellationJPA showCancellation(String rentId)
	{
		CancellationJPA cancellation = null;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<CancellationJPA> cancellations = entman.createQuery("FROM CancellationJPA b WHERE b.rent = ?1").setParameter(1, rentId).getResultList();
			if (!cancellations.isEmpty() || cancellations.size()==1)
			{
				Iterator<CancellationJPA> iter = cancellations.iterator();
				cancellation = (CancellationJPA) iter.next();				
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return cancellation;
	}
	
	public void closeCancellation(CancellationJPA cancellation)
	{	 
		try
		{
			String query = "UPDATE practicalcase.cancellation" + 
					" SET penalizationStatus = '" + cancellation.getPenalizationStatus() + "'" + 
					" WHERE rent = '" + cancellation.getRent() + "'";
			entman.createNativeQuery(query).executeUpdate();
			
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	}
	
	public Collection<RentItemJPA> listRentItemsByRent(String rentId)
	{
		try {			
			@SuppressWarnings("unchecked")
			Collection<RentItemJPA> rentItems = (Collection<RentItemJPA>)entman.createQuery("FROM RentItemJPA b WHERE b.rent.id = :rentID").setParameter("rentID", rentId).getResultList();
			return rentItems;
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
		catch (Exception e) {
			System.out.println(e);
		} 
	    return new ArrayList<RentItemJPA>();
	}
	
	public void createReservation(RentJPA rent)
	{
		String result = "";
		
		Collection<RentItemJPA> rentItems = listRentItemsByRent(rent.getId());
		for (Iterator<RentItemJPA> iter2 = rentItems.iterator(); iter2.hasNext();)
		{		
			try
			{	
				RentItemJPA renItem = iter2.next();
				ItemJPA item = renItem.getItem();

				/*System.out.println("serialnumber::" + item.getSerialNumber());
				System.out.println("status::" + item.getStatus());*/
				//ReservationJPA reservation = new ReservationJPA();				
				@SuppressWarnings("unchecked")				
				String query = "insert into practicalcase.reservation(rentID, serialNumber) values ('" + rent.getId() + "','"+ renItem.getItem().getSerialNumber() + "')";
				//System.out.println(query);
				entman.createNativeQuery(query).executeUpdate();
				result = "reservation created";
			}
			catch (PersistenceException e) {
				System.out.println(e);
				result = e.getMessage();
				
			}
			catch(Exception e) {
				System.out.println(e);
				result = "Exception, reservation NOT inserted";			
			}
			finally {
				//return result;
			}		
		}
	}
	

	public void addItemToRent(String itemId, String rentSelected)
	{
		try
		{				
			System.out.println("itemId:::" + itemId);
			String query = "insert into practicalcase.rentItem(rentID, serialNumber) values ('" + rentSelected + "','"+ itemId + "')";
			//System.out.println(query);			
			entman.createNativeQuery(query).executeUpdate();
		}
		catch (PersistenceException e) {
			System.out.println(e);
			
		}
		catch(Exception e) {
			System.out.println(e);		
		}
	}
}
