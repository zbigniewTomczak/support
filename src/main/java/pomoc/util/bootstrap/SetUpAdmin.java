package pomoc.util.bootstrap;

import pomoc.partner.Partner;
import pomoc.partner.person.Person;
import pomoc.partner.person.Role;

public class SetUpAdmin {

	public Person setUp(Partner partner) {
	//	insert into Person (id, name, email, password, role, partner_id, version) values
 //(0, 'Admin', 'admin@example.com', 'asd', 'ADMIN', 0, 1);
		Person p = new Person();
		p.setName("Jan Administrator");
		p.setEmail("admin@example.com");
		p.setPassword("asd");
		p.setRole(Role.ADMIN);
		p.setPartner(partner);
		return p;
	}

}
