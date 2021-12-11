package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.BrandJPA;
import jpa.ModelJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean AddModelMBean
 */
@ManagedBean(name = "addmodel")
@SessionScoped
public class AddModelMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	protected ModelJPA newModel;
	protected String result = "No result";
	protected String name =  null;
	protected String brandName = null;
	protected List<String> brandNames = new ArrayList<String>();
	
	public AddModelMBean() throws Exception
	{
		newModel = new ModelJPA();
		result = "AddModelMBean called";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@SuppressWarnings("unchecked")
	public List<String> getBrandNames() {
		brandNames.clear();
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
			Collection<BrandJPA> brandsList = (Collection<BrandJPA>)myCatalogRemote.listAllBrands();
			for (Iterator<BrandJPA> iter2 = brandsList.iterator(); iter2.hasNext();)
			{
				BrandJPA brand2 = (BrandJPA) iter2.next();
				brandNames.add(brand2.getName());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return brandNames;
	}
	
	public void setBrandNames(List<String> brandNames) {
		this.brandNames = brandNames;
	}

	@SuppressWarnings("unchecked")
	public void addNewModel() throws Exception
	{	
		try {
			if(name != null) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				int brandId = 0;
				Collection<BrandJPA> brandsList = (Collection<BrandJPA>)myCatalogRemote.listAllBrands();
				for (Iterator<BrandJPA> iter2 = brandsList.iterator(); iter2.hasNext();)
				{
					BrandJPA brand2 = (BrandJPA) iter2.next();
					if(brand2.getName().equalsIgnoreCase(brandName)) {
						brandId = brand2.getId();
						break;
					}
				}
				result = myCatalogRemote.addModel(this.name, brandId);
				
			} else {
				result = "Model null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
