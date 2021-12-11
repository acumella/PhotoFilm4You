package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.QuestionJPA;
import jpa.ResponseJPA;
import ejb.MediaFacadeRemote;

/**
 * Managed Bean ShowResponseMBean
 */
@ManagedBean(name = "showresponse")
@SessionScoped
public class ShowResponseMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected ResponseJPA dataResponse;
	
	public int responseId = 1;
	public String message = "None";
	public boolean status = false;

	public ShowResponseMBean() throws Exception {
		setDataResponse();
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
		setDataResponse();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public ResponseJPA getDataResponse() {
		return dataResponse;
	}
	
	public void setDataResponse() {
		try {
			clearStats();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");
			dataResponse = (ResponseJPA) myMediaRemote.getResponse(responseId);
			this.message = dataResponse.getMessage();
			if(dataResponse.getStatus()==0) {
				this.status = false;
			} else {
				this.status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void clearStats() {
		this.message = "None";
		this.status = false;
	}

}
