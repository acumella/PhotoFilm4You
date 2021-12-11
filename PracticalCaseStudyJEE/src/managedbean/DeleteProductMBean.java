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
 * Managed Bean AddProductMBean
 */
@ManagedBean(name = "deleteproduct")
@SessionScoped
public class DeleteProductMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	
	protected String result;
	protected int productId;
	
	public DeleteProductMBean() throws Exception
	{
		productId = 0;
		result = "DelProductMBean called";
	}

	public int getProductId() {
		System.out.println("gettting id");
		return productId;
	}

	public void setProductId(int productId) {
		System.out.println("configured id");
		this.productId = productId;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
			
	
	public void deleteProduct() throws Exception
	{	
		try {
			if(productId != 0) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
				myCatalogRemote.deleteProduct(this.productId);
				result = "Product successfully deleted!";
				
			} else {
				result = "Product null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
