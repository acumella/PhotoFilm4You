package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.ItemJPA.ItemStatus;
import jpa.ProductJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean UpdateItemMBean
 */
@ManagedBean(name = "updateitem")
@SessionScoped
public class UpdateItemMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CatalogFacadeRemote myCatalogRemote;

	protected String result;
	protected String serialNumber;
	protected String status;
	protected Integer productId;
	
	protected List<String> itemStatus = new ArrayList<String>();
	protected List<Integer> productsIds = new ArrayList<Integer>();
	
	public UpdateItemMBean() throws Exception 
	{
		itemStatus.add("Operational");
		itemStatus.add("Broken");
		result = "UpdateItemMBean called";
	}
	
	/**
	 * Get/set the id number and UpdateJPA instance
	 * @return Update Id
	 */
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String name) {
		this.serialNumber = name;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	
	public List<String> getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(List<String> itemStatus) {
		this.itemStatus = itemStatus;
	}
	
	public void setProductsIds(List<Integer> productsIds) {
		this.productsIds = productsIds;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getProductsIds() {
		productsIds.clear();
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
			Collection<ProductJPA> productsList = (Collection<ProductJPA>)myCatalogRemote.listAllProducts();
			for (Iterator<ProductJPA> iter2 = productsList.iterator(); iter2.hasNext();)
			{
				ProductJPA product2 = (ProductJPA) iter2.next();
				productsIds.add(product2.getId());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return productsIds;
	}

	public void updateItem() throws Exception
	{	
		try {
			if(this.serialNumber != null ) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				result = myCatalogRemote.updateProductItem(this.serialNumber, this.status, this.productId);
				result = "Item successfully updated!";
				
			} else {
				result = "Item null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
}
}
