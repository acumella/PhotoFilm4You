package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.BrandJPA;
import jpa.CategoryJPA;
import ejb.CatalogFacadeRemote;


/**
 * Managed Bean ListAllBrands
 */
@ManagedBean(name = "listallcategories")
@SessionScoped
public class ListAllCategoriesMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	String name;
	
	private Collection<CategoryJPA> categoriesList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer CategoryJPA instances that the user can see on a screen
	protected Collection<CategoryJPA> categoriesListView;
	//stores the total number of instances of BrandJPA
	protected int numberCategories = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllCategoriesMBean() throws Exception
	{
		this.allCategoriesList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less CategoryJPA according screen 
	 * where the user is.
	 * @return Collection CategoryJPA
	 */
	public Collection<CategoryJPA> getCategoriesListView() {
		try {
			this.allCategoriesList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int n =0;
		categoriesListView = new ArrayList<CategoryJPA>();
		for (Iterator<CategoryJPA> iter2 = categoriesList.iterator(); iter2.hasNext();)
		{
			CategoryJPA category2 = (CategoryJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.categoriesListView.add(category2);
			}
			n +=1;
		}
		this.numberCategories = n;
		return categoriesListView;
	}

	public void setCategoriesListView(Collection<CategoryJPA> categoriesListView) {
		this.categoriesListView = categoriesListView;
	}

	/**
	 * Returns the total number of instances of CategoryJPA
	 * @return Categories number
	 */
	public int getNumberCategories()
	{ 
		return this.numberCategories;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < categoriesList.size()))
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
			allCategoriesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all CategoryJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allCategoriesList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		categoriesList = (Collection<CategoryJPA>)myCatalogRemote.listAllCategories();
	}
	
	public String updateList()
	{
		try {
			allCategoriesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllCategoriesView";
	}
}
