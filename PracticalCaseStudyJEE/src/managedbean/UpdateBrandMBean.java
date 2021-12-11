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
@ManagedBean(name = "updatebrand")
@SessionScoped
public class UpdateBrandMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CatalogFacadeRemote catalogFacadeRemote;
	//stores BrandJPA instance
	protected BrandJPA brand;
	//stores BrandJPA number id
	protected int brandId = 1;
	
	protected String newName;
	protected String result ="";
	
	public UpdateBrandMBean() throws Exception 
	{
		setDataBrand();
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
		result = "UpdateBrandMBean called, brandId is " + brandId;
		setDataBrand();
	}
	
	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public BrandJPA getDataBrand()	{
		return brand;
	}	
	
	public void setDataBrand() throws Exception	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		catalogFacadeRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		brand = (BrandJPA) catalogFacadeRemote.getBrandById(brandId);
	}
	
	public void updateBrand() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		catalogFacadeRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		catalogFacadeRemote.updateBrand(brandId, newName);
		result = "Brand successfully updated";
	}
}
