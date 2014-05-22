package pomoc.customer;

import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.company.SupportForm;
import pomoc.company.form.SupportFormService;

@Model
public class SupportFormBean {

	@Produces
	@Named
	private SupportFormResponse supportFormResponse = new SupportFormResponse();
	
	@Inject
	private SupportFormService supportFormService;
	
	@Inject
	private SupportFormResponseService supportFormResponseService;

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private Logger logger;

	public void newSupportFormResponse() {
		String key = facesContext.getExternalContext().getRequestParameterMap().get("key");
		logger.info("Creating ticket for form key: " + key);
		if (key == null) {
			String message = "We are sorry. We cannot perform your request.";
			facesContext.addMessage(message, new FacesMessage(FacesMessage.SEVERITY_ERROR, message,message));
			return;
		}
		
		SupportForm ticketForm = supportFormService.get(key);
		supportFormResponse.setSupportForm(ticketForm);
		supportFormResponseService.save(supportFormResponse);
		
		// TODO message to user
	}

	public SupportFormResponse getSupportFormResponse() {
		return supportFormResponse;
	}

	public void setSupportFormResponse(SupportFormResponse supportFormResponse) {
		this.supportFormResponse = supportFormResponse;
	}


	
}
