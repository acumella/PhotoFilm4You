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
 * Managed Bean ListAllCatalog
 */
@ManagedBean(name = "listallcatalog")
@SessionScoped
public class ListAllCatalogMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	String name;
	
	private Collection<String> catalogList;
	private Collection<ProductJPA> productsList;
	private Collection<CategoryJPA> categoriesList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer BrandJPA instances that the user can see on a screen
	protected Collection<String> catalogListView;
	//stores the total number of instances of BrandJPA
	protected int numberCatalog = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllCatalogMBean() throws Exception
	{
		catalogListView = new ArrayList<String>();
		productsList = new ArrayList<ProductJPA>();
		categoriesList = new ArrayList<CategoryJPA>();
		this.allCatalogList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less BrandJPA according screen 
	 * where the user is.
	 * @return Collection BrandJPA
	 */
	public Collection<String> getCatalogListView() {
		try {
			allCatalogList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int n =0;
		catalogListView = new ArrayList<String>();
		for (Iterator<String> iter2 = catalogList.iterator(); iter2.hasNext();)
		{
			String catalog2 = (String) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{	
				this.catalogListView.add(catalog2);
			}
			n +=1;
		}
		this.numberCatalog = n;
		return catalogListView;
	}

	public void setCatalogListView(Collection<String> catalogListView) {
		this.catalogListView = catalogListView;
	}

	/**
	 * Returns the total number of instances of BrandJPA
	 * @return Brand number
	 */
	public int getNumberCatalog()
	{ 
		return this.numberCatalog;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < catalogList.size()))
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
	 * Get/set the Brand name
	 * @return Brand name
	 */
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
		try {
			allCatalogList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all BrandJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allCatalogList() throws Exception
	{	
		catalogList = new ArrayList<String>();
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		productsList = (Collection<ProductJPA>)myCatalogRemote.listAllProducts();
		categoriesList = (Collection<CategoryJPA>)myCatalogRemote.listAllCategories();
		
		//Add products
		for (Iterator<ProductJPA> iter3 = productsList.iterator(); iter3.hasNext();)
		{
			ProductJPA product2 = (ProductJPA) iter3.next();
			String id = String.valueOf(product2.getId());
			String name = product2.getName();
			this.catalogList.add("Product with id " + id + " and name " + name);
		}
		
		//Add Categories
		for (Iterator<CategoryJPA> iter4 = categoriesList.iterator(); iter4.hasNext();)
		{
			CategoryJPA category2 = (CategoryJPA) iter4.next();
			String id = String.valueOf(category2.getId());
			String name = category2.getName();
			this.catalogList.add("Category with id " + id + " and name " + name);
		}
	}
	
	public String updateList()
	{
		try {
			allCatalogList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllCatalogView";
	}
}
