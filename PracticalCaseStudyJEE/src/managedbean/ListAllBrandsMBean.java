package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.BrandJPA;
import ejb.CatalogFacadeRemote;


/**
 * Managed Bean ListAllBrands
 */
@ManagedBean(name = "listallbrands")
@SessionScoped
public class ListAllBrandsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	String name;
	
	private Collection<BrandJPA> brandsList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer BrandJPA instances that the user can see on a screen
	protected Collection<BrandJPA> brandsListView;
	//stores the total number of instances of BrandJPA
	protected int numberBrands = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllBrandsMBean() throws Exception
	{
		this.allBrandsList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less BrandJPA according screen 
	 * where the user is.
	 * @return Collection BrandJPA
	 */
	public Collection<BrandJPA> getBrandsListView() {
		try {
			allBrandsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int n =0;
		brandsListView = new ArrayList<BrandJPA>();
		for (Iterator<BrandJPA> iter2 = brandsList.iterator(); iter2.hasNext();)
		{
			BrandJPA brand2 = (BrandJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.brandsListView.add(brand2);
			}
			n +=1;
		}
		this.numberBrands = n;
		return brandsListView;
	}

	public void setBrandsListView(Collection<BrandJPA> brandsListView) {
		this.brandsListView = brandsListView;
	}

	/**
	 * Returns the total number of instances of BrandJPA
	 * @return Brand number
	 */
	public int getNumberBrands()
	{ 
		return this.numberBrands;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < brandsList.size()))
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
			allBrandsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all BrandJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allBrandsList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		brandsList = (Collection<BrandJPA>)myCatalogRemote.listAllBrands();
	}
	
	public String updateList()
	{
		try {
			allBrandsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllBrandsView";
	}
}
