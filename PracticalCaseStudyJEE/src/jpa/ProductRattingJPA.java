package jpa;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * JPA Class ProductRattingJPA
 */
@Entity
@Table(name="practicalcase.productRatting")
public class ProductRattingJPA implements Serializable{

	private int id;
	private static final long serialVersionUID = 1L;
	private String comment; 
	private int ratting; 
	private ProductJPA product;
	private CustomerJPA customer;
	
	/**
	 * Class constructor methods
	 */
	public ProductRattingJPA() {
		super();
	}	
	
	public ProductRattingJPA(String comment, int ratting) {		
		this.comment = comment;
		this.ratting = ratting;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "productrating_seq")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public int getRatting() {
		return ratting;
	}

	public void setRatting(int ratting) {
		this.ratting = ratting;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn (name="product")
	public ProductJPA getProduct() {
		return product;
	}
	public  void setProduct(ProductJPA product) {
		this.product = product;
	}
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn (name="customer")
	public CustomerJPA getCustomer() {
		return this.customer;
	}
	
	public void setCustomer(CustomerJPA customer) {
		this.customer = customer;
	}	
	
	
}
