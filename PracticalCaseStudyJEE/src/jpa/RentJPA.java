/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class RentJPA
 */
@Entity
@Table(name="practicalcase.rent")
public class RentJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Date fromDate;
	private Date toDate;
	private double totalPrice;
	//private String customer;
	private CustomerJPA customer = new CustomerJPA();
	//rent_status status = rent_status.NotConfirmed;
	int status = rent_status.NotConfirmed.ordinal();
	
	/**
	 * Class constructor methods
	 */
	public RentJPA() {
		super();
		//customer.setNif("11111111A");
	}	
	public RentJPA(String id, Date fromDate, Date toDate, String customer) {		
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
		//this.customer = customer;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}	

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	/*public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}*/
	
	

	/**
	 * Methods get/set persistent relationships
	 */
	@ManyToOne
	@JoinColumn (name="customer")
	public CustomerJPA getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerJPA customer) {
		this.customer = customer;
	}

}
