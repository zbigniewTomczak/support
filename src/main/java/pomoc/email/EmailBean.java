package pomoc.email;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.util.faces.FacesMessage;

@Model
public class EmailBean {
	@Inject
	private EmailParser emailParser;
	@Inject
	private FacesMessage facesMessage;
	
	public void check() {
		try {
			emailParser.checkMailbox();
		} catch (Exception e) {
			e.printStackTrace();
			facesMessage.postError("Wystąpił błąd podczas sprawdzania poczty.");
		}
	}
}
