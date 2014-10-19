package pomoc.partner.form;

import java.io.IOException;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.annotation.Rule;
import org.ocpsoft.rewrite.faces.annotation.Deferred;

import pomoc.partner.form.model.FormPublication;

@Model
@Rule("formEdit")
@Join(path="/admin/form/presentation/{skey}", to="/support/administration/form.jsf")
public class FormEditBean {
	
    @Parameter
    private String skey;
    
    @Produces
    @Named
    private FormPublication formPublication;
    
	@Inject
	private FormsService fs;
	
    @RequestAction
	@Deferred
	public void loadFormPublication() throws IOException
	{
		if (skey != null) {
		  formPublication = fs.getFormPublication(skey);
	    }
		
	}

    public String savePublication() {
    	return null;
    }
    
	public FormPublication getFormPublication() {
		return formPublication;
	}

	public void setFormPublication(FormPublication formPublication) {
		this.formPublication = formPublication;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}
    
	
    
}
