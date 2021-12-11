package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class CategoryJPA
 */
@Entity
@Table(name="practicalcase.category")
public class CategoryJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private CategoryJPA parentCategory;
	private Collection<ProductJPA> products; 
	
	/**
	 * Class constructor methods
	 */
	public CategoryJPA() {
		super();
	}	
	public CategoryJPA(String name) {		
		this.name = name;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "category_seq")
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
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn(name = "parentCategory")
	public CategoryJPA getParentCategory() {
		return parentCategory;
	}
	
	public void setParentCategory(CategoryJPA parentCategory) {
		this.parentCategory = parentCategory;
	}
}
