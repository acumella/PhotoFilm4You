package jpa;
import java.io.Serializable;
import javax.persistence.*;

/**
 * JPA Class ResponseJPA
 */
@Entity
@Table(name="practicalcase.response")
public class ResponseJPA implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String message;
	private int status = response_status.Pending.ordinal();
	private QuestionJPA question;
	private UserJPA user;
	
	/**
	 * Class constructor methods
	 */
	
	public ResponseJPA() {
		super();
	}

	public ResponseJPA(int id, String message) {
		super();
		this.id = id;
		this.message = message;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "response_seq")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	@ManyToOne
	@JoinColumn (name="question")
	public QuestionJPA getQuestion() {
		return question;
	}
	
	public void setQuestion(QuestionJPA question) {
		this.question = question;
	}	
	
	@ManyToOne
	@JoinColumn (name="userid")
	public UserJPA getUser() {
		return this.user;
	}
	
	public void setUser(UserJPA user) {
		this.user = user;
	}	

}
