package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class ProductJPA
 */
@Entity
@Table(name="practicalcase.product")
public class ProductJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String description;
	private int dailyPrice;
	private int availableItems;
	private float productRatting;
	private BrandJPA brand;
	private ModelJPA model; 
	private CategoryJPA category;
	
	/**
	 * Class constructor methods
	 */
	public ProductJPA() {
		super();
	}	
		
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "product_seq")
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getDailyPrice() {
		return dailyPrice;
	}
	
	public void setDailyPrice(int dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	
	public int getAvailableItems() {
		return availableItems;
	}
	
	public void setAvailableItems(int availableItems) {
		this.availableItems = availableItems;
	}
	
	public float getProductRatting() {
		return productRatting;
	}
	
	public void setProductRatting(float productRatting) {
		this.productRatting = productRatting;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name="brand")
	public BrandJPA getBrand() {
		return brand;
	}
	public  void setbrand(BrandJPA brand) {
		this.brand = brand;
	}
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name="model")
	public ModelJPA getModel() {
		return model;
	}
	public  void setmodel(ModelJPA model) {
		this.model = model;
	}
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name="category")
	public CategoryJPA getCategory() {
		return category;
	}
	public  void setCategory(CategoryJPA category) {
		this.category = category;
	}
	
}
