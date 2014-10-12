package pomoc.partner.login;

import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;
import pomoc.util.faces.FacesMessage;

@Model
public class LoginBean {

	@Inject
	private Logger log;
	
	private String email;
	private String password;
	
	@Inject
	private PersonService personService;
	
	@Inject
	private FacesMessage facesMessage;
	
	public String login() {
		Person person;
		try {
			person = personService.loginPerson(email, password);
		} catch (EJBException e) {
			facesMessage.postError("Błąd logowania. " + e.getCause().getMessage());
			e.printStackTrace();
			return null;
		}
		if (person == null) {
			facesMessage.postError("Nieudane logowanie. Sprawdź email i hasło i spróbuj ponownie.");
			return null;
		}
		return "/support/dashboard2.jsf?faces-redirect=true";
	}

	public String logout() {
		log.info("Logging out");
		try {
			personService.logout();
		} catch (EJBException e) {
			e.printStackTrace();
			//TODO error message
		}
		return "signin2.jsf?faces-redirect=true";
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
