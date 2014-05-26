package pomoc.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import pomoc.partner.SupportForm;

@Entity
public class SupportFormResponse {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String email;
	private String content;
	@ManyToOne
	private SupportForm supportForm;
	
	@Override
	public String toString() {
		return String.format("SupportFormResponse(%s, %s)",name, email);
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public SupportForm getSupportForm() {
		return supportForm;
	}
	public void setSupportForm(SupportForm supportForm) {
		this.supportForm = supportForm;
	}
	
	
}
