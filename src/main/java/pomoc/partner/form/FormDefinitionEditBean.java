package pomoc.partner.form;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.annotation.Rule;
import org.ocpsoft.rewrite.faces.annotation.Deferred;

import pomoc.form.FormElement;
import pomoc.partner.form.model.FormDefinition;

@Named
@ViewScoped
@Rule("formDefinitionEdit")
@Join(path="/admin/form/definition/{skey}", to="/support/administration/form-definition.jsf")
public class FormDefinitionEditBean implements Serializable {
	
	private static final Logger log = Logger.getLogger(FormDefinitionEditBean.class.getName()); 
	private static final long serialVersionUID = 1L;

	@Parameter
	@Deferred
    private String skey;
    
    @Produces
    @Named
    private FormDefinition formDefinition;
    
    private Integer version;
    
	@Inject
	private FormsService fs;
	
    @RequestAction
	@Deferred
	public void loadForm() throws IOException
	{
    	log.info("loadForm() with skey=" + skey);
    	if (formDefinition != null) {
    		return;
    	}
		if (skey != null) {
			formDefinition = fs.getFormDefinition(skey);
			version = formDefinition.getFormVersion();
	    } else {
	    	formDefinition = new FormDefinition();
	    }
		
	}

    public String save() {
    	//TODO increment form version only on a relevant change
    	
    	formDefinition.setFormVersion(formDefinition.getFormVersion() + 1);
    	formDefinition = fs.save(formDefinition);
    	return "/support/administration/form.jsf?faces-redirect=true&skey="+skey;
    }
    
    
    public String addField(String elementType) {
    	if (formDefinition == null || elementType == null) {
    		return null;
    	}
    	List<FormElement> elements = formDefinition.getElements();
    	if (elements == null) {
    		elements = new ArrayList<FormElement>();
    	}

    	FormElement element = FormElement.create(elementType);
    	
    	if (element != null) {
    		log.info("Adding new element with type: " + elementType + " to the existing List of " + elements.size() + " elements.");
    		element.setForm(formDefinition);
    		elements.add(element);
    	}
    	formDefinition.setElements(elements);
    	return null;
    }
    
    public void removeField(FormElement element) {
    	List<FormElement> elements = formDefinition.getElements();
    	if (elements != null) {
    		elements.remove(element);
    	}
    	formDefinition.setElements(elements);
    }
    
    public void moveUp(FormElement element) {
    	List<FormElement> elements = formDefinition.getElements();
    	if (elements != null) {
    		int idx = elements.indexOf(element);
    		if (idx > 0) {
    			Collections.swap(elements, idx, idx-1);
    		}
    	}
    	formDefinition.setElements(elements);
    }
    
    public void moveDown(FormElement element) {
    	List<FormElement> elements = formDefinition.getElements();
    	if (elements != null) {
    		int idx = elements.indexOf(element);
    		if (idx > -1 && idx < elements.size()-1) {
    			Collections.swap(elements, idx, idx+1);
    		}
    	}
    	formDefinition.setElements(elements);
    }
    
	public FormDefinition getFormDefinition() {
		log.info("getFormDefinition()");
		return formDefinition;
	}

	public void setFormDefinition(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

}
