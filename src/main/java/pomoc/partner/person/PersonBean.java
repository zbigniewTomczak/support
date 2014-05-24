package pomoc.partner.person;

import javax.enterprise.inject.Model;

@Model
public class PersonBean {
	public String newPerson() {
		return "people?faces-redirect=true";
	}
}
