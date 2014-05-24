package pomoc.partner.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pomoc.login.LoggedPersonService;

@Stateless
public class PersonService {

	@Inject
	private LoggedPersonService loggedPersonService;
	
	public Person getPerson(String email, String password) {
		Person admin = new Person();
		admin.setRole(Role.ADMIN);
		return admin;
	}

	public Person loginPerson(String email, String password) {
		Person person = getPerson(email, password);
		loggedPersonService.setLoggedPerson(person);
		return person;
	}

}
