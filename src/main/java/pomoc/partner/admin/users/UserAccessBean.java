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

import pomoc.company.form.SupportFormService;
import pomoc.partner.Partner;
import pomoc.partner.login.Current;
import pomoc.partner.login.LoggedPersonService;
import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;
import pomoc.util.faces.FacesMessage;

@Model
@Rule("useraccess")
@Join(path = "/admin/useraccess/{id}", to = "/support/administration/useraccess.jsf")
public class UserAccessBean {

    @Parameter
    private Long id;
	
    private Person user = new Person();
    
    private UserAccessModel userAccessModel = new UserAccessModel();
    
	@Inject
	private FacesMessage fm;
	
	@Inject
	private PersonService personService;
	
	@Inject
	private LoggedPersonService loggedPersonService;
	
	@Inject @Current 
	private Person loggedUser;
	
	@Inject @Current 
	private Partner partner;
	
	@Inject
	private SupportFormService formService;
	
	private boolean admin;

	private boolean disabled;
	
	@RequestAction
	@Deferred
	public void loadUser() throws IOException
	{
		if (id != null) {
		  user = personService.getUserById(id);
		  admin = user.isAdmin();
	    }
		if (admin) {
			disabled = true;
			userAccessModel = UserAccessModel.getWithAllRights(formService.getPartnerForms(partner));
		} else {
			userAccessModel = new UserAccessModel(formService.getPartnerForms(partner), 
					personService.getFormRights(loggedUser));
		}
		
	}

	public String save() {
			try {
				if (admin) {
					fm.postError("Cannot edit admin rights");
					return null;
				} 
				if (user.getId() != null) {
					user.setFormRights(userAccessModel.getFormRights());
					user = personService.saveUser(user);
				} 
			} catch (EJBException e) {
				fm.postError("Cannot Save user ("+ e.getMessage() + ")", e);
				return null;
			}
			return "/support/administration/users.jsf?faces-redirect=true";
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

	public UserAccessModel getUserAccessModel() {
		return userAccessModel;
	}

	public void setUserAccessModel(UserAccessModel userAccessModel) {
		this.userAccessModel = userAccessModel;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	
	
	
}
