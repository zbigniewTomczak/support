package pomoc.partner.form.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;
import javax.persistence.Version;

import pomoc.form.FormElement;
import pomoc.partner.Partner;
import pomoc.partner.form.response.FormResponse;

@Entity
public class FormDefinition {
	@Id
	@GeneratedValue
	private Long id;
	
	@Version
	private Integer version;

	@Column(nullable=false)
	private String name;
	
	@ManyToOne(optional=false)
	private Partner partner;
	
	@Column(nullable=false)
	private String skey;
	
	@Column(nullable=false)
	private Integer formVersion;
	
	
	@OneToMany(mappedBy="form")
	@OrderColumn(name="elementOrder")
	private List<FormElement> elements;
	
	@OneToOne(mappedBy="formDefinition")
	private FormPublication publication;
	
	@OneToMany(mappedBy="formDefinition")
	private List<FormResponse> responses;
	
	
	
	@Transient
	private Integer responsesTotal = 0;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public String getSkey() {
		return skey;
	}
	public void setSkey(String skey) {
		this.skey = skey;
	}
	public Integer getFormVersion() {
		return formVersion;
	}
	public void setFormVersion(Integer formVersion) {
		this.formVersion = formVersion;
	}
	
	public List<FormElement> getElements() {
		return elements;
	}
	public void setElements(List<FormElement> elements) {
		this.elements = elements;
	}
	public FormPublication getPublication() {
		return publication;
	}
	public void setPublication(FormPublication publication) {
		this.publication = publication;
	}
	public List<FormResponse> getResponses() {
		return responses;
	}
	public void setResponses(List<FormResponse> responses) {
		this.responses = responses;
	}
	public Integer getResponsesTotal() {
		return responsesTotal;
	}
	public void setResponsesTotal(Integer responsesTotal) {
		this.responsesTotal = responsesTotal;
	}
	public Long getId() {
		return id;
	}
	@Deprecated
	String filename;
	@Deprecated
	public String getFilename() {
		return filename;
	}
	@Deprecated
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
}
