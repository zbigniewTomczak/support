package pomoc.partner.form;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import pomoc.partner.form.model.FormPublication;
import pomoc.partner.login.LoggedPersonService;
import pomoc.util.faces.FacesMessage;

@Model
public class FormPublicationEditBean {

	@Inject
	private FacesMessage facesMessage;
	@Inject
	private FormPublicationService formPublicationService;
	@Inject
	private LoggedPersonService loggedPersonService;
	@Inject
	private FacesContext facesContext;
	
	private FormPublication formPublication;
	
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
		FormPublication sF = formPublicationService.getFormPublicationFormPublication(key);
		if (sF == null) {
			//todo post faces errorS
			return;			
		}
		if (!formPublicationService.hasAccess(sF, loggedPersonService.getLoggedPerson())) {
			//todo post faces error
			return;
		}
		formPublication = sF;
	}

	

	public String save() {
		try {
			formPublicationService.save(formPublication);
		} catch (EJBException e) {
			facesMessage.postError("Wystąpił błąd podczas zapisywania.");
		}
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		try {
			facesMessage.postInfo("Zmiany zostały zapisane. Wymagane jest ponowne wklejenie kodu na stronę: " + getPasteCode());
		} catch (MalformedURLException e) {
			facesMessage.postError("Błąd generacji kodu");
		}
		return "/support/administration/forms?faces-redirect=true";
	}
	
	public FormPublication getFormPublication() {
		return formPublication;
	}

	public void setFormPublication(FormPublication formPublication) {
		this.formPublication= formPublication;
	}
	
	public String getPasteCode() throws MalformedURLException {
	    if (formPublication!= null && formPublication.getKey() != null) { 
	    	HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
	    	URL url = new URL(request.getRequestURL().toString());
	    	URL newUrl = new URL(url.getProtocol(),
	    			url.getHost(),
	    			url.getPort(),
	    			request.getContextPath());
	    	String sUrl  = newUrl.toString();
	    	String pasteCode = String.format("<iframe src=\"%s/index.jsf?key=%s\"", sUrl, formPublication.getKey());
	    	if (formPublication.getWidth() != null && formPublication.getWidth() > 0) {
	    		pasteCode += String.format(" width=\"%d\"",formPublication.getWidth()); 
	    	}
	    	if (formPublication.getHeight() != null && formPublication.getHeight() > 0) {
	    		pasteCode += String.format(" height=\"%d\"",formPublication.getHeight()); 
	    	}

	    	pasteCode += " scrolling=\"no\" frameBorder=\"0\"></iframe>";
	    	return pasteCode;
	    }
	    
	    return "Błąd w generacji kodu";
	}

	public String getPreviewCode() throws MalformedURLException {
	    if (formPublication!= null && formPublication.getKey() != null) { 
	    	HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
	    	URL url = new URL(request.getRequestURL().toString());
	    	URL newUrl = new URL(url.getProtocol(),
	    			url.getHost(),
	    			url.getPort(),
	    			request.getContextPath());
	    	String sUrl  = newUrl.toString();
	    	String pasteCode = String.format("<iframe id=\"formFrame\" src=\"%s/index.jsf?key=%s\"", sUrl, formPublication.getKey());
	    	if (formPublication.getWidth() != null && formPublication.getWidth() > 0) {
	    		pasteCode += String.format(" width=\"%d\"",formPublication.getWidth()); 
	    	}
	    	if (formPublication.getHeight() != null && formPublication.getHeight() > 0) {
	    		pasteCode += String.format(" height=\"%d\"",formPublication.getHeight()); 
	    	}

	    	pasteCode += " scrolling=\"no\" frameBorder=\"0\"></iframe>";
	    	return pasteCode;
	    }
	    
	    return "Błąd w generacji podglądu";
	}
	
}
