package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class UserJPA
 */
@Entity
@Table(name="practicalcase.user")
public class UserJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String email;
	private String password;
	private String role;
	private Date createdAt;
	private CustomerJPA customer;
	
	/**
	 * Class constructor methods
	 */
	public UserJPA() {
		super();
	}
	public UserJPA(String email, String password, String role) {		
		this.email = email;
		this.password = password;
		this.role = role;
		this.createdAt = java.util.Calendar.getInstance().getTime();
	}
	public UserJPA(String email, String password, CustomerJPA customer) {		
		this.email = email;
		this.password = password;
		this.role = "ROLE_CUSTOMER";
		this.customer = customer;
		this.createdAt = new Date();
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "user_seq")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer", updatable = false, nullable = true)
	public CustomerJPA getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerJPA customer) {
		this.customer = customer;
	}
}
