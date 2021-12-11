package jpa;
import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.*;

/**
 * JPA Class CancellationJPA
 */
@Entity
@Table(name="practicalcase.cancellation")
public class CancellationJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rent;
	private Date creationDate;
	private double penalization = 0.0;
	int penalizationStatus = penalization_status.Pending.ordinal();
	
	/**
	 * Class constructor methods
	 */
	public CancellationJPA() {
		super();
		creationDate = java.util.Calendar.getInstance().getTime();
	}	
	public CancellationJPA(String rent, Date creationDate, double penalization) {		
		this.rent = rent;
		this.creationDate = creationDate;
		this.penalization = penalization;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	public String getRent() {
		return rent;
	}
	public void setRent(String rent) {
		this.rent = rent;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public double getPenalization() {
		return penalization;
	}
	public void setPenalization(double penalization) {
		this.penalization = penalization;
	}	

	public int getPenalizationStatus() {
		return penalizationStatus;
	}
	public void setPenalizationStatus(int penalizationStatus) {
		this.penalizationStatus = penalizationStatus;
	}	

	/*Els usuaris podran realitzar anul�lacions dels lloguers. Per a cada anul�laci� es registrar�
la data i hora en qu� es va realitzar i l'import de la penalitzaci�.

Els administradors podran visualitzar les anul�lacions que tenen penalitzaci� pendent de
pagament per poder gestionar manualment el cobrament. Un cop s'hagi rebut el
pagament informaran en el sistema que la penalitzaci� est� pagada.

El client tamb� ens ha definit amb exactitud el c�lcul de la penalitzaci�:

o Si s'anul�la la petici� de lloguer abans de 2 dies de la data de lloguer, es retornar�
l'import complet (no hi ha penalitzaci�).

o Si s'anul�la entre 24 i 48 hores abans de la data, la penalitzaci� ser� de 2 euros.

o Si s'anul�la durant les 24 hores pr�vies, la penalitzaci� ser� de l'25% de l'import
total del lloguer fins a un m�xim de 25 euros.*/
	
	public void calculatePenalization(Date rentDate, Date finishingDate, double totalPrice)
	{
		//long daysBetween = Duration.between(creationDate, finishingDate).toDays();
		/*long diff = finishingDate.getTime() - creationDate.getTime();
		long daysUntilFinish = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);*/
		long diff = creationDate.getTime() - rentDate.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		if(days >= 2) {
			diff = finishingDate.getTime() - creationDate.getTime();
			long daysUntilFinish = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			if(daysUntilFinish >= 1 && daysUntilFinish <= 2) {
				penalization = 2.0;
			}
			else if(daysUntilFinish < 1) {
				penalization = totalPrice / 4.0;
				penalization = Math.min(penalization, 25.0);
			}
		}
	}
	/**
	 * Methods get/set persistent relationships
	 */
	/*@ManyToOne
	@JoinColumn (name="customer")
	public CustomerJPA getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerJPA customer) {
		this.customer = customer;
	}*/
}
