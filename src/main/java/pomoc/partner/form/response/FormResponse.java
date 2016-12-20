package pomoc.partner.form.response;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import pomoc.form.FormElement;
import pomoc.partner.form.model.FormDefinition;

@Entity
public class FormResponse {
	@Id
	@GeneratedValue
	private Long id;

	@ElementCollection
	@CollectionTable(name="FORM_RESPONSES")
	@Column(name="RESPONSE")
	private Map<FormElement, String> responses;
	
	@ManyToOne
	private FormDefinition formDefinition;

	public Long getId() {
		return id;
	}

	public Map<FormElement, String> getResponses() {
		return responses;
	}
	public void setResponses(Map<FormElement, String> responses) {
		this.responses = responses;
	}

	public FormDefinition getFormDefinition() {
		return formDefinition;
	}


	public void setFormDefinition(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}

	@Deprecated
	private String name;
	@Deprecated
	private String email;
	@Deprecated
	private String content;
	

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



	
	
	
}
