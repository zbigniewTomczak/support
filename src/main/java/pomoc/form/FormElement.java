package pomoc.form;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import pomoc.partner.form.model.FormDefinition;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class FormElement {
	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	
	@ManyToOne
	private FormDefinition form;
	private String label;
	private String placeholder;
	private String pattern;
	
	
}
