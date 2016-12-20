package pomoc.partner.admin.users;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pomoc.partner.form.model.FormPublication;
import pomoc.partner.person.Right;

public class UserAccessModel {

	private FormPublication[] forms;
	private Boolean[] readRights;
	private Boolean[] writeRights;
	
	public UserAccessModel(List<FormPublication> forms,
			Map<FormPublication, Right> formRights) {
		this( forms.size());
		for (int i = 0; i < forms.size(); i ++) {
			FormPublication form = forms.get(i); 
			this.forms[i] = form;
			if (formRights.containsKey(form)) {
				Right right = formRights.get(form);
				if (Right.EDIT == right) {
					readRights[i] = true;
					writeRights[i] = true;
				} else if (Right.READ_ONLY == right) {
					readRights[i] = true;
				}
			}
		}
	}

	public Map<FormPublication, Right> getFormRights() {
		Map<FormPublication, Right> rightsMap = new HashMap<FormPublication, Right>();
		for (int i = 0; i < forms.length; i++) {
			FormPublication form = forms[i];
			if (writeRights[i]) {
				rightsMap.put(form, Right.EDIT);
				continue;
			}
			
			if (readRights[i]) {
				rightsMap.put(form, Right.READ_ONLY);
			}
			
		}
		return rightsMap;
	}
	
	public UserAccessModel() {
		this(0);
	}

	private UserAccessModel(int size) {
		forms = new FormPublication[size];
		readRights = new Boolean[size];
		writeRights = new Boolean[size];
		Arrays.fill(readRights, false);
		Arrays.fill(writeRights, false);
	}
	
	public static UserAccessModel getWithAllRights(List<FormPublication> forms) {
		UserAccessModel userAccessModel = new UserAccessModel( forms.size());
		for (int i = 0; i < forms.size(); i ++) {
			userAccessModel.forms[i] = forms.get(i);
			userAccessModel.readRights[i] = true;
			userAccessModel.writeRights[i] = true;
		}
		return userAccessModel;
	}

	public FormPublication[] getForms() {
		return forms;
	}

	public void setForms(FormPublication[] forms) {
		this.forms = forms;
	}

	public Boolean[] getReadRights() {
		return readRights;
	}


	public void setReadRights(Boolean[] readRights) {
		this.readRights = readRights;
	}


	public Boolean[] getWriteRights() {
		return writeRights;
	}


	public void setWriteRights(Boolean[] writeRights) {
		this.writeRights = writeRights;
	}
	
	

}

