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
 * Managed Bean InfoProductMBean
 */
@ManagedBean(name = "infoproduct")
@SessionScoped
public class InfoProductMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;

	protected int id;
	protected String name;
	protected String description;
	protected String categoryName;
	protected String modelName;
	protected String brandName;
	protected int dailyPrice;
	protected int availableItems;
	protected String result;
	
		
	public InfoProductMBean() throws Exception
	{
		name = "";
		description = "";
		categoryName = "";
		modelName = "";
		brandName = "";
		dailyPrice = 0;
		availableItems = 0;
		result = "InfoProductMBean called";
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
	public void infoProduct() throws Exception
	{	
		try {
			if(id >= 1) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				ProductJPA info = myCatalogRemote.getProductInformation(id);
				this.name=info.getName();
				this.description = info.getDescription();
				this.categoryName = info.getCategory().getName();
				this.modelName = info.getModel().getName();
				this.brandName = info.getBrand().getName();
				this.dailyPrice = info.getDailyPrice();
				this.availableItems = info.getAvailableItems();
				result = "Information of product successfully showed!";
			} else {
				result = "Product id incorrect";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
