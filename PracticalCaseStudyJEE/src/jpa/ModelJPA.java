package jpa;
import java.io.Serializable;
import javax.persistence.*;

/**
 * JPA Class ModelJPA
 */
@Entity
@Table(name="practicalcase.model")
public class ModelJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name; 
	private BrandJPA brand;
	
	/**
	 * Class constructor methods
	 */
	public ModelJPA() {
		super();
	}	
	public ModelJPA(String name) {		
		this.name = name;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "model_seq")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn (name="brand")
	public BrandJPA getBrand() {
		return brand;
	}
	public  void setBrand(BrandJPA brand) {
		this.brand = brand;
	}	
}
