/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import jpa.UserJPA;
import ejb.UserFacadeRemote;

/**
 * Managed Bean LoginMBean
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote userRemote;
	
	protected String result = "";
	
	protected String email = "";
	protected String password = "";
	
	public LoginMBean() throws Exception 
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

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public UserJPA getUser()
	{
		return SessionUtils.getUser();
	}
	
	public String login()
	{
		if(email.length() == 0 || password.length() == 0) {
			result = "Invalid email or password";
			return "";
		}
		try {
			UserJPA user = find(email, password);
			if(user != null) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("id", user.getId());
				session.setAttribute("email", user.getEmail());
				session.setAttribute("password", user.getPassword());
				session.setAttribute("role", user.getRole());
				SessionUtils.setUser(user);
				return "homeView";
			}
			else {
				result = "Invalid email or password";			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage();
		}
		return "";
	}
	
	public void logout()
	{
		SessionUtils.setUser(null);
		HttpSession session = SessionUtils.getSession();
		session.setAttribute("id", null);
		session.setAttribute("email", null);
		session.setAttribute("password", null);
		session.setAttribute("role", null);
	}
	
	public UserJPA find(String email, String password) throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		return userRemote.login(email, password);
	}
}
