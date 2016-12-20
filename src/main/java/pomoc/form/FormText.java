package pomoc.form;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("comment")
public class FormText extends FormElement {
	private String text;

	public FormText() {
	}
	
	public FormText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
