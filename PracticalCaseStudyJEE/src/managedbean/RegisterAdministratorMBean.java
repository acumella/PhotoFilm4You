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

import ejb.UserFacadeRemote;
import jpa.UserJPA;

/**
 * Managed Bean RegisterAdministratorMBean
 */
@ManagedBean(name = "registeradministrator")
@SessionScoped
public class RegisterAdministratorMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote userRemote;	
	protected String result = "";
	
	protected String email = "";
	protected String password = "";
	
	public RegisterAdministratorMBean() throws Exception 
	{
		System.out.println("RegisterAdministratorMBean");
		result = "RegisterAdministratorMBean";
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

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public void create()
	{
		if(email.length() == 0 || password.length() == 0) {
			result = "invalid email or password";
			return;
		}
		
		try {
			UserJPA user = new UserJPA(email, password, "ROLE_ADMIN");
			persist(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage();
		}
	}
	
	public void persist(UserJPA user) throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		result = userRemote.createUser(user);
	}
}
