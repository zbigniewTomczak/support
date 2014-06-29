package pomoc.form;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Model
public class Configuration {

	@Inject 
	private FacesContext facesContext;
	
	public String getNoKeyWarning() {
		if(!facesContext.getExternalContext().getRequestParameterMap().containsKey("key")) {
			return "Uwaga! Formularz jest niepoprawnie skonfigurowany. Proszę ustawić parametr 'key'.";
		}
		return null;
	}
}
