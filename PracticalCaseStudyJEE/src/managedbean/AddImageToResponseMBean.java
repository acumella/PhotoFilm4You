package managedbean;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.ResponseJPA;
import jpa.QuestionJPA;
import jpa.ResponseImageJPA;
import ejb.MediaFacadeRemote;

/**
 * Managed Bean AddImageToResponseMBeans
 */
@ManagedBean(name = "addimagetoresponse")
@SessionScoped
public class AddImageToResponseMBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected ResponseImageJPA newResponseImage;
	public String result = "";
	
	public int responseId = 0;
	public String path =  "";
	
	public AddImageToResponseMBean() throws Exception{
		newResponseImage = new ResponseImageJPA();
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void addImageToResponse() throws Exception
	{	
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");
			result = myMediaRemote.addImageToResponse(this.responseId,this.path);	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}
}
