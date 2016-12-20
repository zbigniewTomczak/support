package pomoc.navigation;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Model
public class Path {

	@Inject
	FacesContext fc;
	
	public Boolean getDashboard() {
		return "/support/dashboard2.xhtml".equalsIgnoreCase(fc.getViewRoot().getViewId());
	}
	
	public Boolean getForms() {
		return "/support/administration/forms.xhtml".equalsIgnoreCase(fc.getViewRoot().getViewId());
	}
	
	public Boolean getMyTickets() {
		return "/support/myTickets.xhtml".equalsIgnoreCase(fc.getViewRoot().getViewId());
	}
}
