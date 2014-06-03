package pomoc.util.faces;

import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class FacesMessage {
	@Inject
	private FacesContext fc;
	
	public void postError(String message) {
		postMessage(message, javax.faces.application.FacesMessage.SEVERITY_ERROR);
	}
	
	public void postInfo(String message) {
		postMessage(message, javax.faces.application.FacesMessage.SEVERITY_INFO);
	}

	private void postMessage(String message, Severity severity) {
		javax.faces.application.FacesMessage fm = new javax.faces.application.FacesMessage(severity, message, message);
		fc.addMessage(null, fm);
	}
}
