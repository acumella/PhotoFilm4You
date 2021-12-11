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
 * Managed Bean AcceptOrRejectResponseMBean
 */
@ManagedBean(name = "acceptorrejectresponse")
@SessionScoped
public class AcceptOrRejectResponseMBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected ResponseJPA response;
	public String result = "";
	
	public int responseId =  0;
	public boolean isAccepted;
	
	public AcceptOrRejectResponseMBean() throws Exception{
		response = new ResponseJPA();
		result = "";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted() throws Exception {
		this.isAccepted = true;
		acceptOrRejectResponse();
	}
	
	public void setRejected() throws Exception {
		this.isAccepted = false;
		acceptOrRejectResponse();
	}
	
	public void acceptOrRejectResponse() throws Exception {	
		try {
			if(responseId >= 1) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");
				result = myMediaRemote.acceptOrRejectResponse(this.responseId,this.isAccepted);	
			} else {
				result = "Response does not exist";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
