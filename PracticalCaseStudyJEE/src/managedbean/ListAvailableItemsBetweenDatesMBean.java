package managedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@ManagedBean(name = "listAvailableItemsBetweenDates")
@SessionScoped
public class ListAvailableItemsBetweenDatesMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	private String name;
	
	private Collection<ItemJPA> itemList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer ItemJPA instances that the user can see on a screen
	protected Collection<ItemJPA> itemsListView;
	//stores the total number of instances of ItemJPA
	protected int numberItems = 0;
	public enum ItemStatus {
		OPERATIONAL, BROKEN
	}
	protected String result = "";
	protected String fromDate = "";
	protected String toDate = "";
	protected String idRent = "";
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAvailableItemsBetweenDatesMBean() throws Exception
	{
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less ModelJPA according screen 
	 * where the user is.
	 * @return Collection ItemJPA
	 */
	public Collection<ItemJPA> getItemsListView() 
	{		
		retrieveItems();
		int n =0;
		itemsListView = new ArrayList<ItemJPA>();
		if(itemList == null) return itemsListView;
		for (Iterator<ItemJPA> iter2 = itemList.iterator(); iter2.hasNext();)
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
		if (((screen+1)*10 < itemList.size()))
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

	public String getIdRent() {
		return idRent;
	}
	public void setIdRent(String idRent) {
		this.idRent = idRent;
	}
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
		retrieveItems();
	}
	
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
		retrieveItems();
	}
	
	@SuppressWarnings("unchecked")
	void retrieveItems()
	{
		System.out.println("[retrieveItems] fromDate::" + fromDate);
		System.out.println("[retrieveItems] toDate::" + toDate);
		
		if(fromDate != "" && toDate != "") {
			Date fromDateFormatted = null, toDateFormatted = null; 
			try {
				//fromDateFormatted = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);		
				fromDateFormatted = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				result = "format of from date invalid";
			}
			try {
				//toDateFormatted = new SimpleDateFormat("dd-MM-yyyy").parse(toDate);		
				toDateFormatted = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				result = "format of to date invalid";
			}
			
			try {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				screen = 0;
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				//productsList = (Collection<ProductJPA>)myCatalogRemote.listCategoryProducts(id);
				itemList = (Collection<ItemJPA>)myCatalogRemote.listAvailableItems(fromDateFormatted, toDateFormatted);
				
				if (itemList.isEmpty()) {
					result = "No available items";
				} 
				
			} catch (Exception e) {
				result = "Items unsuccessfully showed, please check data";
				e.printStackTrace();
			}
		}
	}
}
