package pomoc.customer.ticket;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import pomoc.partner.login.LoggedPersonService;

@Model
public class TicketAssignedBean {

	@Inject
	private LoggedPersonService loggedPersonService;

	@Inject
	private TicketService ticketService;

	private List<TicketData> partnerTickets;

	private TicketData selectedTicket;

	public List<TicketData> getPartnerTickets() {
		if (partnerTickets == null) {
			partnerTickets = 
					ticketService.getPartnerAssignedTicketsData(loggedPersonService
							.getLoggedPerson());
		}
		return partnerTickets;
	}

	public TicketData getSelectedTicket() {
		return selectedTicket;
	}

	public void setSelectedTicket(TicketData selectedTicket) {
		this.selectedTicket = selectedTicket;
	}

}
