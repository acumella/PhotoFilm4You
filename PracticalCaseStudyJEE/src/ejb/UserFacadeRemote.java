package ejb;

import javax.ejb.Remote;
import jpa.UserJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface UserFacadeRemote {
	  /**
	   * Remotely invoked method.
	   */
	public UserJPA login(String email, String password);
	public String createUser(UserJPA user);
	public UserJPA getUser(Integer id);
}
