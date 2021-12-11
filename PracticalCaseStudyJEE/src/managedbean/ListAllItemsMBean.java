package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.ItemJPA;
import jpa.ModelJPA;
import ejb.CatalogFacadeRemote;


/**
 * Managed Bean ListAllItems
 */
@ManagedBean(name = "listallitems")
@SessionScoped
public class ListAllItemsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	private String name;
	
	private Collection<ItemJPA> itemsList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer ItemJPA instances that the user can see on a screen
	protected Collection<ItemJPA> itemsListView;
	//stores the total number of instances of ItemJPA
	protected int numberItems = 0;
	public enum ItemStatus {
		OPERATIONAL, BROKEN
	}
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllItemsMBean() throws Exception
	{
		this.allItemsList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less ModelJPA according screen 
	 * where the user is.
	 * @return Collection ItemJPA
	 */
	public Collection<ItemJPA> getItemsListView() {
		try {
			allItemsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int n =0;
		itemsListView = new ArrayList<ItemJPA>();
		for (Iterator<ItemJPA> iter2 = itemsList.iterator(); iter2.hasNext();)
		{
			ItemJPA item2 = (ItemJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.itemsListView.add(item2);
			}
			n +=1;
		}
		this.numberItems = n;
		return itemsListView;
	}

	public void setItemsListView(Collection<ItemJPA> itemsListView) {
		this.itemsListView = itemsListView;
	}

	/**
	 * Returns the total number of instances of ItemJPA
	 * @return Item number
	 */
	public int getNumberItems()
	{ 
		return this.numberItems;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < itemsList.size()))
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
	 * Get/set the Model name
	 * @return Model name
	 */
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
		try {
			allItemsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all ModelJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allItemsList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		itemsList = (Collection<ItemJPA>)myCatalogRemote.listAllItems();
	}
	
	public String updateList()
	{
		try {
			allItemsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllItemsView";
	}
}
