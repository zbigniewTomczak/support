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
			person = personService.loginPerson(email, password);
		} catch (EJBException e) {
			e.printStackTrace();
			//TODO error message
			return null;
		}
		
		if (person.isAdmin() && !person.getPartner().hasForms()) {
			return "/support/administration/newform?faces-redirect=true";
		} else {
			return "/support/dashboard?faces-redirect=true";
		}
	}

	public String logout() {
		try {
			personService.logout();
		} catch (EJBException e) {
			e.printStackTrace();
			//TODO error message
		}
		return "/support/signin?faces-redirect=true";
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
