package pomoc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "pomoc.converter.BooleanToYesNo", forClass=Boolean.class)
public class BooleanToYesNo implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if ("TAK".equals(value)) {
			return true;
		}
		return false;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if (value instanceof Boolean) {
			if (Boolean.TRUE.equals(value)) {
				return "TAK";
			}
		}
		if (value instanceof String) {
			if ("true".equalsIgnoreCase((String) value)) {
				return "TAK";
			}
		}
		return "NIE";
	}

}
