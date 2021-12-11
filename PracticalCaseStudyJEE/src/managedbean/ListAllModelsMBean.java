package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.ModelJPA;
import ejb.CatalogFacadeRemote;


/**
 * Managed Bean ListAllModels
 */
@ManagedBean(name = "listallmodels")
@SessionScoped
public class ListAllModelsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	String name;
	
	private Collection<ModelJPA> modelsList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer ModelJPA instances that the user can see on a screen
	protected Collection<ModelJPA> modelsListView;
	//stores the total number of instances of ModelJPA
	protected int numberModels = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllModelsMBean() throws Exception
	{
		this.allModelsList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less ModelJPA according screen 
	 * where the user is.
	 * @return Collection ModelJPA
	 */
	public Collection<ModelJPA> getModelsListView() {
		try {
			allModelsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int n =0;
		modelsListView = new ArrayList<ModelJPA>();
		for (Iterator<ModelJPA> iter2 = modelsList.iterator(); iter2.hasNext();)
		{
			ModelJPA model2 = (ModelJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.modelsListView.add(model2);
			}
			n +=1;
		}
		this.numberModels = n;
		return modelsListView;
	}

	public void setModelsListView(Collection<ModelJPA> modelsListView) {
		this.modelsListView = modelsListView;
	}

	/**
	 * Returns the total number of instances of ModelJPA
	 * @return Model number
	 */
	public int getNumberModels()
	{ 
		return this.numberModels;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < modelsList.size()))
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
			allModelsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all ModelJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allModelsList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		modelsList = (Collection<ModelJPA>)myCatalogRemote.listAllModels();
	}
	
	public String updateList()
	{
		try {
			allModelsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllModelsView";
	}
}
