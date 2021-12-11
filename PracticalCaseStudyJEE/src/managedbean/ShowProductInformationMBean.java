package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.ProductJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean ShowProductMBean
 */
@ManagedBean(name = "showproduct")
@SessionScoped
public class ShowProductInformationMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	protected ProductJPA product;
	public String name = "";
	public String description = "None";
	public int dailyPrice = 0;
	public int availableItems = 0;
	public float productRatting = 0.0f;
	
	public ShowProductInformationMBean() throws Exception {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public float getProductRatting() {
		return productRatting;
	}

	public void setProductRatting(float productRatting) {
		this.productRatting = productRatting;
	}
	
	public void getProductInfo() throws Exception
	{	
		try {
			clearStats();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
			//product = myCatalogRemote.getProductInformation(this.name);	
			this.description = product.getDescription();
			this.dailyPrice = product.getDailyPrice();
			this.availableItems = product.getAvailableItems();
			this.productRatting = product.getProductRatting();
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void clearStats() {
		this.description = "None";
		this.availableItems = 0;
		this.dailyPrice = 0;
		this.productRatting = 0.0f;
	}
}
