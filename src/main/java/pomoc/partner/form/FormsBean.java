package pomoc.partner.form;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Rule;

import pomoc.partner.form.model.FormDefinition;
import pomoc.partner.form.qualifier.AllForms;

@Model
@Rule("forms")
@Join(path="/admin/forms", to="/support/administration/forms.jsf")
public class FormsBean {
	@Inject @AllForms
	private List<FormDefinition> allForms;
	
	@Produces
	@Named
	public List<FormDefinition> getAllForms() {
		return allForms;
	}
}
