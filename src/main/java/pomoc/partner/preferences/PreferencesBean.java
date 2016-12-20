package pomoc.partner.preferences;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.partner.PartnerService;
import pomoc.partner.login.LoggedPersonService;
import pomoc.util.faces.FacesMessage;

@Model
public class PreferencesBean {
	@Inject
	private LoggedPersonService loggedPersonService;
	
	@Inject
	private PartnerService partnerService;
	
	@Inject
	private FacesMessage fm;
	
	private Preferences preferences;

	@PostConstruct
	public void init() {
		if (loggedPersonService.getLoggedPerson() != null) {
			preferences = partnerService.getPreferences(loggedPersonService.getLoggedPerson());
		} 
		if (preferences  == null) {
			preferences = new Preferences();
		}
	}
	
	public String save() {
		try {
			partnerService.save(preferences);
			fm.postInfo("Zapisano ustawienia");
		} catch (EJBException e) {
			fm.postError("Błąd zapisu");
		}
		return "/support/dashboard2?faces-redirect=true";
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}
	
	
}
