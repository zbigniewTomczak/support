package pomoc.customer;

import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.util.faces.FacesMessage;

@Model
public class SupportFormResponseBean {

	@Produces
	@Named
	private SupportFormResponse supportFormResponse = new SupportFormResponse();
	
	@Inject
	private SupportFormResponseService supportFormResponseService;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private Logger logger;

	@Inject
	private FacesMessage facesMessage;
	
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
		facesMessage.postInfo("Wiadomość została zarejestrowana w systemie.");
		return null;
	}

	public SupportFormResponse getSupportFormResponse() {
		return supportFormResponse;
	}

	public void setSupportFormResponse(SupportFormResponse supportFormResponse) {
		this.supportFormResponse = supportFormResponse;
	}


	
}
