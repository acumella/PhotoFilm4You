package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.ResponseJPA;
import ejb.MediaFacadeRemote;


/**
 * Managed Bean AnswerQuestionMBean
 */
@ManagedBean(name = "answerquestion")
@SessionScoped
public class AnswerQuestionMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected ResponseJPA newResponse;
	public String result = "";
	
	public int questionId =  1;
	public String response =  null;
	
	public AnswerQuestionMBean() throws Exception
	{
		newResponse = new ResponseJPA();
		result = "";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	public void askNewQuestion() throws Exception
	{	
		try {
			if(questionId != 0) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");
				result = myMediaRemote.answerQuestion(this.questionId,this.response);	
			} else {
				result = "Question does not exist";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
