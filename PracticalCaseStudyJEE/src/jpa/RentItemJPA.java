/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class RentItemJPA
 */
@Entity
@Table(name="practicalcase.rentItem")
public class RentItemJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private RentJPA rent;
	private ItemJPA item;
	
	/**
	 * Class constructor methods
	 */
	public RentItemJPA() {
		super();
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	

	/**
	 * Methods get/set persistent relationships
	 */
	@OneToOne
	@JoinColumn (name="rentID")
	public RentJPA getRent() {
		return rent;
	}
	public void setRent(RentJPA rent) {
		this.rent = rent;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	@OneToOne
	@JoinColumn (name="serialNumber")
	public ItemJPA getItem() {
		return item;		
	}	
	public void setItem(ItemJPA item) {
		this.item = item;
	}
	
}
