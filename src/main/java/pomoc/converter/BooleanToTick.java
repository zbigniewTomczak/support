package pomoc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "pomoc.converter.BooleanToTick", forClass=Boolean.class)
public class BooleanToTick implements Converter {

	private static final String FOR_TRUE = "<i class=\"fa fa-check-square-o fa-fw\"></i>";
	private static final String FOR_FALSE = "<i class=\"fa fa-square-o fa-fw\"></i>";
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return Boolean.valueOf(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if (value instanceof Boolean) {
			if (Boolean.TRUE.equals(value)) {
				return FOR_TRUE;
			}
		}
		return FOR_FALSE;
	}

}
