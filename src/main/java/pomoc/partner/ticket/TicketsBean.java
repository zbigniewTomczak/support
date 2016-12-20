package pomoc.partner.ticket;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.partner.login.Current;
import pomoc.partner.person.Person;

@Model
public class TicketsBean {
	@Inject @AllTickets
	private List<TicketData> allTickets;
	private List<TicketData> allTicketsWithAccess;
	private List<TicketData> allMyTickets;
	
	@Inject @Current
	private Person loggedPerson;
	@Inject
	private TicketService ts;
	
	@PostConstruct
	public void init() {
		if (loggedPerson == null) {
			return;
		}
		if (loggedPerson.isAdmin()) {
			allTicketsWithAccess = allTickets;
		} else {
			allTicketsWithAccess = ts.getAllTicketsWithAccess(loggedPerson);
		}
		allMyTickets = ts.getPartnerAssignedTicketsData(loggedPerson);
	}
	
	@Produces
	@Named
	public List<TicketData> getAllTickets() {
		return allTickets;
	}
	
	@Produces
	@Named
	public List<TicketData> getAllTicketsWithAccess() {
		return allTicketsWithAccess;
	}
	
	@Produces
	@Named
	public List<TicketData> getAllMyTickets() {
		return allMyTickets;
	}
}
