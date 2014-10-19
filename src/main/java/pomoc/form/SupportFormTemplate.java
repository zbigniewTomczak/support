package pomoc.form;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Version;

import pomoc.partner.FormPublication;

@Entity
public class SupportFormTemplate {
	@Id
	@GeneratedValue
	private Long id;
	
	@Version
	private Integer ver_internal;
	String filename;
	@Column(nullable=false)
	private String skey;
	@Column(nullable=false)
	private Integer version;
	private SupportFormTemplate previous;
	
	@OneToMany(mappedBy="form")
	@OrderColumn(name="elementOrder")
	private List<FormElement> formElements;
	
	@OneToMany(mappedBy="supportFormTemplate")
	private List<FormPublication> formPublication;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
