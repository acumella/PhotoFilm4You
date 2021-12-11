package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.MediaFacadeRemote;
import jpa.ResponseImageJPA;

/**
 * Managed Bean ListAllQuestions
 */
@ManagedBean(name = "listallresponseimages")
@SessionScoped
public class ListAllResponseImagesMBean implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected Collection<ResponseImageJPA> responseImagesListView;
	protected int numberResponseImages  = 0;
	
	int responseId = 1;
	
	private Collection<ResponseImageJPA> responseImagesList;
	private int screen = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllResponseImagesMBean() throws Exception{
		this.allResponseImagesList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less ResponseImageJPA according screen 
	 * where the user is.
	 * @return Collection ResponseImageJPA
	 */
	
	
	public Collection<ResponseImageJPA> getResponseImagesListView() {
		try {
			this.allResponseImagesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int n =0;
		responseImagesListView = new ArrayList<ResponseImageJPA>();
		for (Iterator<ResponseImageJPA> iter2 = responseImagesList.iterator(); iter2.hasNext();)
		{
			ResponseImageJPA responseimage2 = (ResponseImageJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10)) {				
				this.responseImagesListView.add(responseimage2);
			}
			n +=1;
		}
		
		this.numberResponseImages = n;
		return responseImagesListView;
	}

	public void setResponseImagesListView(Collection<ResponseImageJPA> responseImagesListView) {
		this.responseImagesListView = responseImagesListView;
	}
	
	
	/**
	 * Returns the total number of instances of ResponseImageJPA
	 * @return Response Image number
	 */
	public int getNumberResponseImages() { 
		return this.numberResponseImages;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	
	public void nextScreen()
	{
		if (((screen+1)*10 < responseImagesList.size()))
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
	 * Get/set the Response id
	 * @return response id
	 */
	public int getResponseId()
	{
		return this.responseId;
	}
	
	public void setResponseId(int responseId)
	{
		this.responseId = responseId;
		try {
			allResponseImagesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all ResponseImageJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allResponseImagesList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");		
		responseImagesList = (Collection<ResponseImageJPA>) myMediaRemote.getImages(this.responseId);
	}
	
	public String updateList()
	{
		try {
			allResponseImagesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllResponseImagesView";
	}
	
	
	
}
