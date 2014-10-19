package pomoc.partner.form;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import pomoc.partner.login.LoggedPersonService;
import pomoc.util.faces.FacesMessage;

@Model

public class FormPublicationBean {
	
	@Inject
	private LoggedPersonService loggedPersonService;
	@Inject
	private FacesMessage facesMessage;
	@Inject
	private FormPublicationService formService;
	
	private List<FormPublicationListData> partnerForms;
	
	private FormPublicationListData selectedForm;

	public List<FormPublicationListData> getPartnerForms() {
		if (partnerForms == null) {
			partnerForms = 
					formService.getPartnerForms(loggedPersonService
							.getLoggedPerson());
		}
		return partnerForms;
	}
	
	public void onRowSelect(SelectEvent e) {
		if (e.getObject() instanceof FormPublicationListData) {
			FormPublicationListData form = (FormPublicationListData) e.getObject();
			if (form.getKey() != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
			    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/support/administration/form?faces-redirect=true&key="+form.getKey());
			    return;
			}
		}
		facesMessage.postError("Wystąpił błąd. Nie można przejść do wybranego formularza.");
			
	}

	public FormPublicationListData getSelectedForm() {
		return selectedForm;
	}

	public void setSelectedForm(FormPublicationListData selectedForm) {
		this.selectedForm = selectedForm;
	}
}
