package pomoc.partner.person;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.partner.Partner;
import pomoc.partner.admin.users.UserAccessModel;
import pomoc.partner.form.FormPublicationService;
import pomoc.partner.login.Current;

@Model
public class MyProfileBean {
	@Inject @Current
	private Person loggedPerson;
	
	private UserAccessModel userAccessModel = new UserAccessModel();
	
	@Inject @Current 
	private Partner partner;
	
	@Inject
	private FormPublicationService formService;
	
	@Inject
	private PersonService personService;
	
	@PostConstruct
	public void init() {
		if (loggedPerson == null) {
			return;
		}
		if (loggedPerson.isAdmin()) {
			userAccessModel = UserAccessModel.getWithAllRights(formService.getPartnerForms(partner));
		} else {
			userAccessModel = new UserAccessModel(formService.getPartnerForms(partner), 
					personService.getFormRights(loggedPerson));
		}
	}

	public UserAccessModel getUserAccessModel() {
		return userAccessModel;
	}
	
	
}
