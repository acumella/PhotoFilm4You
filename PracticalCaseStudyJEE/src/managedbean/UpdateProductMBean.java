package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.BrandJPA;
import jpa.CategoryJPA;
import jpa.ModelJPA;
import jpa.ProductJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean UpdateProductMBean
 */
@ManagedBean(name = "updateproduct")
@SessionScoped
public class UpdateProductMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	protected ProductJPA newProduct;

	protected int id;
	protected String categoryName;
	protected String name;
	protected String brandName;
	protected String modelName;
	protected int dailyPrice;
	protected int availableItems;
	protected String description;
	
	protected List<String> categoryNames = new ArrayList<String>();
	protected List<String> brandNames = new ArrayList<String>();
	protected List<String> modelNames = new ArrayList<String>();

	
	public String result = "No result";
	
	public UpdateProductMBean() throws Exception
	{
		newProduct = new ProductJPA();
		name = null;
		description = null;
		dailyPrice = 0;
		result = "UpdateProductMBean called";
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public int getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(int dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	
	public int getAvailableItems() {
		return availableItems;
	}

	public void setAvailableItems(int availableItems) {
		this.availableItems = availableItems;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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
	public List<String> getBrandNames() {
		brandNames.clear();
		brandNames.add("None");
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
	public List<String> getModelNames() {
		modelNames.clear();
		modelNames.add("None");
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
			Collection<ModelJPA> modelsList = (Collection<ModelJPA>)myCatalogRemote.listAllModels();
			for (Iterator<ModelJPA> iter2 = modelsList.iterator(); iter2.hasNext();)
			{
				ModelJPA model2 = (ModelJPA) iter2.next();
				modelNames.add(model2.getName());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return modelNames;
	}
	
	public void setModelNames(List<String> modelNames) {
		this.modelNames = modelNames;
	}

	@SuppressWarnings("unchecked")
	public void updateProduct() throws Exception
	{	
		try {
			if(name != null) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				int categoryId = -1;
				Collection<CategoryJPA> categoriesList = (Collection<CategoryJPA>)myCatalogRemote.listAllCategories();
				for (Iterator<CategoryJPA> iter2 = categoriesList.iterator(); iter2.hasNext();)
				{
					CategoryJPA category2 = (CategoryJPA) iter2.next();
					if(category2.getName().equalsIgnoreCase(categoryName)) {
						categoryId = category2.getId();
						break;
					}
				}
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
				int modelId = 0;
				Collection<ModelJPA> modelsList = (Collection<ModelJPA>)myCatalogRemote.listAllModels();
				for (Iterator<ModelJPA> iter2 = modelsList.iterator(); iter2.hasNext();)
				{
					ModelJPA model2 = (ModelJPA) iter2.next();
					if(model2.getName().equalsIgnoreCase(modelName)) {
						modelId = model2.getId();
						break;
					}
				}
				myCatalogRemote.updateProduct(this.id, this.name, this.description, categoryId, modelId, brandId,  this.dailyPrice, this.availableItems);
				result = "Product successfully updated!";
			} else {
				result = "Product null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
