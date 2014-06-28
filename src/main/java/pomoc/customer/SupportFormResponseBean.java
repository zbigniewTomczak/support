package pomoc.customer;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.company.form.SupportFormService;
import pomoc.partner.SupportForm;
import pomoc.util.faces.FacesMessage;

@Model
public class SupportFormResponseBean {

	@Produces
	@Named
	private SupportFormResponse supportFormResponse = new SupportFormResponse();
	
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
	
	private SupportForm supportForm;
	
	private Integer width;
	private Integer height;
	
	@PostConstruct
	public void init() {
		String key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
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

	public SupportFormResponse getSupportFormResponse() {
		return supportFormResponse;
	}

	public void setSupportFormResponse(SupportFormResponse supportFormResponse) {
		this.supportFormResponse = supportFormResponse;
	}

	public SupportForm getSupportForm() {
		return supportForm;
	}

	public int getWidth() {
		if (width == null && supportForm != null) {
			width = (int) Math.round(supportForm.getWidth() * 0.98);
		}
		return width;
	}

	public int getHeight() {
		if (height  == null && supportForm != null) {
			height = (int) Math.round(supportForm.getHeight() * 0.98);
		}
		return height;
	}
	
	
}
