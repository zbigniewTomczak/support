package pomoc.partner.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pomoc.partner.Partner;
import pomoc.partner.login.LoggedPersonService;

@Stateless
public class PersonService {

	@Inject
	private LoggedPersonService loggedPersonService;
	
	public Person getPerson(String email, String password) {
		Person admin = new Person();
		//admin.setRole(Role.ADMIN);
		admin.setPartner(new Partner());
		return admin;
	}

	public Person loginPerson(String email, String password) {
		Person person = getPerson(email, password);
		loggedPersonService.setLoggedPerson(person);
		return person;
	}

	public void logout() {
		loggedPersonService.setLoggedPerson(null);
	}

}
