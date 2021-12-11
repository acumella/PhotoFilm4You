package ejb;

import java.util.Collection;

import javax.ejb.Remote;

import jpa.QuestionJPA;
import jpa.ResponseJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface MediaFacadeRemote {
	/**
	 * Remotely invoked method.
	 */
	public String askQuestion(String title, String message);
	public String answerQuestion(int questionId, String response);
	public Collection<?> listAllQuestions();
	public QuestionJPA getQuestion(int questionId);
	public ResponseJPA getResponse(int responseId);
	public String rateProduct(int productId, int rate, String comment);
	public Collection<?> listAllProductComments(int productId);
	public String addImageToResponse(int responseId, String path);
	public String acceptOrRejectResponse(int responseId, boolean isAccepted);
	public Collection<?> listAllQuestionResponses(int questionId);
	public Collection<?> getImages(int responseId);
	

}
