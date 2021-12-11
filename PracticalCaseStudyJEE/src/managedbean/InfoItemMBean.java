package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.BrandJPA;
import jpa.CategoryJPA;
import jpa.ItemJPA;
import jpa.ItemJPA.ItemStatus;
import jpa.ModelJPA;
import jpa.ProductJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean InfoItemMBean
 */
@ManagedBean(name = "infoitem")
@SessionScoped
public class InfoItemMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;

	protected String serialNumber;
	protected ItemStatus status;
	protected String statusView;
	protected String productName;
	protected String result;
	
		
	public InfoItemMBean() throws Exception
	{
		//setData();
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public ItemStatus getStatus() {
		return status;
	}

	public void setStatus(ItemStatus status) {
		this.status = status;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getStatusView() {
		return statusView;
	}

	public void setStatusView(String statusView) {
		this.statusView = statusView;
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public void setData() {
		this.serialNumber= "";
		this.status =  ItemStatus.OPERATIONAL;
		this.productName =  "a";
	}
	@SuppressWarnings("unchecked")
	public void infoItem() throws Exception
	{	
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
			ItemJPA info = myCatalogRemote.getProductItem(this.serialNumber);
			this.serialNumber=info.getSerialNumber(); 
			this.statusView = info.getStatus().toString();
			this.productName = info.getProduct().getName();
			result = "Information of item successfully showed!";
		} catch(Exception e) {
			e.printStackTrace();
			result = "Verify the serial number please.";
		}		
	}

}
