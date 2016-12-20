package pomoc.partner.form.response;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pomoc.partner.form.FormPublicationService;
import pomoc.partner.form.model.FormPublication;
import pomoc.partner.form.sla.SlaPlan;
import pomoc.partner.ticket.NewTicketService;
import pomoc.partner.ticket.Ticket;
import pomoc.partner.ticket.event.SlaDeadlineEvent;

import com.google.common.base.Preconditions;

@Stateless
public class FormResponseService {

	@Inject
	private EntityManager em;
	@Inject
	private FormPublicationService formPublicationervice;
	@Inject
	private NewTicketService newTicketService;
	
	public void saveNewFormResponse(FormResponse formResponse,
			String key) {
		Preconditions.checkNotNull(formResponse);
		Preconditions.checkNotNull(key);
		FormPublication formPublication= formPublicationervice.getFormPublicationFormPublication(key);
		Preconditions.checkNotNull(formPublication);
//		formResponse.setPublication(formPublication);
		em.persist(formResponse);
		Ticket newTicket = newTicketService.getNewTicket();
		newTicket.setFormResponse(formResponse);
		if (formPublication.getSlaPlans() != null && formPublication.getSlaPlans().containsKey(SlaPlan.FOR_OPENING)) {
			Integer planHours = formPublication.getSlaPlans().get(SlaPlan.FOR_OPENING);
			if (planHours != null) {
				newTicket.getEvents().add(new SlaDeadlineEvent(SlaPlan.FOR_OPENING, planHours));
			}
		}
		//newTicket.setPartner(formPublication.getPartner());
		em.merge(newTicket);
	}

}
