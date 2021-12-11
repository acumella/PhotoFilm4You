/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package managedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
 * Managed Bean CreateRentMBean
 */
@ManagedBean(name = "createrent")
@SessionScoped
public class CreateRentMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private RentFacadeRemote createRentRemote;
	protected RentJPA dataRent;
	
	protected String result = "";
	protected String fromDate = "";
	protected String toDate = "";
	HttpSession session;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	
	public CreateRentMBean() throws Exception 
	{
		System.out.println("CreateRentMBean");
		result = "CreateRentMBean";
		dataRent = new RentJPA();
		session = (HttpSession) facesContext.getExternalContext().getSession(true);
	}
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public void createRent()
	{
		Date fromDateFormatted = null, toDateFormatted = null; 
		try {
			Date current = java.util.Calendar.getInstance().getTime();
			dataRent.setId(current.toString());
			fromDateFormatted = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			result = "format of from date invalid";
		}
		try {
			toDateFormatted = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			result = "format of to date invalid";
		}
		
		dataRent.setFromDate(fromDateFormatted);
		dataRent.setToDate(toDateFormatted);
		
		try {
			setDataRent();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
	}
	
	public RentJPA getDataRent()
	{
		return dataRent;
	}
	
	public void setDataRent() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		createRentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		UserFacadeRemote userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		
		String email = (String)session.getAttribute("email");
		String password = (String)session.getAttribute("password");
		UserJPA user = userRemote.login(email, password);
		result = createRentRemote.createRent(dataRent, user.getCustomer().getId());
	}
}
