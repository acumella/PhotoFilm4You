package jpa;
import java.io.Serializable;
import javax.persistence.*;

/**
 * JPA Class ResponseImageJPA
 */
@Entity
@Table(name="practicalcase.responseimage")
public class ResponseImageJPA implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String path;
	private ResponseJPA response;
	
	/**
	 * Class constructor methods
	 */
	
	public ResponseImageJPA() {
		super();
	}
	
	public ResponseImageJPA(int id, String path) {
		super();
		this.id = id;
		this.path = path;
	}	
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "responseimage_seq")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	
	@ManyToOne
	@JoinColumn (name="response")
	public ResponseJPA getResponse() {
		return response;
	}
	
	public void setResponse(ResponseJPA response) {
		this.response = response;
	}	
}
