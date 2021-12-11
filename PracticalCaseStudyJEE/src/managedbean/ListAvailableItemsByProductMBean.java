package managedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@ManagedBean(name = "listAvailableItemsByProduct")
@SessionScoped
public class ListAvailableItemsByProductMBean implements Serializable{
	
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
	protected int idProduct = -1;
	protected String fromDate = "";
	protected String toDate = "";
	protected String result;

	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAvailableItemsByProductMBean() throws Exception
	{
		result = "ListAvailableItemsByProductMBean called";
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
	}
	
	public int getIdProduct() {
		return this.idProduct;
	}
	
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
		retrieveItems();
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
	
	public String getResult() {
		return this.result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	@SuppressWarnings("unchecked")
	void retrieveItems()
	{
		System.out.println("[retrieveItems] idProduct::" + idProduct);
		System.out.println("[retrieveItems] fromDate::" + fromDate);
		System.out.println("[retrieveItems] toDate::" + toDate);
		
		if(idProduct != -1 && fromDate != "" && toDate != "") {
			Date fromDateFormatted = null, toDateFormatted = null; 
			try {
				fromDateFormatted = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);		
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				result = "format of from date invalid";
			}
			try {
				toDateFormatted = new SimpleDateFormat("dd-MM-yyyy").parse(toDate);		
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
				itemList = (Collection<ItemJPA>)myCatalogRemote.listProductAvailableItems(idProduct, fromDateFormatted, toDateFormatted);
				
				if (itemList.isEmpty()) {
					result = "No available items";
				} 
				
			} catch (Exception e) {
				result = "Items unsuccessfully showed, please check data";
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less CategoryJPA according screen 
	 * where the user is.
	 * @return Collection CategoryJPA
	 */
	public Collection<ItemJPA> getItemsListView() {
		/*try {
			this.allItemsByProductList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		int n =0;
		itemsListView = new ArrayList<ItemJPA>();
		if(itemList == null) return itemsListView;
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
}
