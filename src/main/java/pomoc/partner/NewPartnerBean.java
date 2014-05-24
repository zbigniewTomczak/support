package pomoc.partner;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.partner.person.Person;
import pomoc.partner.person.PersonRegistrationService;

@Model
public class NewPartnerBean {
	
	@Produces
	@Named
	private Partner newPartner = new Partner();
	
	@Produces
	@Named
	private Person newAdmin = new Person();
	
	@Inject
	private PersonRegistrationService personRegistrationService;
	
	private String passwordConfirm = "";
	
	public String register() {
		if (!passwordConfirm.equals(newAdmin.getPassword())) {
			//TODO Faces message to user
		}
		
		//newAdmin.setRole(Roles.ADMIN);
		//newAdmin.setPartner(newPartner);
		try {
			personRegistrationService.registerNewAdmin(newAdmin, newPartner);
		} catch (EJBException e) {
			//TODO error message
			e.printStackTrace();
			return null;
		}
		//TODO success message
		return "/support/administration/newform?faces-redirect=true";
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
