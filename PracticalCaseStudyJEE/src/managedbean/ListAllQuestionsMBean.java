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
 * Managed Bean ListAllQuestions
 */
@ManagedBean(name = "listallquestions")
@SessionScoped
public class ListAllQuestionsMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected Collection<QuestionJPA> questionsListView;
	protected int numberQuestions  = 0;
	
	private Collection <QuestionJPA> questionsList;
	private int screen = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllQuestionsMBean() throws Exception
	{
		this.allQuestionsList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less QuestionJPA according screen 
	 * where the user is.
	 * @return Collection QuestionJPA
	 */
	public Collection<QuestionJPA> getQuestionsListView() 
	{
		updateList();
		int n =0;
		questionsListView = new ArrayList<QuestionJPA>();
		for (Iterator<QuestionJPA> iter2 = questionsList.iterator(); iter2.hasNext();)
		{
			QuestionJPA question2 = (QuestionJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.questionsListView.add(question2);
			}
			n +=1;
		}
		this.numberQuestions = n;
		return questionsListView;
	}

	public void setQuestionsListView(Collection<QuestionJPA> questionsListView) {
		this.questionsListView = questionsListView;
	}

	/**
	 * Returns the total number of instances of QuestionJPA
	 * @return Question number
	 */
	public int getNumberQuestions()
	{ 
		return this.numberQuestions;
	}

	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < questionsList.size()))
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
	 * Method that gets a list of instances by category or all QuestionJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allQuestionsList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");		
		questionsList = (Collection<QuestionJPA>) myMediaRemote.listAllQuestions();
	}
	
	public String updateList()
	{
		try {
			allQuestionsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllQuestionsView";
	}



	

	
}
