package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class CustomerJPA
 */
@Entity
@Table(name="practicalcase.customer")
public class CustomerJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String address;
	Collection<RentJPA> rents;
    private UserJPA user;
	
	/**
	 * Class constructor methods
	 */
	public CustomerJPA() {
		super();
	}	
	public CustomerJPA(String email, String password, String nif, String name, String surname, String phone, String address) {		
		this.nif = nif;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.address = address;
		this.user = new UserJPA(email, password, this);
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "customer_seq")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	/*
	 * @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	 * 
	 * @JoinColumn(name = "customer") public Collection<RentJPA> getRents() { return
	 * rents; } public void setRents(Collection<RentJPA> rents) { this.rents =
	 * rents; }
	 */
	
	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL )
	public UserJPA getUser() {
		return user;
	}
	
	
	public void setUser(UserJPA user) {
		this.user = user;
	}
}
