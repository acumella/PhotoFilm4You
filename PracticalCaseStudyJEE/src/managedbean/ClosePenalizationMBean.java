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

import jpa.CancellationJPA;
import jpa.CustomerJPA;
import jpa.penalization_status;
import jpa.CancellationJPA;
import jpa.rent_status;
import ejb.CatalogFacadeRemote;
import ejb.RentFacadeRemote;

/**
 * Managed Bean ClosePenalizationMBean
 */
@ManagedBean(name = "closePenalization")
@SessionScoped
public class ClosePenalizationMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private RentFacadeRemote rentRemote;
	//stores CancellationJPA instance
	protected CancellationJPA dataCancellation;
	//stores CancellationJPA number id
	protected String idRent = "";
	
	
	public ClosePenalizationMBean() throws Exception 
	{
		//setDataCancellation();
	}
	
	/**
	 * Get/set the id number and CancellationJPA instance
	 * @return Rent Id
	 */
	public String getIdRent()
	{
		return idRent;
	}
	public void setIdRent(String idRent) throws Exception
	{
		this.idRent = idRent;
		setDataCancellation();
	}
		
	public CancellationJPA getDataCancellation()
	{
		return dataCancellation;
	}	
	public void setDataCancellation() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		dataCancellation = (CancellationJPA) rentRemote.showCancellation(idRent);		
	}
	
	public String closeCancellation() throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		dataCancellation.setPenalizationStatus(penalization_status.Paid.ordinal());
		rentRemote.closeCancellation(dataCancellation);
		setDataCancellation();
		return "listPendingPenalizationsView";
	}
}
