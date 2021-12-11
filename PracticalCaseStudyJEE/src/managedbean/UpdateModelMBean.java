package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.ModelJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean UpdateModelMBean
 */
@ManagedBean(name = "updatemodel")
@SessionScoped
public class UpdateModelMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CatalogFacadeRemote catalogFacadeRemote;
	//stores ModelJPA instance
	protected ModelJPA model;
	//stores ModelJPA number id
	protected int modelId = 1;
	//stores ModelJPA name
	protected String newName;
	//stores ModelJPA brand
	protected String newBrand;
	protected String result ="";
	
	public UpdateModelMBean() throws Exception 
	{
		setDataModel();
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
		result = "UpdateModelMBean called, modelId is " + modelId;
		setDataModel();
	}
	
	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
	
	public String getNewBrand() {
		return newBrand;
	}

	public void setNewBrand(String newBrand) {
		this.newBrand = newBrand;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ModelJPA getDataModel()	{
		return model;
	}	
	
	public void setDataModel() throws Exception	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		catalogFacadeRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		model = (ModelJPA) catalogFacadeRemote.getModelById(modelId);
	}
	
	public void updateModel() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		catalogFacadeRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		catalogFacadeRemote.updateModel(modelId, newName);
		result = "Model successfully updated";
	}
}
