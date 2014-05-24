package pomoc.login;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;

@Model
public class LoginBean {

	private String email;
	private String password;
	
	@Inject
	private PersonService personService;
	
	public String login() {
		Person person;
		try {
			person = personService.getPerson(email, password);
		} catch (EJBException e) {
			e.printStackTrace();
			//TODO error message
			return null;
		}
		
		return "dashboard?faces-redirect=true";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
