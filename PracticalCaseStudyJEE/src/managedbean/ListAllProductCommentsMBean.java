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

/**
 * Managed Bean ListAllQuestions
 */
@ManagedBean(name = "listallproductcomments")
@SessionScoped
public class ListAllProductCommentsMBean implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected Collection<ProductRattingJPA> productRatingListView;
	protected int numberProductRatings  = 0;
	
	int productId = 1;
	
	private Collection <ProductRattingJPA> productRatingList;
	private int screen = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListAllProductCommentsMBean() throws Exception{
		this.allProductCommentsList();
	}
	
	/**
	 * Method that returns an instance Collection of 10 or less ProductRattingJPA according screen 
	 * where the user is.
	 * @return Collection ProductRattingJPA
	 */
	
	
	public Collection<ProductRattingJPA> getProductRatingListView() {
		try {
			this.allProductCommentsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int n =0;
		productRatingListView = new ArrayList<ProductRattingJPA>();
		for (Iterator<ProductRattingJPA> iter2 = productRatingList.iterator(); iter2.hasNext();)
		{
			ProductRattingJPA productRating2 = (ProductRattingJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10)) {				
				this.productRatingListView.add(productRating2);
			}
			n +=1;
		}
		this.numberProductRatings = n;
		return productRatingListView;
	}

	public void setProductRatingListView(Collection<ProductRattingJPA> productRatingListView) {
		this.productRatingListView = productRatingListView;
	}
	
	
	/**
	 * Returns the total number of instances of QuestionJPA
	 * @return Question number
	 */
	public int getNumberProductRatings() { 
		return this.numberProductRatings;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	
	public void nextScreen()
	{
		if (((screen+1)*10 < productRatingList.size()))
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
	 * Get/set the Product id
	 * @return product id
	 */
	public int getProductId()
	{
		return this.productId;
	}
	
	public void setProductId(int productId)
	{
		this.productId = productId;
		try {
			allProductCommentsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets a list of instances by category or all ProductRattingJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void allProductCommentsList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");		
		productRatingList = (Collection<ProductRattingJPA>) myMediaRemote.listAllProductComments(this.productId);
	}
	
	public String updateList()
	{
		try {
			allProductCommentsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listAllProductCommentsView";
	}
	
	
	
}
