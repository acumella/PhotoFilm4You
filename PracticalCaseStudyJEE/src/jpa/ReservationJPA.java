package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class ReservationJPA
 */
@Entity
@Table(name="practicalcase.reservation")
public class ReservationJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private Date reservedDay;
	private String serialNumber;
	
	/**
	 * Class constructor methods
	 */
	public ReservationJPA() {
		super();
	}	
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	

	public Date getReservedDay() {
		return reservedDay;
	}
	public void setReservedDay(Date reservedDay) {
		this.reservedDay = reservedDay;
	}
}
