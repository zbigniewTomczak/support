package pomoc.util.bootstrap;

import java.util.HashMap;
import java.util.Map;

import pomoc.partner.Partner;
import pomoc.partner.form.model.FormPublication;
import pomoc.partner.person.Person;
import pomoc.partner.person.Right;
import pomoc.partner.person.Role;

public class SetUpUser {

	public Person setUp(Partner partner, FormPublication formPub) {
		Person p = new Person();
		p.setName("Jan Użytkownik");
		p.setEmail("user@example.com");
		p.setPassword("asd");
		p.setRole(Role.USER);
		p.setPartner(partner);
		Map<FormPublication, Right> formRights = new HashMap<>();
		formRights.put(formPub, Right.EDIT);
		p.setFormRights(formRights);
		return p;
	}

}
