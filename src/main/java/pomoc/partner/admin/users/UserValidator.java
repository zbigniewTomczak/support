package pomoc.partner.admin.users;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;
import pomoc.util.faces.FacesMessage;

public class UserValidator {

	@Inject
	private FacesMessage fm;
	
	@Inject
	private PersonService personService;

	public boolean validate(Person user) {
		if (user == null) {
			fm.postError("Peron null");
			return false;
		}
		if (StringUtils.isEmpty(user.getEmail())) {
			fm.postError("Person email cannot be empty");
			return false;
		}
		if (StringUtils.isEmpty(user.getName())) {
			fm.postError("Person name cannot be empty");
			return false;
		}
		if (personService.emailExists(user.getEmail())) {
			fm.postError("Person with this email already exists ("+user.getEmail()+")");
			return false;
		}
		
		return true;
	}
	
	
	
}
