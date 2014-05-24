package pomoc.partner.person;

import javax.ejb.Stateless;

@Stateless
public class PersonService {

	public Person getPerson(String email, String password) {
		Person admin = new Person();
		admin.setRole(Role.ADMIN);
		return admin;
	}

}
