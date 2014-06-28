package pomoc.company.form;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import pomoc.partner.SupportForm;
import pomoc.partner.login.LoggedPersonService;
import pomoc.util.faces.FacesMessage;

@Model
public class SupportFormEditBean {

	@Inject
	private FacesMessage facesMessage;
	
	@Inject
	private SupportFormService supportFormService;
	
	@Inject
	private LoggedPersonService loggedPersonService;
	
	@Inject
	private FacesContext facesContext;
	
	private SupportForm supportForm;
	
	
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
		SupportForm sF = supportFormService.getSupportForm(key);
		if (sF == null) {
			//todo post faces error
			return;			
		}
		if (!supportFormService.hasAccess(sF, loggedPersonService.getLoggedPerson())) {
			//todo post faces error
			return;
		}
		supportForm = sF;
	}

	public String save() {
		try {
			supportFormService.save(supportForm);
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
	
	public SupportForm getSupportForm() {
		return supportForm;
	}

	public void setSupportForm(SupportForm supportForm) {
		this.supportForm = supportForm;
	}
	
	public String getPasteCode() throws MalformedURLException {
	    if (supportForm != null && supportForm.getKey() != null) { 
	    	HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
	    	URL url = new URL(request.getRequestURL().toString());
	    	URL newUrl = new URL(url.getProtocol(),
	    			url.getHost(),
	    			url.getPort(),
	    			request.getContextPath());
	    	String sUrl  = newUrl.toString();
	    	String pasteCode = String.format("<iframe src=\"%s/index.jsf?key=%s\"", sUrl, supportForm.getKey());
	    	if (supportForm.getWidth() != null && supportForm.getWidth() > 0) {
	    		pasteCode += String.format(" width=\"%d\"",supportForm.getWidth()); 
	    	}
	    	if (supportForm.getHeight() != null && supportForm.getHeight() > 0) {
	    		pasteCode += String.format(" height=\"%d\"",supportForm.getHeight()); 
	    	}

	    	pasteCode += " scrolling=\"no\" frameBorder=\"0\"></iframe>";
	    	return pasteCode;
	    }
	    
	    return "Błąd w generacji kodu";
	}

	public String getPreviewCode() throws MalformedURLException {
	    if (supportForm != null && supportForm.getKey() != null) { 
	    	HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
	    	URL url = new URL(request.getRequestURL().toString());
	    	URL newUrl = new URL(url.getProtocol(),
	    			url.getHost(),
	    			url.getPort(),
	    			request.getContextPath());
	    	String sUrl  = newUrl.toString();
	    	String pasteCode = String.format("<iframe id=\"formFrame\" src=\"%s/index.jsf?key=%s\"", sUrl, supportForm.getKey());
	    	if (supportForm.getWidth() != null && supportForm.getWidth() > 0) {
	    		pasteCode += String.format(" width=\"%d\"",supportForm.getWidth()); 
	    	}
	    	if (supportForm.getHeight() != null && supportForm.getHeight() > 0) {
	    		pasteCode += String.format(" height=\"%d\"",supportForm.getHeight()); 
	    	}

	    	pasteCode += " scrolling=\"no\" frameBorder=\"0\"></iframe>";
	    	return pasteCode;
	    }
	    
	    return "Błąd w generacji podglądu";
	}
	
}
