package pomoc.partner.admin.users;

import java.io.IOException;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.annotation.Rule;
import org.ocpsoft.rewrite.faces.annotation.Deferred;

import pomoc.partner.Partner;
import pomoc.partner.login.LoggedPersonService;
import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;
import pomoc.partner.person.Role;
import pomoc.util.PasswordGenerator;
import pomoc.util.faces.FacesMessage;

@Model
@Rule("useredit")
@Join(path = "/admin/useredit/{id}", to = "/support/administration/useredit.jsf")
public class UserEditBean {

    @Parameter
    private Long id;
	
    private Person user = new Person();
    
	@Inject
	private FacesMessage fm;
	
	@Inject
	private PersonService personService;
	
	@Inject
	private UserValidator userValidator;
	
	@Inject
	private PasswordGenerator passwordGenerator;
	
	@Inject
	private LoggedPersonService loggedPersonService;
	
	private boolean admin;
	
	@RequestAction
	@Deferred
	public void loadUser() throws IOException
	{
		if (id != null) {
		  user = personService.getUserById(id);
		  admin = user.isAdmin();
	    }
		
	}

	   
	public String saveUser() {
		boolean ok = false;
		String samePage = "/support/administration/useredit.jsf?faces-redirect=true&id="+id;
		if (id==null) {
			ok = userValidator.validateNew(user);
		} else {
			ok = userValidator.validate(user);
		}
		if (ok) {
			try {
				Partner partner  = loggedPersonService.getPartnerForLoggedPerson();
				if (admin) {
					user.setRole(Role.ADMIN);
				} else {
					if (user.getId() != null && personService.willBeNoAdmin(user.getId(), partner)) {
						fm.postError("Musi pozostać co najmniej jeden aktywny administrator.");
						return samePage;
					}
					user.setRole(Role.USER);
				}
				if (user.getId() != null) {
					user = personService.saveUser(user);
				} else {
					user.setPartner(partner);
					user.setPassword(passwordGenerator.generatePassword());
					user = personService.saveAndNotifyUser(user);
					fm.postInfo("User "+user.getName()+" created. User password has beed send to " + user.getEmail() + ".");
				}
			} catch (EJBException e) {
				fm.postError("Cannot Save user ("+ e.getMessage() + ")", e);
				return samePage;
			}
			return "/support/administration/users.jsf?faces-redirect=true";
		}
		return samePage;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Person getUser() {
		return user;
	}


	public void setUser(Person user) {
		this.user = user;
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
}
