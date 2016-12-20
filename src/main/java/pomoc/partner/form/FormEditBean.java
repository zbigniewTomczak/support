package pomoc.partner.form;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.annotation.Rule;
import org.ocpsoft.rewrite.faces.annotation.Deferred;

import pomoc.partner.form.model.FormPublication;
import pomoc.partner.form.sla.SlaPlan;

@Model
@Rule("formEdit")
@Join(path="/admin/form/presentation/{skey}", to="/support/administration/form.jsf")
public class FormEditBean {
	private static final Logger log = Logger.getLogger(FormEditBean.class.getName()); 
    @Parameter
    private String skey;
    
    @Produces
    @Named
    private FormPublication formPublication;
    
	@Inject
	private FormsService fs;
	@Produces
	@Named
	private String pasteCode;
	
	Map<SlaPlan, Integer> slaPlans = new HashMap<>();
	
	private List<SelectItem> slaHours = new ArrayList<>(Arrays.asList(
			new SelectItem(null, "Bez planu SLA"),
			new SelectItem(1, "1 godzina"),
			new SelectItem(2, "2 godziny"),
			new SelectItem(4, "4 godziny"),
			new SelectItem(8, "8 godzin"),
			new SelectItem(12, "12 godzin"),
			new SelectItem(24, "24 godzin"),
			new SelectItem(36, "36 godzin"),
			new SelectItem(48, "48 godzin")
			));
	
    @RequestAction
	@Deferred
	public void loadForm() throws IOException
	{
    	log.info("loadForm() with skey="+skey);
    	if (formPublication != null) {
    		return;
    	}
		if (skey != null) {
			formPublication = fs.getFormPublication(skey);
	    } else {
	    	formPublication = new FormPublication();
	    }
		slaPlans = formPublication.getSlaPlans();
		pasteCode = getPasteCode();
	}

    public String save() {
    	formPublication = fs.save(formPublication);
    	return "/support/administration/forms.jsf?faces-redirect=true";
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
    
	private String getPasteCode() throws MalformedURLException {
	    if (formPublication!= null && formPublication.getKey() != null) { 
	    	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
	    			.getExternalContext().getRequest();
	    	URL url = new URL(request.getRequestURL().toString());
	    	URL newUrl = new URL(url.getProtocol(),
	    			url.getHost(),
	    			url.getPort(),
	    			request.getContextPath());
	    	String sUrl  = newUrl.toString();
	    	String pasteCode = String.format("<iframe src=\"%s/index.jsf?key=%s\"", sUrl, formPublication.getKey());
	    	if (formPublication.getWidth() != null && formPublication.getWidth() > 0) {
	    		pasteCode += String.format(" width=\"%d\"",formPublication.getWidth()); 
	    	}
	    	if (formPublication.getHeight() != null && formPublication.getHeight() > 0) {
	    		pasteCode += String.format(" height=\"%d\"",formPublication.getHeight()); 
	    	}

	    	pasteCode += " scrolling=\"no\" frameBorder=\"0\"></iframe>";
	    	return pasteCode;
	    }
	    
	    return "Błąd w generacji kodu";
	}

	public Integer getOpeningPlanHours() {
		return slaPlans.get(SlaPlan.FOR_OPENING);
	}

	public void setOpeningPlanHours(Integer hours) {
		if (hours != null) {
			slaPlans.put(SlaPlan.FOR_OPENING, hours);
		}
	}

	public Integer getResolvingPlanHours() {
		return slaPlans.get(SlaPlan.FOR_RESOLVING);
	}

	public void setResolvingPlanHours(Integer hours) {
		if (hours != null) {
			slaPlans.put(SlaPlan.FOR_RESOLVING, hours);
		}
	}

	public List<SelectItem> getSlaHours() {
		return slaHours;
	}

	public void setSlaHours(List<SelectItem> slaHours) {
		this.slaHours = slaHours;
	}

}
