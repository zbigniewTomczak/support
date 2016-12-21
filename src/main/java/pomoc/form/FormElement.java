package pomoc.form;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

import pomoc.partner.form.model.FormDefinition;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class FormElement {
	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	
	@Column(insertable = false, updatable = false)
    private String type;
	
	private Integer elementOrder;
	
	@ManyToOne
	private FormDefinition form;
	private String label;
	private String placeholder;
	@Transient
	private String transientType;

	public static FormElement create(String elementType) {
		FormElement element;
		switch (elementType) {
		case "input-text": 
			element = new FormInputText();
			element.setLabel("Title");
			element.setPlaceholder("");
			break;
		case "input-textarea": 
			element = new FormInputTextarea();
			element.setLabel("Title");
			element.setPlaceholder("");
			break;
		case "comment": 
			element = new FormText("Kliknij aby wyedytować tekst.");
			break;
		case "input-email": 
			element = new FormEmail();
			element.setLabel("Email");
			element.setPlaceholder("Email");
			break;
		case "input-checkbox":
			element = new FormCheckbox();
			element.setLabel("Opis");
			break;
		default:
			return null;
		}
		element.setType(elementType);
		return element;
	}
	
	public Long getId() {
		return id;
	}
	public String getType() {
		return type == null ? transientType : type;
	}
	public String setType(String type) {
		return transientType = type;
	}
	public FormDefinition getForm() {
		return form;
	}
	public void setForm(FormDefinition form) {
		this.form = form;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public Integer getElementOrder() {
		return elementOrder;
	}
	
	
	
	
}
