package pomoc.company.form;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import pomoc.partner.SupportForm;
import pomoc.partner.login.LoggedPersonService;
import pomoc.util.faces.FacesMessage;

@Model
public class SupportFormBean {

	private SupportForm selectedForm;
	
	@Inject
	private LoggedPersonService loggedPersonService;
	
	@Inject
	private FacesMessage facesMessage;
	
	@Inject
	private SupportFormService formService;
	
	private List<SupportFormListData> partnerForms;
	
	public String newSupportForm() {
		return "forms?faces-redirect=true";
	}
	
	public List<SupportFormListData> getPartnerForms() {
		if (partnerForms == null) {
			partnerForms = 
					formService.getPartnerForms(loggedPersonService
							.getLoggedPerson());
		}
		return partnerForms;
	}
	
	public void onRowSelect(SelectEvent e) {
		if (e.getObject() instanceof SupportForm) {
			SupportForm form = (SupportForm) e.getObject();
			if (form.getKey() != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
			    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/support/ticket?faces-redirect=true&id="+form.getKey());
			}
		}
		facesMessage.postError("Wystąpił błąd. Nie można przejść do wybranego formularza.");
			
	}

	public SupportForm getSelectedForm() {
		return selectedForm;
	}

	public void setSelectedForm(SupportForm selectedForm) {
		this.selectedForm = selectedForm;
	}
	
	
}
