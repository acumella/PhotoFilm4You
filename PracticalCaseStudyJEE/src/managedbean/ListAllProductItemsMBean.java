package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.BrandJPA;
import jpa.CategoryJPA;
import jpa.ItemJPA;
import jpa.ProductJPA;
import ejb.CatalogFacadeRemote;


/**
 * Managed Bean ListAllProductsbycategory
 */
@ManagedBean(name = "listallproductitems")
@SessionScoped
public class ListAllProductItemsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	String name;
	
	private Collection<ItemJPA> itemList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer ProductJPA instances that the user can see on a screen
	protected Collection<ItemJPA> itemsListView;
	//stores the total number of instances of BrandJPA
	protected int numberItems = 0;
	protected int id;
	protected String result;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getResult() {
		return this.result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllProductItemsMBean() throws Exception
	{
		this.allItemsByProductList();
		result = "ListAllProductItemsMBean called";
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less CategoryJPA according screen 
	 * where the user is.
	 * @return Collection CategoryJPA
	 */
	public Collection<ItemJPA> getItemsListView() {
		try {
			this.allItemsByProductList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int n =0;
		itemsListView = new ArrayList<ItemJPA>();
		for (Iterator<ItemJPA> iter2 = itemList.iterator(); iter2.hasNext();)
		{
			ItemJPA i2 = (ItemJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.itemsListView.add(i2);
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
	 * Returns the total number of instances of ProductJPA
	 * @return Products number
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
	
	/**
	 * Get/set the Product name
	 * @return Product name
	 */
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
		try {
			allItemsByProductList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all CategoryJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void allItemsByProductList() throws Exception
	{	
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			screen = 0;
			myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
			System.out.println("aqui");
			itemList = (Collection<ItemJPA>)myCatalogRemote.listProductsItems(this.id);
			
			if (itemList.isEmpty()) {
				result = "Verify product id, seems to be wrong";
			} else {
				result = "Items successfully showed!";
			}
			
		} catch (Exception e) {
			result = "Items unsuccessfully showed, please check product id";
			e.printStackTrace();
		}

	}
	
	public String updateList()
	{
		try {
			allItemsByProductList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllProductItemsView";
	}
}
