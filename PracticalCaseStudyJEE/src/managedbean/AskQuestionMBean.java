package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import jpa.QuestionJPA;
import jpa.UserJPA;
import ejb.MediaFacadeRemote;
import ejb.UserFacadeRemote;


/**
 * Managed Bean AskQuestionMBean
 */
@ManagedBean(name = "askquestion")
@SessionScoped
public class AskQuestionMBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected QuestionJPA newQuestion;
	public String result = "No result";
	
	public String title =  null;
	public String message =  null;
	
	public AskQuestionMBean() throws Exception
	{
		newQuestion = new QuestionJPA();
		result = "AskQuestionMBean called";
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void askNewQuestion() throws Exception
	{	
		try {
			if(title != null) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");			
				result = myMediaRemote.askQuestion(this.title,this.message);
	
			} else {
				result = "Title null string";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}



}
