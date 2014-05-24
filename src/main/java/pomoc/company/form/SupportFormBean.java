package pomoc.company.form;

import javax.enterprise.inject.Model;

@Model
public class SupportFormBean {

	public String newSupportForm() {
		return "forms?faces-redirect=true";
	}
}
