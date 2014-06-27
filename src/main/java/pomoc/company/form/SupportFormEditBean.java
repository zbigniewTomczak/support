package pomoc.company.form;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import pomoc.partner.SupportForm;
import pomoc.partner.login.LoggedPersonService;
import pomoc.util.faces.FacesMessage;

@Model
public class SupportFormEditBean {

	@Inject
	private FacesMessage facesMessage;
	
	@Inject
	private SupportFormService supportFormService;
	
	@Inject
	private LoggedPersonService loggedPersonService;
	
	private SupportForm supportForm;
	
	@PostConstruct
	public void init() {
		String key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
		if (key == null) {
			//todo post faces error
			return;
		}
		if (loggedPersonService.getLoggedPerson() == null) {
			//todo post faces error
			return;
		}
		// try
		SupportForm sF = supportFormService.getSupportForm(key);
		if (sF == null) {
			//todo post faces error
			return;			
		}
		if (!supportFormService.hasAccess(sF, loggedPersonService.getLoggedPerson())) {
			//todo post faces error
			return;
		}
		
		supportForm = sF;
	}

	public String save() {
		return null;
	}
	
	public SupportForm getSupportForm() {
		return supportForm;
	}

	public void setSupportForm(SupportForm supportForm) {
		this.supportForm = supportForm;
	}
	
	
}
