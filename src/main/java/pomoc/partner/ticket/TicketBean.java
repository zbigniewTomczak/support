package pomoc.partner.ticket;

import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import pomoc.partner.login.LoggedPersonService;
import pomoc.partner.person.Person;

@ManagedBean
@ViewScoped
public class TicketBean {
	@Inject
	private Logger log;
	
	@Inject
	private LoggedPersonService loggedPersonService;

	@Inject
	private TicketService ticketService;
	
	private TicketData selectedTicket;
	
	public void assignToMe() {
		if (selectedTicket == null) {
			//Faces error
		}
		Person loggedPerson = loggedPersonService.getLoggedPerson();
		log.info("assigning ticket to: "+loggedPerson.getEmail());
		if (loggedPerson == null) {
			//Error
		}
		try {
		ticketService.assignTo(selectedTicket, loggedPersonService
				.getLoggedPerson());
		} catch (EJBException e) {
			e.printStackTrace();
		}
		selectedTicket = null;
		return;
	}
	
	public TicketData getSelectedTicket() {
		return selectedTicket;
	}

	public void setSelectedTicket(TicketData selectedTicket) {
		System.out.println("malk:"+selectedTicket);
		this.selectedTicket = selectedTicket;
	}

}
