package pomoc.customer.ticket;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
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
	private Logger logger;
	
	@Inject
	private LoggedPersonService loggedPersonService;

	@Inject
	private TicketService ticketService;
	
	private TicketData selectedTicket;

	@PostConstruct
	public void init() {
		//String number = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("submenuForm:selectedTicketNumber");
		//System.out.println(number);
		//if (number != null) {
		//	selectedTicket = new TicketData(ticketService.findTicketByNumber(number, loggedPersonService.getLoggedPerson()));
		//}
		
	}
	
	public String assignToMe() {
		logger.info("assigning");
		if (selectedTicket == null) {
			//Faces error
		}
		Person loggedPerson = loggedPersonService.getLoggedPerson();
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
		return null;
	}
	
	public TicketData getSelectedTicket() {
		return selectedTicket;
	}

	public void setSelectedTicket(TicketData selectedTicket) {
		System.out.println("malk:"+selectedTicket);
		this.selectedTicket = selectedTicket;
	}

}
