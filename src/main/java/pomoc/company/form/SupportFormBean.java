package pomoc.company.form;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import pomoc.partner.login.LoggedPersonService;
import pomoc.util.faces.FacesMessage;

@Model
public class SupportFormBean {

	private SupportFormListData selectedForm;
	
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
		if (e.getObject() instanceof SupportFormListData) {
			SupportFormListData form = (SupportFormListData) e.getObject();
			if (form.getKey() != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
			    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/support/administration/form?faces-redirect=true&key="+form.getKey());
			    return;
			}
		}
		facesMessage.postError("Wystąpił błąd. Nie można przejść do wybranego formularza.");
			
	}

	public SupportFormListData getSelectedForm() {
		return selectedForm;
	}

	public void setSelectedForm(SupportFormListData selectedForm) {
		this.selectedForm = selectedForm;
	}

	
	
	
}
