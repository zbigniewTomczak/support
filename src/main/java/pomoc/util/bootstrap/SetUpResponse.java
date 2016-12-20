package pomoc.util.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pomoc.form.FormCheckbox;
import pomoc.form.FormElement;
import pomoc.form.FormEmail;
import pomoc.form.FormInputText;
import pomoc.partner.form.model.FormDefinition;
import pomoc.partner.form.response.FormResponse;

public class SetUpResponse {

	public FormResponse setUp(FormDefinition formDef) {
		FormResponse resp = new FormResponse();
		resp.setFormDefinition(formDef);
		List<FormElement> elements = formDef.getElements();
		Map<FormElement, String> map = new HashMap<>();
		for(FormElement element : elements) {
			if (element instanceof FormInputText) {
				map.put(element, "Jan Kowalski");
			}
			if (element instanceof FormCheckbox) {
				map.put(element, "true");
			}
			if (element instanceof FormEmail) {
				map.put(element, "furtka@o2.pl");
			}
		}
		resp.setResponses(map);
		return resp;
	}

}
