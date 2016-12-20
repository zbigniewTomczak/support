package pomoc.partner;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Rule;

import pomoc.partner.person.Person;
import pomoc.partner.person.PersonRegistrationService;
import pomoc.util.faces.FacesMessage;

@Model
@Rule("new-partner")
@Join(path = "/signup", to = "/support/signup2.jsf")
public class NewPartnerBean {
	
	@Produces
	@Named
	private Partner newPartner = new Partner();
	
	@Produces
	@Named
	private Person newAdmin = new Person();
	
	@Inject
	private PersonRegistrationService personRegistrationService;
	
	@Inject
	private FacesMessage facesMessage;
	
	@Inject 
	private FacesContext facesContext;
	
	private String passwordConfirm = "";
	
	public String register() {
		if (!passwordConfirm.equals(newAdmin.getPassword())) {
			facesMessage.postError("Hasła muszą być zgodne.");
			return null;
		}
		
		try {
			personRegistrationService.registerNewPartner(newAdmin, newPartner);
		} catch (EJBException e) {
			Throwable t = e.getCause();
			facesMessage.postError("Błąd rejestracji. " + t.getMessage());
			e.printStackTrace();
			return null;
		}
		facesMessage.postInfo("Rejestracja przebiegła pomyślnie. Zaloguj się na swoje konto.");
		return "/support/signin2?faces-redirect=true";
	}

	public Partner getNewPartner() {
		return newPartner;
	}

	public void setNewPartner(Partner newPartner) {
		this.newPartner = newPartner;
	}

	public Person getNewAdmin() {
		return newAdmin;
	}

	public void setNewAdmin(Person newAdmin) {
		this.newAdmin = newAdmin;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
