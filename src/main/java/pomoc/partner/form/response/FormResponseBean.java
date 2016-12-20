package pomoc.partner.form.response;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.form.FormElement;
import pomoc.form.FormStyle;
import pomoc.partner.form.FormDefinitionService;
import pomoc.partner.form.FormPublicationService;
import pomoc.partner.form.model.FormDefinition;
import pomoc.partner.form.model.FormPublication;
import pomoc.util.faces.FacesMessage;

@Model
public class FormResponseBean {
	
	@Inject
	private FormResponseService formResponseService;
	@Inject
	private FormPublicationService formPublicationService;
	@Inject
	private FormDefinitionService formDefinitionService;
	@Inject
	private FacesContext facesContext;
	@Inject
	private Logger logger;
	@Inject
	private FacesMessage facesMessage;
	
	@Produces
	@Named
	private FormResponse formResponse = new FormResponse();
	
	private Object[] responses;

	private FormPublication formPublication;
	private FormDefinition formDefinition;
	
	private FormStyle formStyle;

	private String formFile;

	private String key;
	
	@PostConstruct
	public void init() {
		key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
		if (key == null) {
			formFile = "noKey.xhtml";
			return;
		}
		// try
		formPublication = formPublicationService.getFormPublicationFormPublication(key);
		if (formPublication == null) {
			//todo post faces error
			return;			
		}
		formDefinition = formDefinitionService.getFormDefinition(formPublication);
		responses = new Object[formDefinition.getElements().size()];
		formFile = formPublicationService.getFormFile(key);
	}

	public void checkKey(ComponentSystemEvent event) throws IOException {
		if (formPublication== null) {
			FacesContext fc = FacesContext.getCurrentInstance();
		    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/noKey?faces-redirect=true");
		}
	}
	
	public String newFormResponse() {
		String key = facesContext.getExternalContext().getRequestParameterMap().get("key");
		logger.info("Creating ticket for form key: " + key + " with values: " + Arrays.toString(responses));
		if (key == null) {
			facesMessage.postError("We are sorry. We cannot perform your request.");
			return null;
		}
		
		
		
		formResponse = new FormResponse();
		formResponse.setFormDefinition(formDefinition);
		List<FormElement> elements = formDefinition.getElements();
		Map<FormElement, String> responses = new HashMap<>();
		for(int i=0; i < elements.size(); i++) {
			if (this.responses[i] != null) {
				responses.put(elements.get(i), this.responses[i].toString());
			}
		}
		formResponse.setResponses(responses);
		try {
			formResponseService.saveNewFormResponse(formResponse, key);
		} catch (EJBException e) {
			facesMessage.postError("Wystąpił błąd. Sróbuj ponownie później.");
			e.printStackTrace();
			return null;
		}
		if (formPublication!= null && formPublication.getConfirmationMessage() != null) {
			facesMessage.postInfo(formPublication.getConfirmationMessage());
			this.responses = new Object[formDefinition.getElements().size()];
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
		return formFile;
	}

	public FormDefinition getFormDefinition() {
		return formDefinition;
	}

	public Object[] getResponses() {
		return responses;
	}

	public void setResponses(Object[] responses) {
		this.responses = responses;
	}
	
}
