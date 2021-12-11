package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.BrandJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean UpdateBrandMBean
 */
@ManagedBean(name = "deletebrand")
@SessionScoped
public class DeleteBrandMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CatalogFacadeRemote catalogFacadeRemote;

	//stores BrandJPA number id
	protected int brandId = 1;
	//stores BrandJPA name
	protected String name;
	protected String result ="";
	
	public DeleteBrandMBean() throws Exception 
	{
		System.out.println("Delete Brand actived");
	}
	
	/**
	 * Get/set the id number and BrandJPA instance
	 * @return Brand Id
	 */
	public int getBrandId()	{
		return brandId;
	}
	
	public void setBrandId(int brandId) throws Exception	{
		this.brandId = brandId;
		result = "DeleteBrandMBean called, brandId is " + brandId + " and name is " + name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		result = "DeleteBrandMBean called, name is " + name + " and brandId is " + brandId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public void deleteBrand() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		catalogFacadeRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		catalogFacadeRemote.deleteBrand(brandId, name);
		result = "Brand successfully deleted";
	}
}
