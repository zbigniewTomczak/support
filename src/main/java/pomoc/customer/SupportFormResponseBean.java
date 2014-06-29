package pomoc.customer;

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

import pomoc.company.form.SupportFormService;
import pomoc.form.FormStyle;
import pomoc.partner.SupportForm;
import pomoc.util.faces.FacesMessage;

@Model
public class SupportFormResponseBean {
	
	@Inject
	private SupportFormResponseService supportFormResponseService;
	@Inject
	private SupportFormService supportFormService;
	@Inject
	private FacesContext facesContext;
	@Inject
	private Logger logger;
	@Inject
	private FacesMessage facesMessage;
	
	@Produces
	@Named
	private SupportFormResponse supportFormResponse = new SupportFormResponse();

	private SupportForm supportForm;
	
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
		SupportForm sF = supportFormService.getSupportForm(key);
		if (sF == null) {
			//todo post faces error
			return;			
		}
		supportForm = sF;
		formFile = supportFormService.getFormFile(key);
	}

	public void checkKey(ComponentSystemEvent event) throws IOException {
		if (supportForm == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
		    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/noKey?faces-redirect=true");
		}
	}
	
	public String newSupportFormResponse() {
		String key = facesContext.getExternalContext().getRequestParameterMap().get("key");
		logger.info("Creating ticket for form key: " + key);
		if (key == null) {
			facesMessage.postError("We are sorry. We cannot perform your request.");
			return null;
		}
		
		try {
			supportFormResponseService.saveNewFormResponse(supportFormResponse, key);
		} catch (EJBException e) {
			facesMessage.postError("Wystąpił błąd. Sróbuj ponownie później.");
			e.printStackTrace();
			return null;
		}
		
		supportFormResponse = new SupportFormResponse();
		if (supportForm != null && supportForm.getConfirmationMessage() != null) {
			facesMessage.postInfo(supportForm.getConfirmationMessage());
		}
		return null;
	}

	@Produces
	@Named
	public FormStyle getFormStyle() {
		if (formStyle == null) {
			formStyle = new FormStyle(supportForm);
		}
		return formStyle;
	}
	
	public SupportFormResponse getSupportFormResponse() {
		return supportFormResponse;
	}

	public void setSupportFormResponse(SupportFormResponse supportFormResponse) {
		this.supportFormResponse = supportFormResponse;
	}

	public SupportForm getSupportForm() {
		return supportForm;
	}

	public String getFormFile() {
		if (formFile == null) {
			return "noKey.xhtml";
		}
		return formFile;
	}
	
}
