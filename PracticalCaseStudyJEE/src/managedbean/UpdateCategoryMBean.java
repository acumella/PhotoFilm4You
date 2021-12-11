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
 * Managed Bean UpdateProductMBean
 */
@ManagedBean(name = "updatecategory")
@SessionScoped
public class UpdateCategoryMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	protected CategoryJPA updateCategory;
	public String result = "No result";
	public String name;
	public int id;
	public int parentCategory;
	
	public UpdateCategoryMBean() throws Exception
	{
		updateCategory = new CategoryJPA();
		name = "";
		id = 0;
		parentCategory = 0;
		result = "UpdateCategoryMBean called";
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
	
	public int getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(int parentCategory) {
		this.parentCategory = parentCategory;
	}
	

		
	
	public void updateCategory() throws Exception
	{	
		try {
			if(id != 0) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				result = myCatalogRemote.updateCategory(this.id, this.name, this.parentCategory);
				
			} else {
				result = "Category null integer";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
