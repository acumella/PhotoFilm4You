package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import jpa.BrandJPA;
import jpa.CategoryJPA;
import jpa.ItemJPA;
import jpa.ModelJPA;
import jpa.ProductJPA;
import jpa.RentJPA;
import jpa.UserJPA;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ejb.CatalogFacadeRemote;
import ejb.RentFacadeRemote;
import ejb.UserFacadeRemote;

/**
 * Managed Bean AddProductMBean
 */
@ManagedBean(name = "addItemToRent")
@SessionScoped
public class AddItemToRentMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote myCatalogRemote;
	private RentFacadeRemote myRentRemote;
	private UserFacadeRemote myUserRemote;
	
	protected String rentSelected;
	protected String itemId;
	protected ItemJPA dataItem;	
	protected List<String> rents = new ArrayList<String>();

	
	public String result = "No result";
	
	public AddItemToRentMBean() throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		myCatalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		myRentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		myUserRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");	
		
	}
	
	public String getRentSelected() {
		return rentSelected;
	}

	public void setRentSelected(String rentSelected) {
		this.rentSelected = rentSelected;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
		showItem();
	}	

	public ItemJPA getDataItem() {
		return dataItem;
	}

	public void setDataItem(ItemJPA dataItem) {
		this.dataItem = dataItem;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> getRents() 
	{
		System.out.println("::getRents::");
		rents.clear();
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
			if(session != null) {
				String email = (String)session.getAttribute("email");
				String password = (String)session.getAttribute("password");
				UserJPA user = myUserRemote.login(email, password);
				
				Collection<RentJPA> rentList = (Collection<RentJPA>) myRentRemote.listRentsByCustomer(user.getCustomer());
				for (Iterator<RentJPA> iter2 = rentList.iterator(); iter2.hasNext();)
				{
					RentJPA currentRent = (RentJPA) iter2.next();
					rents.add(currentRent.getId());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rents;
	}
	
	public void setRents(List<String> rents) {
		this.rents = rents;
	}
	
	public void showItem()
	{
		dataItem = myCatalogRemote.getProductItem(this.itemId);
	}

	public String addItemToRent() throws Exception
	{	
		try {
			if(rentSelected != null && !rentSelected.equals("")) {
				myRentRemote.addItemToRent(dataItem.getSerialNumber(), rentSelected);
				
			} else {
				result = "Rent null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
		
		return "listAvailableItemsBetweenDatesView";
	}

}
