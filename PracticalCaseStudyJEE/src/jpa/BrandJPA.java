package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class BrandJPA
 */
@Entity
@Table(name="practicalcase.brand")
public class BrandJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private Collection<ProductJPA> products;
	private Collection<ModelJPA> models;
	
	/**
	 * Class constructor methods
	 */
	public BrandJPA() {
		super();
	}	
	public BrandJPA(String name) {		
		this.name = name;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "brand_seq")
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
/*	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name = "model") 
	public Collection<ModelJPA> getModelsbyBrand() {
		return models;
	}	
	public void setModelsbyBrand(Collection<ModelJPA> models) {
 		this.models = models; 
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name = "product") 
	public Collection<ProductJPA> getProductsbyBrand() {
		return products;
	}	
	public void setProductsbyBrand(Collection<ProductJPA> products) {
 		this.products = products; 
	} */
}
