package managedbean;

import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ejb.UserFacadeRemote;
import jpa.UserJPA;

public class SessionUtils {
	private static UserJPA user;
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}
	
	public static String getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("userid");
		else
			return null;
	}

	public static String getRole() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		return session.getAttribute("role").toString();
	}
	
	public static void setUser(UserJPA user) {
		SessionUtils.user = user;
	}
	
	public static UserJPA getUser() {
		HttpSession session = (HttpSession) SessionUtils.getSession();
		if(user != null)
			return user;
		else if(session.getAttribute("id") != null) {
			try {
				user = SessionUtils.find((Integer) session.getAttribute("id"));
			}
			catch (Exception e){
				user = null;
			}
			if(user != null)
				return user;
		}
		return null;
	}
	
	private static UserJPA find(Integer id) throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		UserFacadeRemote userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		return userRemote.getUser(id);
	}
}
