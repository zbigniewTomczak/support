package pomoc.partner.ticket;

import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import pomoc.partner.login.LoggedPersonService;
import pomoc.partner.person.Person;
import pomoc.util.faces.FacesMessage;

@ManagedBean
@ViewScoped
public class TicketBean {
	@Inject
	private Logger log;

	@Inject
	private LoggedPersonService loggedPersonService;

	@Inject
	private TicketService ticketService;
	@Inject
	private FacesMessage facesMessage;
	private TicketData selectedTicket;

	public void assignToMe() {
		if (selectedTicket == null) {
			// Faces error
		}
		Person loggedPerson = loggedPersonService.getLoggedPerson();
		log.info("assigning ticket to: " + loggedPerson.getEmail());
		if (loggedPerson == null) {
			// Error
		}
		try {
			ticketService.assignTo(selectedTicket,
					loggedPersonService.getLoggedPerson());
		} catch (EJBException e) {
			e.printStackTrace();
		}
		selectedTicket = null;
		return;
	}

	public void onRowSelect(SelectEvent e) {
		if (e.getObject() instanceof TicketData) {
			TicketData ticketData = (TicketData) e.getObject();
			if (ticketData.getNumber() != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
			    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/support/ticket?faces-redirect=true&number="+ticketData.getNumber());
			}
		}
		facesMessage.postError("Wystąpił błąd. Nie można przejść do wybranego zgłoszenia.");
			
	}

	public TicketData getSelectedTicket() {
		return selectedTicket;
	}

	public void setSelectedTicket(TicketData selectedTicket) {
		System.out.println("malk:" + selectedTicket);
		this.selectedTicket = selectedTicket;
	}

}
