package pomoc.partner.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import pomoc.partner.Partner;
import pomoc.partner.person.Person;

//@Stateful
@SessionScoped
//@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class LoggedPersonService implements Serializable {
	private static final long serialVersionUID = 1L;
	private Person loggedPerson;
	
	@Current
	@Produces
	@Named("person")
	public Person getLoggedPerson() {
		//Person p = new Person();
		//p.setId(0L);
		//return p;
		return loggedPerson;
	}

	@Current 
	@Produces
	@Named("partner")
	public Partner getPartnerForLoggedPerson() {
		if (loggedPerson != null) {
			return loggedPerson.getPartner();
		}
		return null;
	}
	
	public void setLoggedPerson(Person loggedPerson) {
		this.loggedPerson = loggedPerson;
	}
	
	
}
