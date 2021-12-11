package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.RentItemJPA;
import ejb.RentFacadeRemote;

/**
 * Managed Bean ListAllRentItems
 * 
 **/

@ManagedBean(name = "rentItemsList")
@SessionScoped
public class ListAllRentItems implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private RentFacadeRemote rentRemote;
	
	String rentId;
	private Collection<RentItemJPA> rentsList;
	//stores the screen number where the user is 
	private int screen = 0;
	protected Collection<RentItemJPA> rentsListView;
	protected int numberRents = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllRentItems() throws Exception
	{
		this.rentItemsList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less RentItemJPA according screen 
	 * where the user is.
	 * @return Collection RentItemJPA
	 */
	public Collection<RentItemJPA> getRentsListView()
	{
		try {
			rentItemsList();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		int n =0;
		rentsListView = new ArrayList<RentItemJPA>();
		for (Iterator<RentItemJPA> iter2 = rentsList.iterator(); iter2.hasNext();)
		{
			RentItemJPA rent2 = (RentItemJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.rentsListView.add(rent2);
			}
			n +=1;
		}
		this.numberRents = n;
		return rentsListView;
	}
	
	/**
	 * Returns the total number of instances of RentItemJPA
	 * @return RentItem number
	 */
	public int getNumberRents()
	{ 
		return this.numberRents;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < rentsList.size()))
		{
			screen +=1;
		}
	}
	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}
	
	/**
	 * Get/set the Category name
	 * @return Category name
	 */
	public String getRentId()
	{
		return this.rentId;
	}
	public void setRentId(String rentId)
	{
		this.rentId = rentId;
		try {
			rentItemsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all RentItemJPA
	 * @throws Exception
	 */
	private void rentItemsList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		rentsList = (Collection<RentItemJPA>)rentRemote.listRentItemsByRent(rentId);
	}
	
	public String updateList()
	{
		try {
			rentItemsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllRentItemsView";
	}
}
