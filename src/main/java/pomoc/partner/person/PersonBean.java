package pomoc.partner.person;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.partner.login.LoggedPersonService;

@Model
public class PersonBean {
	
	@Inject
	private PersonService personService;
	
	@Inject
	private LoggedPersonService loggedPersonService;
	
	public String newPerson() {
		return "people?faces-redirect=true";
	}
	
	@Produces
	@Named
	public List<SelectItem> getPartnerPersons() {
		if (loggedPersonService.getLoggedPerson() != null) {
			return personService.getColleagues(loggedPersonService.getLoggedPerson());
		} 
		
		//todo report error
		return null;
	}
}
