package pomoc.partner.preferences;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.partner.PartnerService;
import pomoc.partner.login.LoggedPersonService;

@Model
public class PreferencesBean {
	@Inject
	private LoggedPersonService loggedPersonService;
	
	@Inject
	private PartnerService partnerService;
	
	
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
		partnerService.save(preferences);
		return "/support/dashboard";
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}
	
	
}
