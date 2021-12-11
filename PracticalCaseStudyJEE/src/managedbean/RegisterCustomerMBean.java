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

import jpa.CustomerJPA;
import ejb.CustomerFacadeRemote;

/**
 * Managed Bean RegisterCustomerMBean
 */
@ManagedBean(name = "registercustomer")
@SessionScoped
public class RegisterCustomerMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CustomerFacadeRemote customerRemote;	
	protected String result = "";
	
	protected String email = "";
	protected String password = "";
	protected String nif = "";
	protected String name = "";
	protected String surname = "";
	protected String phone = "";
	protected String address = "";
	
	public RegisterCustomerMBean() throws Exception 
	{
		result = "";
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public void create()
	{
		if(email.length() == 0 || password.length() == 0) {
			result = "Invalid email or password";
			return;
		}
		
		try {
			CustomerJPA customer = new CustomerJPA(email, password, nif, name, surname, phone, address);
			System.out.println("customer id::" + customer.getId());
			persist(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage();
		}
	}
	
	public void persist(CustomerJPA customer) throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		customerRemote = (CustomerFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CustomerFacadeBean!ejb.CustomerFacadeRemote");
		result = customerRemote.createCustomer(customer);
	}
}
