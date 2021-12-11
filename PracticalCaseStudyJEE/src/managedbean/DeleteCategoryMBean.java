package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.CategoryJPA;
import jpa.ProductJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean AddProductMBean
 */
@ManagedBean(name = "deletecategory")
@SessionScoped
public class DeleteCategoryMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	protected CategoryJPA delCategory;
	public String result = "No result";
	public String name;
	public int id;
	
	public DeleteCategoryMBean() throws Exception
	{
		delCategory = new CategoryJPA();
		name = null;
		id = 0;
		result = "DelCategoryMBean called";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	
	public void deleteCategory() throws Exception
	{	
		try {
			if(id != 0) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				result = myCatalogRemote.deleteCategory(this.id);
				
			} else {
				result = "Category null integer";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
