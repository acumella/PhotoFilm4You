package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import jpa.CategoryJPA;
import jpa.CustomerJPA;
import jpa.ProductJPA;
import jpa.ProductRattingJPA;
import jpa.QuestionJPA;
import jpa.ResponseImageJPA;
import jpa.ResponseJPA;
import jpa.UserJPA;

@Stateless
public class MediaFacadeBean implements MediaFacadeRemote {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	@Override
	public String askQuestion(String title, String message) {	
		
		if(isLoggedIn()) {
			if(isCustomer()) {
					QuestionJPA question = new QuestionJPA();
					UserJPA user = getUser();	
					CustomerJPA c = entman.find(CustomerJPA.class, user.getCustomer().getId());
					
					question.setTitle(title);
					question.setMessage(message);
					question.setCustomer(c);
					
					entman.persist(question);
					return "Question succesfully asked";
			}
		}
		return "Customer not logged in. Question was not asked";	
	}

	@Override
	public String answerQuestion(int questionId, String response) {
		if(isLoggedIn()) {
			if(!response.equals("")) {
				ResponseJPA r = new ResponseJPA();
				UserJPA user = getUser();	
				QuestionJPA q = entman.find(QuestionJPA.class, questionId);
				
				r.setMessage(response);
				if(q!=null) {
					r.setQuestion(q);
				}
				r.setUser(user);
				
				entman.persist(r);
				
				return "Question succesfully answered";
			}else {
				return "Response field cannot be void";
			}
		}
		return "User not logged in. Question was not answered";
	}

	@Override
	public Collection<?> listAllQuestions() {
		@SuppressWarnings("unchecked")
		Collection<QuestionJPA> allQuestions = entman.createQuery("from QuestionJPA").getResultList();
		return allQuestions;
	}

	@Override
	public QuestionJPA getQuestion(int questionId) {
		QuestionJPA q = null;
		
		try
		{
			if(questionId!=0) {
				@SuppressWarnings("unchecked")
				Collection<QuestionJPA> questions = entman.createQuery("FROM QuestionJPA q WHERE q.id = ?1").setParameter(1, questionId).getResultList();
				if (!questions.isEmpty() || questions.size()==1)
				{
					Iterator<QuestionJPA> iter = questions.iterator();
					q = (QuestionJPA) iter.next();				
				}
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
		
	    return q;
	}

	@Override
	public ResponseJPA getResponse(int responseId) {
		ResponseJPA r = null;
		
		try
		{
			@SuppressWarnings("unchecked")
			Collection<ResponseJPA> responses = entman.createQuery("FROM ResponseJPA q WHERE q.id = ?1").setParameter(1, responseId).getResultList();
			if (!responses.isEmpty() || responses.size()==1)
			{
				Iterator<ResponseJPA> iter = responses.iterator();
				r = (ResponseJPA) iter.next();				
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
		
	    return r;
	}

	@Override
	public String rateProduct(int productId, int rate, String comment) {
		if(isLoggedIn()) {
			if(isCustomer()) {
				ProductRattingJPA pr = new ProductRattingJPA();
				
				ProductJPA p = entman.find(ProductJPA.class, productId);
				
				pr.setComment(comment);
				if(rate>=0) {
					pr.setRatting(rate);
				} else { 
					return "Rating lower than 0";
				}
						
				pr.setProduct(p);
				
				UserJPA user = getUser();
				
				CustomerJPA c = entman.find(CustomerJPA.class, user.getCustomer().getId());
				pr.setCustomer(c);
				
				entman.persist(pr);
				
				return "Product succesfully rated";
			}
			return "Administrators cannot rate products";
		}
		return "User not logged in";
	}

	@Override
	public Collection<?> listAllProductComments(int productId) {
		@SuppressWarnings("unchecked")
		Collection<ProductRattingJPA> pr = entman.createQuery("from ProductRattingJPA where product = "+productId).getResultList();
		return pr;
	}

	@Override
	public String addImageToResponse(int responseId, String path) {	
		if(isLoggedIn()) {
			ResponseImageJPA ri = new ResponseImageJPA();
			ResponseJPA response = entman.find(ResponseJPA.class, responseId);
			
			ri.setPath(path);
			if(response!=null) {
				ri.setResponse(response);
			}
			
			entman.persist(ri);
			
			return "Response image succesfully added";
		}
		return "User not logged in";
	}

	@Override
	public String acceptOrRejectResponse(int responseId, boolean isAccepted) {
		if(isLoggedIn()) {
			if(isAdmin()) {
				System.out.println("isadmin "+isAdmin());
				try { 
					if(isAccepted) {
						@SuppressWarnings("unchecked")
						String query = "update practicalcase.response set status=2 where id="+responseId+"";		
						entman.createNativeQuery(query).executeUpdate();
					} else { 
						@SuppressWarnings("unchecked")
						String query = "update practicalcase.response set status=1 where id="+responseId+"";
						entman.createNativeQuery(query).executeUpdate();
					}
					return "Response succesfully accepted/rejected";
				}catch(PersistenceException e){
					System.out.println(e);
					return "Exception called, Response NOT accepted/rejected" + e.toString();
				}
			}
			return "Only administrators can accept or reject responses";
		}
		return "Administrator not logged in";
	}

	@Override
	public Collection<?> listAllQuestionResponses(int questionId) {
		@SuppressWarnings("unchecked")
		Collection<ResponseJPA> r = entman.createQuery("from ResponseJPA where question = "+questionId).getResultList();
		return r;
	}

	@Override
	public Collection<?> getImages(int responseId) {
		@SuppressWarnings("unchecked")
		Collection<ResponseImageJPA> ri = entman.createQuery("from ResponseImageJPA where response = "+responseId).getResultList();
		return ri;
	}
	
	private boolean isLoggedIn() {
		HttpSession session = getSession();
		if(session != null) {
			if(session.getAttribute("role") != null) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isAdmin() {
		HttpSession session = getSession();
		if(session != null) {
			if(session.getAttribute("role").equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isCustomer() {
		HttpSession session = getSession();
		if(session != null) {
			if(session.getAttribute("role").equals("ROLE_CUSTOMER")) {
				return true;
			}
		}
		return false;
	}
	
	private UserJPA getUser() {
		HttpSession session = getSession();
		UserJPA user = new UserJPA();
		if(isLoggedIn()) {
			try {
				Properties props = System.getProperties();
				Context ctx;
				ctx = new InitialContext(props);
				UserFacadeRemote userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
				String email = (String)session.getAttribute("email");
				String password = (String)session.getAttribute("password");
				user = userRemote.login(email, password);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	
	private HttpSession getSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (HttpSession) facesContext.getExternalContext().getSession(true);
	}



}
