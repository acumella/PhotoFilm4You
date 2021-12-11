package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class QuestionJPA
 */
@Entity
@Table(name="practicalcase.question")
public class QuestionJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String message; 
	private CustomerJPA customer = new CustomerJPA();
	
	/**
	 * Class constructor methods
	 */
	
	public QuestionJPA() {
		super();
	}

	public QuestionJPA(int id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "question_seq")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn (name="customer")
	public CustomerJPA getCustomer() {
		return customer;
	}
	
	public void setCustomer(CustomerJPA customer) {
		this.customer = customer;
	}
	
	
	
}
