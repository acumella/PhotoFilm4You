package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import jpa.RentJPA;
import jpa.UserJPA;
import ejb.RentFacadeRemote;
import ejb.UserFacadeRemote;

/**
 * Managed Bean ListMyRentsBean
 */
@ManagedBean(name = "rentList")
@SessionScoped
public class ListMyRentsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private RentFacadeRemote rentRemote;
	
	//private String customerID = "11111111A";
	//private String customerID = "ADMIN";
	private Collection<RentJPA> rentsList = new ArrayList<RentJPA>();;
	//stores the screen number where the user is 
	private int screen = 0;
	protected Collection<RentJPA> rentsListView = new ArrayList<RentJPA>();
	protected int numberRents = 0;
	protected String rent;
	HttpSession session;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	private static Boolean hasSearched;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListMyRentsBean() throws Exception
	{

		System.out.println("ListMyRentsBean::");
		session = (HttpSession) facesContext.getExternalContext().getSession(true);
		this.rentList();
		hasSearched = false;
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less RentJPA according screen 
	 * where the user is.
	 * @return Collection RentJPA
	 */
	public Collection<RentJPA> getRentsListView()
	{
		try {
			if(!hasSearched) {
				this.rentList();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int n =0;
		rentsListView = new ArrayList<RentJPA>();
		for (Iterator<RentJPA> iter2 = rentsList.iterator(); iter2.hasNext();)
		{
			RentJPA rent2 = (RentJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.rentsListView.add(rent2);
			}
			n +=1;
		}
		this.numberRents = n;
		return rentsListView;
	}
	

	public String getRent() {
		return rent;
	}
	public void setRent(String rent) {
		this.rent = rent;
	}
	
	/**
	 * @return numberRents
	 */
	public int getNumberRents()
	{ 
		return this.numberRents;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < rentsList.size()))
		{
			screen +=1;
		}
	}
	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}
		
	/**
	 * Method used for Facelet to call listRentsView Facelet
	 * @return Facelet name
	 * @throws Exception
	 */
	public String listRents() throws Exception
	{
		rentList();
		return "listRentsView";
	}
	
	/**
	 * Method used for Facelet to call showRentView Facelet
	 * @return Facelet name
	 */
	public String setShowRent()
	{
		return "showRentView";
	}
	
	/**
	 * Method that gets a list of instances by category or all RentJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void rentList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		if(session != null) {
			if(session.getAttribute("role") != null) {
				if (session.getAttribute("role").equals("ROLE_ADMIN")) {
					rentsList = (Collection<RentJPA>)rentRemote.listAllActiveRents();
				}
				else if (session.getAttribute("role").equals("ROLE_CUSTOMER")) {					
					UserFacadeRemote userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");					
					String email = (String)session.getAttribute("email");
					String password = (String)session.getAttribute("password");
					UserJPA user = userRemote.login(email, password);
					
					rentsList = (Collection<RentJPA>)rentRemote.listRentsByCustomer(user.getCustomer());
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void rentListById() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		if(session != null) {
			if(session.getAttribute("role") != null) {
				if (session.getAttribute("role").equals("ROLE_ADMIN")) {
					rentsList = (Collection<RentJPA>)rentRemote.listAllActiveRentsById(rent);
				}
				else if (session.getAttribute("role").equals("ROLE_CUSTOMER")) {					
					UserFacadeRemote userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");					
					String email = (String)session.getAttribute("email");
					String password = (String)session.getAttribute("password");
					UserJPA user = userRemote.login(email, password);
					
					rentsList = (Collection<RentJPA>)rentRemote.listRentsByCustomerAndById(user.getCustomer(), rent);
				}
			}
		}
	}
	
	public String updateList()
	{
		try {
			rentList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		hasSearched = false;
		
		return "listAllActiveRentsView";
	}
	

	public String updateListById()
	{
		try {
			rentListById();
		} catch (Exception e) {
			e.printStackTrace();
		}
		hasSearched = true;
		return "listAllActiveRentsView";
	}
}
