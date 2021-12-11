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
 * Managed Bean AddBrandMBean
 */
@ManagedBean(name = "addbrand")
@SessionScoped
public class AddBrandMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	protected BrandJPA newBrand;
	protected String name =  null;
	protected String result = "No result";
	
	public AddBrandMBean() throws Exception
	{
		newBrand = new BrandJPA();
		result = "AddBrandMBean called";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void addNewBrand() throws Exception
	{	
		try {
			if(name != null) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				result = myCatalogRemote.addBrand(this.name);
				
			} else {
				result = "Brand null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
