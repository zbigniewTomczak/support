package pomoc.form;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SupportFormTemplate {
	@Id
	@GeneratedValue
	private Long id;
	
	String filename;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
