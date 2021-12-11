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
import jpa.ProductJPA;
import ejb.CatalogFacadeRemote;


/**
 * Managed Bean ListAllProducts by available items
 */
@ManagedBean(name = "listallproductsavailableitems")
@SessionScoped
public class ListAllProductsAvailableItemsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	String name;
	
	private Collection<ProductJPA> productsList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer ProductJPA instances that the user can see on a screen
	protected Collection<ProductJPA> productsListView;
	//stores the total number of instances of BrandJPA
	protected int numberProducts = 0;
	protected int id;
	protected String result;
	protected String fromDate;
	protected String toDate;
	
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
	public ListAllProductsAvailableItemsMBean() throws Exception
	{
		this.allProductsByAvailableItemsList();
		result = "ListAllProductsAvailableItemsMBean called";
	}
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less CategoryJPA according screen 
	 * where the user is.
	 * @return Collection CategoryJPA
	 */
	public Collection<ProductJPA> getProductsListView() {
		/*try {
			this.allProductsByAvailableItemsList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("::getProductsListView::");
		int n =0;
		productsListView = new ArrayList<ProductJPA>();
		for (Iterator<ProductJPA> iter2 = productsList.iterator(); iter2.hasNext();)
		{
			ProductJPA product2 = (ProductJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.productsListView.add(product2);
			}
			n +=1;
		}
		this.numberProducts = n;
		return productsListView;
	}

	public void setProductsListView(Collection<ProductJPA> productsListView) {
		this.productsListView = productsListView;
	}

	/**
	 * Returns the total number of instances of ProductJPA
	 * @return Products number
	 */
	public int getNumberProducts()
	{ 
		return this.numberProducts;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < productsList.size()))
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
	/*public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
		try {
			allProductsByAvailableItemsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Method that gets a list of instances by category or all CategoryJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void allProductsByAvailableItemsList() throws Exception
	{	
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			screen = 0;
			myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
			productsList = (Collection<ProductJPA>)myCatalogRemote.listCategoryProducts(id);
			
			if (productsList.isEmpty()) {
				result = "Not products with available items";
			} 
			
		} catch (Exception e) {
			result = "Products unsuccessfully showed, please check data";
			e.printStackTrace();
		}

	}	
	
	public String updateList()
	{
		try {
			allProductsByAvailableItemsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllProductAvailableItemsView";
	}
	
	/**
	 * Method that gets a list of instances by category or all CategoryJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void listProductsWithAvailableItemsBetweenDates() throws Exception
	{	
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
			productsList = (Collection<ProductJPA>)myCatalogRemote.listProductAvailableItems(fromDateFormatted, toDateFormatted);
			System.out.println("productsList:::" + productsList.size());
			
			if (productsList.isEmpty()) {
				result = "Not products with available items";
			} 
			
		} catch (Exception e) {
			result = "Products unsuccessfully showed, please check data";
			e.printStackTrace();
		}
	}
	
	public void search()
	{
		try {
			listProductsWithAvailableItemsBetweenDates();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return "listAllProductAvailableItemsView";
	}
}
