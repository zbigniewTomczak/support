package pomoc.partner.person;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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
	
	@Inject
	private FacesContext fc;
	
	@Inject
	private Logger log;
	
	public String newPerson() {
		return "people?faces-redirect=true";
	}
	
	public void checkPermissions(ComponentSystemEvent event) throws IOException {
		String email = fc.getExternalContext().getInitParameter("pomoc.logged.person.email");
		if (loggedPersonService.getLoggedPerson()==null) {
			if (email != null) {
				log.warning("Backdoor logging to " + email);
				Person person = personService.getPerson(email);
				loggedPersonService.setLoggedPerson(person);
				return;
			}
			FacesContext fc = FacesContext.getCurrentInstance();
		    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/support/signin2?faces-redirect=true");
		}
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
