package pomoc.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import pomoc.partner.person.Person;

//@Stateful
@SessionScoped
//@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class LoggedPersonService implements Serializable {
	private static final long serialVersionUID = 1L;
	private Person loggedPerson;

	@Produces 
	@Named("person")
	public Person getLoggedPerson() {
		return loggedPerson;
	}

	public void setLoggedPerson(Person loggedPerson) {
		this.loggedPerson = loggedPerson;
	}
	
	
}
