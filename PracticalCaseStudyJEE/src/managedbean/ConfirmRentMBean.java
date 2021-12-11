/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jpa.CustomerJPA;
import jpa.RentJPA;
import jpa.rent_status;
import ejb.CatalogFacadeRemote;
import ejb.RentFacadeRemote;

/**
 * Managed Bean ConfirmRentMBean
 */
@ManagedBean(name = "confirmRent")
@SessionScoped
public class ConfirmRentMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private RentFacadeRemote rentRemote;
	//stores RentJPA instance
	protected RentJPA dataRent;
	//stores RentJPA number id
	protected String idRent = "";
	
	public ConfirmRentMBean() throws Exception 
	{
		//setDataRent();
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
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		dataRent = (RentJPA) rentRemote.showRent(idRent);
	}
	
	public void confirmRent() throws Exception
	{
		dataRent.setStatus(rent_status.Confirmed.ordinal());
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		rentRemote.updateRent(dataRent);
		//rentRemote.createReservation(dataRent);
		setDataRent();
	}
}
