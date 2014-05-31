package pomoc.customer.communication;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.Partner;
import pomoc.partner.ticket.Ticket;

import com.google.common.base.Preconditions;

public class CommunicationService {

	@Inject
	private EntityManager em;
	@Inject
	private Logger log;
	public void registerClientResponse(String number, String from,
			Date date, String content, Partner partner) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(from);
		Preconditions.checkNotNull(partner);
		Preconditions.checkNotNull(partner.getId());
		TypedQuery<Ticket> query = em.createQuery("SELECT t FROM Ticket t WHERE t.number=:number AND t.supportFormResponse.email=:email AND t.partner.id=:id", Ticket.class);
		query.setParameter("number", number);
		query.setParameter("email", from);
		query.setParameter("id", partner.getId());
		List<Ticket> list = query.getResultList();
		if (list.isEmpty()) {
			log.warning(String.format("No ticket is matchning number=%s and email=%s", number, from));
			return;
		}
		if (list.size() > 1) {
			log.warning(String.format("More than one ticket is matchning number=%s and email=%s", number, from));
		}
		Ticket ticket = list.get(0);
		CommunicationCase communicationCase = new CommunicationCase();
		communicationCase.setDate(date);
		communicationCase.setDirection(Direction.INCOMING);
		communicationCase.setTicket(ticket);
		communicationCase.setContent(content.substring(0, content.length() > 254 ? 253 : content.length()));
		communicationCase.setResponsible(ticket.getAssignee());
		em.persist(communicationCase);
	}

}
