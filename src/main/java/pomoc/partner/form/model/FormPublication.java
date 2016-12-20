package pomoc.partner.form.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import pomoc.partner.Partner;
import pomoc.partner.form.sla.SlaPlan;

@Entity
public class FormPublication {

	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	@Deprecated
	private String name;
	@ManyToOne
	@Deprecated
	private Partner partner;
	@Column(nullable=false, unique=true)
	private String key;
	
	private boolean active = true;
	
	private String title;
	private String confirmationMessage;
	private Integer width = 700;
	private Integer height = 300;
	private String css;
	@OneToOne(optional=false)
	private FormDefinition formDefinition;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<SlaPlan, Integer> slaPlans;
	
	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		} else {
			return this.hashCode();
		}
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormPublication other = (FormPublication) obj;
		if (id == null || other.id == null) {
			return false;
		} else if (id.equals(other.id))
			return true;
		return false;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Deprecated
	public String getName() {
		return name;
	}
	@Deprecated
	public void setName(String name) {
		this.name = name;
	}
	@Deprecated
	public Partner getPartner() {
		return partner;
	}
	@Deprecated
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getConfirmationMessage() {
		return confirmationMessage;
	}
	public void setConfirmationMessage(String confirmationMessage) {
		this.confirmationMessage = confirmationMessage;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public FormDefinition getFormDefinition() {
		return formDefinition;
	}
	public void setFormDefinition(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Map<SlaPlan, Integer> getSlaPlans() {
		return slaPlans;
	}
	public void setSlaPlans(Map<SlaPlan, Integer> slaPlans) {
		this.slaPlans = slaPlans;
	}
	
	
	
}
