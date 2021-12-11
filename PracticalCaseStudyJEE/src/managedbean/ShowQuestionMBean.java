package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.QuestionJPA;
import ejb.MediaFacadeRemote;

/**
 * Managed Bean ShowProductMBean
 */
@ManagedBean(name = "showquestion")
@SessionScoped
public class ShowQuestionMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected QuestionJPA dataQuestion;
	
	public int questionId = 1;
	public String title = "None";
	public String message = "None";

	public ShowQuestionMBean() throws Exception {
		setDataQuestion();
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
		setDataQuestion();
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
	
	public QuestionJPA getDataQuestion() {
		return this.dataQuestion;
	}
	
	public void setDataQuestion() {
		try {
			clearStats();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");
			dataQuestion = (QuestionJPA) myMediaRemote.getQuestion(questionId);
			this.title = dataQuestion.getTitle();
			this.message = dataQuestion.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void clearStats() {
		this.title = "None";
		this.message = "None";
	}

}
