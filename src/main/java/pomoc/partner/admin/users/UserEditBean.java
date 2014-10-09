package pomoc.partner.admin.users;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;
import pomoc.partner.person.Role;
import pomoc.util.PasswordGenerator;
import pomoc.util.faces.FacesMessage;

@Model
public class UserEditBean {

	private Person user;
	
	@Inject
	private FacesMessage fm;
	
	@Inject
	private PersonService personService;
	
	@Inject
	private UserValidator userValidator;
	
	@Inject
	private PasswordGenerator passwordGenerator;
	
	private boolean admin;
	
	public String saveUser() {
		boolean ok = userValidator.validate(user);
		if (ok) {
			try {
				if (admin) {
					user.setRole(Role.ADMIN);
				}
				user.setPassword(passwordGenerator.generatePassword());
				user = personService.saveAndNotifyUser(user);
			} catch (EJBException e) {
				fm.postError("Cannot Save user ("+ e.getMessage() + ")", e);
			}
			fm.postInfo("User "+user.getName()+" created. User password has beed send to " + user.getEmail() + ".");
		}
		return null;
	}
	
}
