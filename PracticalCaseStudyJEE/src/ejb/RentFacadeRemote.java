package ejb;

import java.util.Collection;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import jpa.CancellationJPA;
import jpa.CustomerJPA;
import jpa.RentItemJPA;
import jpa.RentJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface RentFacadeRemote {
	  /**
	   * Remotely invoked method.
	   */
	public String createRent(RentJPA rent, Integer customerId);
	public Collection<?> listAllActiveRents();
	public Collection<?> listAllActiveRentsById(String id);
	public Collection<?> listRentsByCustomer(CustomerJPA customer);
	public Collection<?> listRentsByCustomerAndById(CustomerJPA customer, String id);
	public RentJPA showRent(String rentId); 
	public void updateRent(RentJPA rent);
	public void createCancellation(RentJPA rent);
	public Collection<?> listPendingCancellations();
	public CancellationJPA showCancellation(String rentId); 
	public void closeCancellation(CancellationJPA cancellation);
	public Collection<RentItemJPA> listRentItemsByRent(String rentId);
	public void createReservation(RentJPA rent);
	public void addItemToRent(String itemId, String rentSelected);
}
