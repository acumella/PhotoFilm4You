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
@ManagedBean(name = "deletemodel")
@SessionScoped
public class DeleteModelMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CatalogFacadeRemote catalogFacadeRemote;

	//stores modelJPA number id
	protected int modelId = 1;
	//stores result
	protected String result ="";
	
	public DeleteModelMBean() throws Exception 
	{
		
	}
	
	/**
	 * Get/set the id number and ModelJPA instance
	 * @return Model Id
	 */
	public int getModelId()	{
		return modelId;
	}
	
	public void setModelId(int modelId) throws Exception	{
		this.modelId = modelId;
		result = "DeleteModelMBean called, modelId is " + modelId;
	}
	

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public void deleteModel() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		catalogFacadeRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		catalogFacadeRemote.deleteModel(modelId);
		result = "Model successfully deleted";
	}
}
