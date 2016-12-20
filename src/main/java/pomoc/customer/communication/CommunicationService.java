package pomoc.customer.communication;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.email.MailboxCheckHistory;
import pomoc.partner.Partner;
import pomoc.partner.ticket.Ticket;
import pomoc.partner.ticket.event.Event;
import pomoc.partner.ticket.event.IncomingEvent;

import com.google.common.base.Preconditions;

@Stateless
public class CommunicationService {

	@Inject
	private EntityManager em;
	@Inject
	private Logger log;
	
	public void save(MailboxCheckHistory h) {
		em.persist(h);
	}
	
	public void registerClientResponse(String number, String from,
			Date date, String content, Partner partner) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(from);
		Preconditions.checkNotNull(partner);
		Preconditions.checkNotNull(partner.getId());
		TypedQuery<Ticket> query = em.createQuery("SELECT t FROM Ticket t WHERE t.number=:number AND t.formResponse.formDefinition.partner.id=:id", Ticket.class);
		query.setParameter("number", Integer.parseInt(number));
		query.setParameter("id", partner.getId());
		List<Ticket> list = query.getResultList();
		if (list.isEmpty()) {
			log.warning(String.format("No ticket is matchning number=%s ", number, from));
			return;
		}
		if (list.size() > 1) {
			log.warning(String.format("More than one ticket is matchning number=%s and email=%s", number, from));
		}
		Ticket ticket = list.get(0);
		Event event = new IncomingEvent(content.substring(0, content.length() > 254 ? 253 : content.length()));
		event.setDate(date);
		ticket.getEvents().add(event);
		em.merge(ticket);
	}

	public MailboxCheckHistory getLastCheck(Partner partner) {
		List<MailboxCheckHistory> list = em.createQuery("SELECT m FROM MailboxCheckHistory m WHERE m.partner.id = :id ORDER BY m.date DESC", MailboxCheckHistory.class)
				.setParameter("id", partner.getId())
				.setMaxResults(1)
				.getResultList();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
