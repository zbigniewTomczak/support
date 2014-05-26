package pomoc.customer.ticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.Partner;
import pomoc.partner.person.Person;

import com.google.common.base.Preconditions;

public class TicketService {

	@Inject
	private EntityManager em;
	
	public void save(Ticket ticket) {
		// TODO Auto-generated method stub
		
	}

	public List<TicketData> getPartnerUnassignedTicketsData(Person loggedPerson) {
		if (loggedPerson == null || loggedPerson.getId() == null) {
			return Collections.emptyList();
		}
		Preconditions.checkNotNull(loggedPerson.getId());
		loggedPerson = em.find(Person.class, loggedPerson.getId());
		Preconditions.checkNotNull(loggedPerson);
		
		TypedQuery<Ticket> query = em.createQuery(
				"SELECT t from Ticket t WHERE t.partner.id=:id AND t.assignee IS NULL order by t.date ASC", 
				Ticket.class);
		query.setParameter("id", loggedPerson.getPartner().getId());
		List<Ticket> tickets = query.getResultList();
		List<TicketData> ticketsData = new ArrayList<TicketData>();
		for (Ticket ticket : tickets) {
			TicketData ticketData = new TicketData(ticket);
			ticketsData.add(ticketData);
		}
		return ticketsData;
	}

	public String getNewTicketNumber(Partner partner) {
		return "" + new Random().nextInt();
	}

}
