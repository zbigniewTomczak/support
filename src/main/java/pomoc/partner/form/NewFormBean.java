package pomoc.partner.form;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class NewFormBean {
	
	@Inject
	private FormsService fs;
	
	private String name;

    public String newForm() {
    	if (name == null || name.length() == 0) {
    		return null;
    	}
    	
    	String skey = fs.newForm(name);
    	return "/support/administration/form.jsf?faces-redirect=true&skey="+skey;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
