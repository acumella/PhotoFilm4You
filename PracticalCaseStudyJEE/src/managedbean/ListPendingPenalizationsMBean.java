package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.CancellationJPA;
import ejb.RentFacadeRemote;

/**
 * Managed Bean ListPendingPenalizationsMBean
 */
@ManagedBean(name = "penalizationList")
@SessionScoped
public class ListPendingPenalizationsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private RentFacadeRemote rentRemote;
	
	private Collection<CancellationJPA> cancellationsList;
	//stores the screen number where the user is 
	private int screen = 0;
	protected Collection<CancellationJPA> cancellationsListView;
	protected int numberCancellations = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListPendingPenalizationsMBean() throws Exception
	{
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less CancellationJPA according screen 
	 * where the user is.
	 * @return Collection CancellationJPA
	 */
	public Collection<CancellationJPA> getCancellationsListView()
	{
		updateList();
		int n =0;
		cancellationsListView = new ArrayList<CancellationJPA>();
		for (Iterator<CancellationJPA> iter2 = cancellationsList.iterator(); iter2.hasNext();)
		{
			CancellationJPA rent2 = (CancellationJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.cancellationsListView.add(rent2);
			}
			n +=1;
		}
		this.numberCancellations = n;
		return cancellationsListView;
	}
	
	/**
	 * Returns the total number of instances of CancellationJPA
	 * @return Cancellation number
	 */
	public int getNumberCancellations()
	{ 
		return this.numberCancellations;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < cancellationsList.size()))
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
	 * Method that gets a list of instances by category or all CancellationJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void cancellationList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		cancellationsList = (Collection<CancellationJPA>)rentRemote.listPendingCancellations();
	}
	
	public String updateList()
	{
		try {
			cancellationList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listPendingPenalizationsView";
	}
}
