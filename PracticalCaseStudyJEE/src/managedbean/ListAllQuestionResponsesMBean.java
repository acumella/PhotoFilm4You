package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.MediaFacadeRemote;
import jpa.ProductRattingJPA;
import jpa.QuestionJPA;
import jpa.ResponseJPA;

/**
 * Managed Bean ListAllQuestionResponses
 */
@ManagedBean(name = "listallquestionresponses")
@SessionScoped
public class ListAllQuestionResponsesMBean implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected Collection<ResponseJPA> questionResponsesListView;
	protected int numberQuestionResponses  = 0;
	
	int questionId = 1;
	
	private Collection <ResponseJPA> questionResponsesList;
	private int screen = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllQuestionResponsesMBean() throws Exception{
		this.allQuestionResponsesList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less ResponseJPA according screen 
	 * where the user is.
	 * @return Collection ResponseJPA
	 */
	
	
	public Collection<ResponseJPA> getQuestionResponsesListView() {
		try {
			this.allQuestionResponsesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int n =0;
		questionResponsesListView = new ArrayList<ResponseJPA>();
		for (Iterator<ResponseJPA> iter2 = questionResponsesList.iterator(); iter2.hasNext();)
		{
			ResponseJPA response2 = (ResponseJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10)) {				
				this.questionResponsesListView.add(response2);
			}
			n +=1;
		}
		this.numberQuestionResponses = n;
		return questionResponsesListView;
	}

	public void setQuestionResponsesListView(Collection<ResponseJPA> questionResponsesListView) {
		this.questionResponsesListView = questionResponsesListView;
	}
	
	
	/**
	 * Returns the total number of instances of ResponseJPA
	 * @return Question number
	 */
	public int getNumberQuestionResponses() { 
		return this.numberQuestionResponses;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	
	public void nextScreen()
	{
		if (((screen+1)*10 < questionResponsesList.size()))
		{
			screen +=1;
		}
	}
	
	
	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}
	
	/**
	 * Get/set the Question id
	 * @return question id
	 */
	public int getQuestionId()
	{
		return this.questionId;
	}
	
	public void setQuestionId(int questionId)
	{
		this.questionId = questionId;
		try {
			allQuestionResponsesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all ResponseJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allQuestionResponsesList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");		
		questionResponsesList = (Collection<ResponseJPA>) myMediaRemote.listAllQuestionResponses(this.questionId);
	}
	
	public String updateList()
	{
		try {
			allQuestionResponsesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllProductCommentsView";
	}
	
	
	
}
