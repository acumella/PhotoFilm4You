package jpa;
import java.io.Serializable;
import javax.persistence.*;

/**
 * JPA Class ItemJPA
 */
@Entity
@Table(name="practicalcase.item")
public class ItemJPA implements Serializable {
	
	public enum ItemStatus {
		OPERATIONAL, BROKEN
	}

	private static final long serialVersionUID = 1L;

	private String serialNumber;
	private ItemStatus status; 
	private ProductJPA product;

	/**
	 * Class constructor methods
	 */
	public ItemJPA() {
		super();
		status = status.OPERATIONAL;
	}	
	public ItemJPA(String serialNumber, ItemStatus status, ProductJPA product) {		
		this.serialNumber = serialNumber;
		this.status = status;
		this.product = product;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	@Enumerated(EnumType.STRING)
	public ItemStatus getStatus() {
		return status;
	}
	public void setStatus(ItemStatus status) {
		this.status = status;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	@ManyToOne
	@JoinColumn (name="product")
	public ProductJPA getProduct() {
		return product;
	}
	public void setProduct(ProductJPA product) {
		this.product = product;
	}
	

}
