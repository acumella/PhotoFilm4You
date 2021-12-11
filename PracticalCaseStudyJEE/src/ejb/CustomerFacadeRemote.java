package ejb;

import javax.ejb.Remote;
import jpa.CustomerJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface CustomerFacadeRemote {
	  /**
	   * Remotely invoked method.
	   */
	public String createCustomer(CustomerJPA customer);
	public String updateCustomer(CustomerJPA customer);
}
