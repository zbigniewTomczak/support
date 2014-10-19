package pomoc.partner.form.response;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import pomoc.partner.form.model.FormDefinition;

@Entity
public class FormResponse {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String email;
	private String content;
	@ManyToOne
	private FormDefinition formDefinition;
	
	@Override
	public String toString() {
		return String.format("FormResponse(%s, %s)",name, email);
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


	public FormDefinition getFormDefinition() {
		return formDefinition;
	}


	public void setFormDefinition(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}
	
	
}
