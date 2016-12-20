package pomoc.email;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.customer.communication.CommunicationService;
import pomoc.partner.Partner;
import pomoc.partner.login.Current;
import pomoc.util.faces.FacesMessage;

@Model
public class EmailBean {
	
	@Inject
	private EmailParser emailParser;
	@Inject
	private FacesMessage facesMessage;
	
	@Inject
	private CommunicationService cs;
	
	@Produces
	@Named
	private MailboxCheckHistory lastCheck;
	
	@Inject @Current
	private Partner partner; 
	
	@PostConstruct
	public void init() {
		if (partner != null) {
			lastCheck = cs.getLastCheck(partner);
		}
	}
	
	public String check() {
		try {
			emailParser.checkMailbox();
			facesMessage.postError("Rozpoczęto sprawdzonie poczty.");
		} catch (Exception e) {
			e.printStackTrace();
			facesMessage.postError("Wystąpił błąd podczas sprawdzania poczty.");
		}
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		return viewId + "?faces-redirect=true";
	}
}
