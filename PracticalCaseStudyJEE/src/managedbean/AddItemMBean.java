package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.CatalogFacadeRemote;
import jpa.BrandJPA;
import jpa.ProductJPA;

/**
 * Managed Bean AddItemMBean
 */
@ManagedBean(name = "additem")
@SessionScoped
public class AddItemMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	protected String result;
	protected String serialNumber;
	protected String status;
	protected int productId;
	
	protected List<String> itemStatus = new ArrayList<String>();
	protected List<Integer> productsIds = new ArrayList<Integer>();
	

	public AddItemMBean() throws Exception
	{
		itemStatus.add("Operational");
		itemStatus.add("Broken");
		result = "AddItemMBean called";
	}

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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
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

	public void addNewItem() throws Exception
	{	
		try {
			if(serialNumber != null) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				result = myCatalogRemote.addItem(this.serialNumber, this.status, this.productId);
				
			} else {
				result = "Item null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}