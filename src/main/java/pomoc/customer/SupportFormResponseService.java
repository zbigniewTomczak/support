package pomoc.customer;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pomoc.company.form.SupportFormService;
import pomoc.customer.ticket.Status;
import pomoc.customer.ticket.Ticket;
import pomoc.customer.ticket.TicketService;
import pomoc.partner.SupportForm;

import com.google.common.base.Preconditions;

@Stateless
public class SupportFormResponseService {

	@Inject
	private EntityManager em;
	
	@Inject 
	private Logger logger;
	
	@Inject
	private SupportFormService supportFormService;
	
	@Inject
	private TicketService ticketService;
	
	public void save(SupportFormResponse supportFormResponse) {
		logger.info("Saved " + supportFormResponse);
		
	}

	public void saveNewFormResponse(SupportFormResponse supportFormResponse,
			String key) {
		Preconditions.checkNotNull(supportFormResponse);
		Preconditions.checkNotNull(key);
		SupportForm supportForm = supportFormService.getSupportForm(key);
		Preconditions.checkNotNull(supportForm);
		supportFormResponse.setSupportForm(supportForm);
		em.persist(supportFormResponse);
		Ticket newTicket = new Ticket();
		newTicket.setSupportFormResponse(supportFormResponse);
		newTicket.setDate(new Date());
		newTicket.setNumber(ticketService.getNewTicketNumber(supportForm.getPartner()));
		newTicket.setStatus(Status.NEW);
		newTicket.setPartner(supportForm.getPartner());
		em.persist(newTicket);
	}

}
