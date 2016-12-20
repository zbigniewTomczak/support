package pomoc.partner.person;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.partner.login.LoggedPersonService;
import pomoc.util.bootstrap.SetUpSampleData;

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
	
	@Inject
	private SetUpSampleData sampleData;
	
	@Produces
	@Named
	private Person loggedPerson;
	
	@PostConstruct
	public void init() {
		loggedPerson = loggedPersonService.getLoggedPerson();
	}
	
	public String newPerson() {
		return "people?faces-redirect=true";
	}
	
	public void checkPermissions(ComponentSystemEvent event) throws IOException {
		if (loggedPerson==null) {
			String bootstrapData = fc.getExternalContext().getInitParameter("pomoc.hooks.bootstrap.data");
			if (Boolean.valueOf(bootstrapData)) {
				sampleData.setUp();
			}
			String email = fc.getExternalContext().getInitParameter("pomoc.logged.person.email");
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
		if (loggedPerson != null) {
			return personService.getColleagues(loggedPerson);
		} 
		
		//todo report error
		return null;
	}

	public Person getLoggedPerson() {
		return loggedPerson;
	}

	public void setLoggedPerson(Person loggedPerson) {
		this.loggedPerson = loggedPerson;
	}
	
	
}
