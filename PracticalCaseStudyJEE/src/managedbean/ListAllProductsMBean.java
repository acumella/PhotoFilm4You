package managedbean;

import java.io.Serializable;
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
 * Managed Bean ListAllProducts
 */
@ManagedBean(name = "listallproducts")
@SessionScoped
public class ListAllProductsMBean implements Serializable{
	
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
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllProductsMBean() throws Exception
	{
		this.allProductsList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less CategoryJPA according screen 
	 * where the user is.
	 * @return Collection CategoryJPA
	 */
	public Collection<ProductJPA> getProductsListView() {
		try {
			this.allProductsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
		try {
			allProductsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all CategoryJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allProductsList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		productsList = (Collection<ProductJPA>)myCatalogRemote.listAllProducts();	
	}
	
	public String updateList()
	{
		try {
			allProductsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllProductsView";
	}
}
