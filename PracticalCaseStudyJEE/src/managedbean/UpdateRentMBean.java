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
 * Managed Bean UpdateRentMBean
 */
@ManagedBean(name = "rentUpdateStatus")
@SessionScoped
public class UpdateRentMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private RentFacadeRemote rentRemote;
	//stores RentJPA instance
	protected RentJPA dataRent;
	//stores RentJPA number id
	protected String idRent = "";
	protected Collection<rent_status> statusList;
	protected rent_status currentStatus = rent_status.Confirmed;
	
	public UpdateRentMBean() throws Exception 
	{
		statusList = new ArrayList<rent_status>();
		statusList.add(rent_status.Confirmed);
		statusList.add(rent_status.NotConfirmed);
		statusList.add(rent_status.Cancelled);
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
	
	public rent_status getCurrentStatus()
	{
		return this.currentStatus;
	}
	public void setCurrentStatus(rent_status status)
	{
		this.currentStatus = status;
		dataRent.setStatus(status.ordinal());
	}
	
	public RentJPA getDataRent()
	{
		return dataRent;
	}	
	public void setDataRent() throws Exception
	{	
		System.out.println("setDataRent::");
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		dataRent = (RentJPA) rentRemote.showRent(idRent);
		System.out.println("dataRent status::" + dataRent.getStatus());
		currentStatus = rent_status.values()[dataRent.getStatus()];
		System.out.println("dataRent currentStatus::" + currentStatus);
	}
	
	public int getStatus()
	{
		return this.dataRent.getStatus();
	}
	public void setStatus(int status)
	{
		System.out.println("setStatus::" + status);
		this.dataRent.setStatus(status);
	}
	public void statusValueChanged(ValueChangeEvent statusChanged) {
		//int status = (Integer) statusChanged.getNewValue();
		System.out.println("statusValueChanged::");
		String status = statusChanged.getNewValue().toString();
		System.out.println("status::" + status);
		this.currentStatus = (rent_status)statusChanged.getNewValue();
		setStatus(rent_status.valueOf(status).ordinal());
	}
	public Collection<rent_status> getStatusList()
	{
		return this.statusList;
	}
	
	public String updateRentStatus() throws Exception
	{
		System.out.println("updateRentStatus::");
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rentRemote = (RentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/RentFacadeBean!ejb.RentFacadeRemote");
		rentRemote.updateRent(dataRent);
		setDataRent();
		return "updateRentStatusView";
	}
}
