package pomoc.util.faces;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class FacesMessage {
	@Inject
	private FacesContext fc;
	
	@Inject
	private Logger log =  Logger.getGlobal();
	
	public void postError(String message) {
		postMessage(message, javax.faces.application.FacesMessage.SEVERITY_ERROR);
		keep();
	}
	
	public void postWarning(String message) {
		postMessage(message, javax.faces.application.FacesMessage.SEVERITY_WARN);
		keep();
	}
	
	public void postInfo(String message) {
		postMessage(message, javax.faces.application.FacesMessage.SEVERITY_INFO);
		keep();
	}

	private void postMessage(String message, Severity severity) {
		javax.faces.application.FacesMessage fm = new javax.faces.application.FacesMessage(severity, message, message);
		fc.addMessage(null, fm);
	}

	public void postError(String string, Exception e) {
		log.log(Level.SEVERE, string, e);
		postError(string);
		
	}

	public void keep() {
		fc.getExternalContext().getFlash().setKeepMessages(true);
	}
}
