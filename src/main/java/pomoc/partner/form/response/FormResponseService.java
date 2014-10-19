package pomoc.partner.form.response;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pomoc.partner.form.FormPublicationService;
import pomoc.partner.form.model.FormPublication;
import pomoc.partner.ticket.Status;
import pomoc.partner.ticket.Ticket;
import pomoc.partner.ticket.TicketService;

import com.google.common.base.Preconditions;

@Stateless
public class FormResponseService {

	@Inject
	private EntityManager em;
	@Inject 
	private Logger logger;
	@Inject
	private FormPublicationService formPublicationervice;
	@Inject
	private TicketService ticketService;
	
	public void save(FormResponse formResponse) {
		logger.info("Saved " + formResponse);
		
	}

	public void saveNewFormResponse(FormResponse formResponse,
			String key) {
		Preconditions.checkNotNull(formResponse);
		Preconditions.checkNotNull(key);
		FormPublication formPublication= formPublicationervice.getFormPublicationFormPublication(key);
		Preconditions.checkNotNull(formPublication);
//		formResponse.setPublication(formPublication);
		em.persist(formResponse);
		Ticket newTicket = new Ticket();
		newTicket.setFormResponse(formResponse);
		newTicket.setDate(new Date());
		newTicket.setNumber(ticketService.getNewTicketNumber(formPublication.getPartner()));
		newTicket.setStatus(Status.NEW);
		newTicket.setPartner(formPublication.getPartner());
		em.persist(newTicket);
	}

}
