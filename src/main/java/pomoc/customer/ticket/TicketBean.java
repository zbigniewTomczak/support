package pomoc.customer.ticket;

import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pomoc.customer.company.Company;
import pomoc.customer.company.CompanyService;

@Model
public class TicketBean {

	@Produces
	@Named
	private Ticket ticket = new Ticket();
	
	@Inject
	private CompanyService companyService;
	
	@Inject
	private TicketService ticketService;

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private Logger logger;
	
	public void newTicket() {
		System.out.println("nnn");
		String companyKey = facesContext.getExternalContext().getRequestParameterMap().get("companyKey");
		logger.info("Creating ticket for company key: " + companyKey);
		if (companyKey == null) {
			// TODO report error
			return;
		}
		
		Company company = companyService.get(companyKey);
		ticket.setCompany(company);
		ticketService.save(company);
		
		// TODO message to user
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	
}
