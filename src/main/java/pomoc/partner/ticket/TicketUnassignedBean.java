package pomoc.partner.ticket;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.partner.login.LoggedPersonService;

@Model
public class TicketUnassignedBean {

	@Inject
	private LoggedPersonService loggedPersonService;

	@Inject
	private TicketService ticketService;

	private List<TicketData> partnerTickets;

	public List<TicketData> getPartnerTickets() {
		if (partnerTickets == null) {
			partnerTickets = 
					ticketService.getPartnerUnassignedTicketsData(loggedPersonService
							.getLoggedPerson());
		}
		return partnerTickets;
	}
}
