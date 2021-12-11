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
 * Managed Bean RateProductMBean
 */
@ManagedBean(name = "rateproduct")
@SessionScoped
public class RateProductMBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MediaFacadeRemote myMediaRemote;
	
	protected ProductRattingJPA newProductRating;
	public String result = "No result";
	
	public int productId = 1;
	public int rate;
	public String comment =  null;
	
	public RateProductMBean() throws Exception {
		newProductRating = new ProductRattingJPA();
		result = "RateProductMBean called";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void rateProduct() throws Exception
	{	
		try {
			if(rate >= 0 || rate <= 10) {
				Properties props = System.getProperties();
				Context ctx = new InitialContext(props);
				myMediaRemote = (MediaFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/MediaFacadeBean!ejb.MediaFacadeRemote");
				result = myMediaRemote.rateProduct(this.productId, this.rate, this.comment);	
			} else {
				result = "Error rating product";
			}	
		} catch(Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}		
	}

}
