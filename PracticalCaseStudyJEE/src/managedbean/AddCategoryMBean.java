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
 * Managed Bean AddCategoryMBean
 */
@ManagedBean(name = "addcategory")
@SessionScoped
public class AddCategoryMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	protected CategoryJPA newCategory;
	protected String result = "No result";
	protected String name =  null;
	public String categoryName = null;
	public List<String> categoryNames = new ArrayList<String>();
	
	
	public AddCategoryMBean() throws Exception
	{
		newCategory = new CategoryJPA();
		result = "AddCategoryMBean called";
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
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@SuppressWarnings("unchecked")
	public List<String> getCategoryNames() {
		categoryNames.clear();
		categoryNames.add("None");
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
			Collection<CategoryJPA> categoryList = (Collection<CategoryJPA>)myCatalogRemote.listAllCategories();
			for (Iterator<CategoryJPA> iter2 = categoryList.iterator(); iter2.hasNext();)
			{
				CategoryJPA category2 = (CategoryJPA) iter2.next();
				categoryNames.add(category2.getName());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return categoryNames;
	}
	
	public void setCategoryNames(List<String> categoryNames) {
		this.categoryNames = categoryNames;
	}

	@SuppressWarnings("unchecked")
	public void addNewCategory() throws Exception
	{	
		try {
			if(name != null) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				int parentCategoryId = -1;
				Collection<CategoryJPA> categoriesList = (Collection<CategoryJPA>)myCatalogRemote.listAllCategories();
				for (Iterator<CategoryJPA> iter2 = categoriesList.iterator(); iter2.hasNext();)
				{
					CategoryJPA category2 = (CategoryJPA) iter2.next();
					if(category2.getName().equalsIgnoreCase(categoryName)) {
						parentCategoryId = category2.getId();
						break;
					}
				}
				result = myCatalogRemote.addCategory(this.name, parentCategoryId);
				
			} else {
				result = "Category null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
