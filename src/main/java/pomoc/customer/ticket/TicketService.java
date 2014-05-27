package pomoc.customer.ticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.Partner;
import pomoc.partner.person.Person;

import com.google.common.base.Preconditions;

@Stateless
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

	public List<TicketData> getPartnerAssignedTicketsData(Person loggedPerson) {
		if (loggedPerson == null || loggedPerson.getId() == null) {
			return Collections.emptyList();
		}
		Preconditions.checkNotNull(loggedPerson.getId());
		loggedPerson = em.find(Person.class, loggedPerson.getId());
		Preconditions.checkNotNull(loggedPerson);
		
		TypedQuery<TicketData> query = em.createQuery(
				"SELECT new pomoc.customer.ticket.TicketData(t) from Ticket t WHERE t.partner.id=:partnerId AND t.assignee.id=:personId order by t.date ASC", 
				TicketData.class);
		query.setParameter("partnerId", loggedPerson.getPartner().getId());
		query.setParameter("personId", loggedPerson.getId());
		return query.getResultList();
	}

	public void assignTo(TicketData selectedTicket, Person loggedPerson) {
		Preconditions.checkNotNull(loggedPerson);
		Preconditions.checkNotNull(loggedPerson.getId());
		Preconditions.checkNotNull(selectedTicket);
		Ticket ticket = findTicketByNumber(selectedTicket.getNumber(), em.find(Person.class, loggedPerson.getId()).getPartner());
		ticket.setAssignee(em.find(Person.class, loggedPerson.getId()));
		//em.persist(ticket);
	}

	public Ticket findTicketByNumber(String number, Partner partner) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(partner);
		Preconditions.checkNotNull(partner.getId());
		TypedQuery<Ticket> query = em.createQuery(
				"SELECT t from Ticket t WHERE t.partner.id=:id AND t.number=:number", 
				Ticket.class);
		query.setParameter("id", partner.getId());
		query.setParameter("number", number);
		List<Ticket> resultList = query.getResultList();
		if (resultList.size() > 1) {
			//todo report error
		}
		
		if (resultList.isEmpty()) {
			return null;
		}
		
		return resultList.get(0);
	}
	
	public Ticket findTicketByNumber(String number, Person person) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(person);
		Preconditions.checkNotNull(person.getId());
		return findTicketByNumber(number, em.find(Person.class, person.getId()).getPartner());
	}

}
