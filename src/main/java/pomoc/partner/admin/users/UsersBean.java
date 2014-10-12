
package pomoc.partner.admin.users;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.partner.Partner;
import pomoc.partner.login.LoggedPersonService;
import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;

@Model
public class UsersBean {

	@Inject
	private PersonService personService;
	
	@Inject
	private LoggedPersonService loggedPersonService;
	
	@Inject
	private Logger log;
	
	public List<Person> getAllPartnerUsers() {
		Partner partner = loggedPersonService.getPartnerForLoggedPerson();
		if (partner != null) {
			log.info("There are " + personService.getPartnerPeopleCount(partner) + " people for Partner " + partner.getName());
			return personService.getPartnerPeople(partner);
		}
		return Collections.emptyList();
	}
}
