package pomoc.partner.form.response;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.form.FormStyle;
import pomoc.partner.form.FormPublicationService;
import pomoc.partner.form.model.FormPublication;
import pomoc.util.faces.FacesMessage;

@Model
public class FormResponseBean {
	
	@Inject
	private FormResponseService formResponseService;
	@Inject
	private FormPublicationService formPublicationervice;
	@Inject
	private FacesContext facesContext;
	@Inject
	private Logger logger;
	@Inject
	private FacesMessage facesMessage;
	
	@Produces
	@Named
	private FormResponse formResponse = new FormResponse();

	private FormPublication formPublication;
	
	private FormStyle formStyle;

	private String formFile;

	private String key;
	
	@PostConstruct
	public void init() {
		key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
		if (key == null) {
			//todo post faces error
			return;
		}
		// try
		FormPublication sF = formPublicationervice.getFormPublicationFormPublication(key);
		if (sF == null) {
			//todo post faces error
			return;			
		}
		formPublication= sF;
		formFile = formPublicationervice.getFormFile(key);
	}

	public void checkKey(ComponentSystemEvent event) throws IOException {
		if (formPublication== null) {
			FacesContext fc = FacesContext.getCurrentInstance();
		    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/noKey?faces-redirect=true");
		}
	}
	
	public String newFormResponse() {
		String key = facesContext.getExternalContext().getRequestParameterMap().get("key");
		logger.info("Creating ticket for form key: " + key);
		if (key == null) {
			facesMessage.postError("We are sorry. We cannot perform your request.");
			return null;
		}
		
		try {
			formResponseService.saveNewFormResponse(formResponse, key);
		} catch (EJBException e) {
			facesMessage.postError("Wystąpił błąd. Sróbuj ponownie później.");
			e.printStackTrace();
			return null;
		}
		
		formResponse = new FormResponse();
		if (formPublication!= null && formPublication.getConfirmationMessage() != null) {
			facesMessage.postInfo(formPublication.getConfirmationMessage());
		}
		return null;
	}

	@Produces
	@Named
	public FormStyle getFormStyle() {
		if (formStyle == null) {
			formStyle = new FormStyle(formPublication);
		}
		return formStyle;
	}
	
	public FormResponse getFormResponse() {
		return formResponse;
	}

	public void setFormResponse(FormResponse formResponse) {
		this.formResponse = formResponse;
	}

	public FormPublication getFormPublication() {
		return formPublication;
	}

	public String getFormFile() {
		if (formFile == null) {
			return "noKey.xhtml";
		}
		return formFile;
	}
	
}
