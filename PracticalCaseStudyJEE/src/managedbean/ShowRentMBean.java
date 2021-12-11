/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.RentJPA;
import ejb.CatalogFacadeRemote;
import ejb.RentFacadeRemote;

/**
 * Managed Bean ShowRentMBean
 */
@ManagedBean(name = "rentshow")
@SessionScoped
public class ShowRentMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private RentFacadeRemote showRentRemote;
	//stores RentJPA instance
	protected RentJPA dataRent;
	//stores RentJPA number id
	protected String idRent = "";
	
	public ShowRentMBean() throws Exception 
	{
		setDataRent();
	}
	
	/**
	 * Get/set the id number and RentJPA instance
	 * @return Rent Id
	 */
	public String getIdRent()
	{
		return idRent;
	}
	public void setIdRent(String idRent) throws Exception
	{
		this.idRent = idRent;
		setDataRent();
	}
	public RentJPA getDataRent()
	{
		return dataRent;
	}	
	public void setDataRent() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		showRentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		dataRent = (RentJPA) showRentRemote.showRent(idRent);
	}
}
