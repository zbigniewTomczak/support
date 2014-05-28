package pomoc.partner.ticket;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.Partner;
import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;

import com.google.common.base.Preconditions;

@Stateless
public class TicketService {

	@Inject
	private EntityManager em;
	
	@Inject
	private PersonService personService;
	
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
		
		TypedQuery<TicketData> query = em.createQuery(
				"SELECT new pomoc.partner.ticket.TicketData(t) from Ticket t WHERE t.partner.id=:id AND t.assignee IS NULL order by t.date ASC", 
				TicketData.class);
		query.setParameter("id", loggedPerson.getPartner().getId());
		return query.getResultList();
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
				"SELECT new pomoc.partner.ticket.TicketData(t) from Ticket t WHERE t.partner.id=:partnerId AND t.assignee.id=:personId order by t.date ASC", 
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

	public TicketStaticData getStaticData(String number, Person person) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(person);
		Preconditions.checkNotNull(person.getId());
		return new TicketStaticData(findTicketByNumber(number, person));
	}

	public TicketEditableData getEditableData(String number, Person person) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(person);
		Preconditions.checkNotNull(person.getId());
		return new TicketEditableData(findTicketByNumber(number, person));
	}

	public void save(TicketStaticData staticData, TicketEditableData editable, Person person) {
		Preconditions.checkNotNull(staticData);
		Preconditions.checkNotNull(editable);
		Preconditions.checkNotNull(staticData.getNumber());
		Preconditions.checkNotNull(person);
		Ticket ticket= findTicketByNumber(staticData.getNumber(), person);
		ticket.setStatus(editable.getStatus());
		ticket.setAssignee(personService.getPerson(editable.getAssigneeEmail()));
	}

}
