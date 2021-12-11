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
 * Managed Bean DeleteItemMBean
 */
@ManagedBean(name = "deleteitem")
@SessionScoped
public class DeleteItemMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CatalogFacadeRemote myCatalogRemote;

	protected String result;
	protected String serialNumber;

	
	public DeleteItemMBean() throws Exception 
	{
		result = "DeleteItemMBean called";
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
	

	public void deleteItem() throws Exception
	{	
		try {
			if(this.serialNumber != null ) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				myCatalogRemote.deleteItem(this.serialNumber);
				result = "Item successfully delete!";
				
			} else {
				result = "Item null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
}
}
