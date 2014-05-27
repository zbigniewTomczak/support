package pomoc.customer.ticket;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.partner.login.LoggedPersonService;

@Named
public class TicketDataConverter implements Converter {

	@Inject
	private LoggedPersonService loggedPersonService;
	
	@Inject
	private TicketService ticketService;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String number) {
		if (number == null) {
			return null;
		}
		return new TicketData(ticketService.findTicketByNumber(number, loggedPersonService.getLoggedPerson()));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if (object instanceof TicketData) {
			TicketData ticketData = (TicketData) object;
			return ticketData.getNumber();
		}
		return null;
	}

}
