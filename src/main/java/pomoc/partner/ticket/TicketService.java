package pomoc.partner.ticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.mail.EmailException;

import pomoc.customer.communication.CommunicationCase;
import pomoc.customer.communication.CommunicationCaseData;
import pomoc.customer.communication.Direction;
import pomoc.email.EmailSender;
import pomoc.partner.Partner;
import pomoc.partner.form.model.FormPublication;
import pomoc.partner.login.Current;
import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;
import pomoc.partner.person.Right;
import pomoc.partner.ticket.event.Event;
import pomoc.partner.ticket.event.OutgoingEvent;

import com.google.common.base.Preconditions;

@Stateless
public class TicketService {
	
	@Resource
	private EJBContext context;

	@Inject
	private EntityManager em;
	
	@Inject
	private PersonService personService;

	@Inject
	private EmailSender sender;
	
	@Inject
	private Logger log;
	
	@Inject @Current
	private Partner p;

	@Inject @Current
	private Person loggedUser;

	public Ticket save(Ticket ticket) {
		if (ticket.getAssigneeId() != null) {
			Person newAssignee = em.find(Person.class, ticket.getAssigneeId());
			if (ticket.getAssignee() == null || ticket.getAssignee().getId() != newAssignee.getId()) {
				ticket.setAssignee(newAssignee, loggedUser);
			}
		}
		return em.merge(ticket);
	}

	@Produces
	@AllTickets
	public List<TicketData> getAllPartnerTickets() {
		TypedQuery<TicketData> query = em.createQuery(
				"SELECT DISTINCT "
						+ "new pomoc.partner.ticket.TicketData(t) "
						+ "from Ticket t "
						+ "LEFT JOIN t.events "
						+ "WHERE t.formResponse.formDefinition.partner.id=:id "
//						+ "order by t.date DESC"
				, TicketData.class);
		query.setParameter("id", p.getId());
		return query.getResultList();
	}
	
	public List<TicketData> getPartnerUnassignedTicketsData(Person loggedPerson) {
		if (loggedPerson == null || loggedPerson.getId() == null) {
			return Collections.emptyList();
		}
		Preconditions.checkNotNull(loggedPerson.getId());
		loggedPerson = em.find(Person.class, loggedPerson.getId());
		Preconditions.checkNotNull(loggedPerson);
		
		TypedQuery<TicketData> query = em.createQuery(
				"SELECT DISTINCT new pomoc.partner.ticket.TicketData(t) "
						+ "from Ticket t "
						+ "LEFT JOIN t.events "
						+ "WHERE t.formResponse.formDefinition.partner.id=:id "
						+ "AND t.assignee IS NULL "
//						+ "order by t.number DESC"
				, TicketData.class);
		query.setParameter("id", loggedPerson.getPartner().getId());
		return query.getResultList();
	}

	

	public List<TicketData> getPartnerAssignedTicketsData(Person loggedPerson) {
		if (loggedPerson == null || loggedPerson.getId() == null) {
			return Collections.emptyList();
		}
		Preconditions.checkNotNull(loggedPerson.getId());
		loggedPerson = em.find(Person.class, loggedPerson.getId());
		Preconditions.checkNotNull(loggedPerson);
		
		TypedQuery<TicketData> query = em.createQuery(
				"SELECT DISTINCT new pomoc.partner.ticket.TicketData(t) "
						+ "from Ticket t "
						+ "LEFT JOIN  t.events "
						+ "WHERE t.formResponse.formDefinition.partner.id=:partnerId "
						+ "AND t.assignee.id=:personId "
//						+ "order by t.number DESC"
				, TicketData.class);
		query.setParameter("partnerId", loggedPerson.getPartner().getId());
		query.setParameter("personId", loggedPerson.getId());
		return query.getResultList();
	}
	
	public List<TicketData> getAllTicketsWithAccess(Person loggedPerson) {
		if (loggedPerson == null || loggedPerson.getId() == null) {
			return Collections.emptyList();
		}
		Preconditions.checkNotNull(loggedPerson.getId());
		loggedPerson = em.find(Person.class, loggedPerson.getId());
		Preconditions.checkNotNull(loggedPerson);

		Set<FormPublication> publications = loggedPerson.getFormRights().keySet();
		if (publications.isEmpty()) {
			return Collections.emptyList();
		}
		List<Long> publicationIds = new ArrayList<>();
		for(FormPublication pub : publications) {
			publicationIds.add(pub.getId());
		}
		TypedQuery<TicketData> query = em.createQuery(
				"SELECT new pomoc.partner.ticket.TicketData(t) "
						+ "from Ticket t "
						+ "WHERE t.formResponse.formDefinition.partner.id=:partnerId"
					+ " AND t.formResponse.formDefinition.publication.id IN :publications"
//					+ " order by t.number DESC"
				, TicketData.class);
		query.setParameter("partnerId", loggedPerson.getPartner().getId());
		query.setParameter("publications", publicationIds);
		return query.getResultList();
	}

	public void assignTo(TicketData selectedTicket, Person loggedPerson) {
		Preconditions.checkNotNull(loggedPerson);
		Preconditions.checkNotNull(loggedPerson.getId());
		Preconditions.checkNotNull(selectedTicket);
		Ticket ticket = findTicketByNumber(selectedTicket.getNumber(), em.find(Person.class, loggedPerson.getId()).getPartner());
		ticket.setAssignee(em.find(Person.class, loggedPerson.getId()), loggedPerson);
	}

	public Ticket findTicketByNumber(Integer number, Partner partner) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(partner);
		Preconditions.checkNotNull(partner.getId());
		TypedQuery<Ticket> query = em.createQuery(
				"SELECT t from Ticket t WHERE t.formResponse.formDefinition.partner.id.id=:id AND t.number=:number", 
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
	
	public Ticket findTicketByNumber(Integer number, Person person) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(person);
		Preconditions.checkNotNull(person.getId());
		return findTicketByNumber(number, em.find(Person.class, person.getId()).getPartner());
	}

	public TicketStaticData getStaticData(Integer number, Person person) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(person);
		Preconditions.checkNotNull(person.getId());
		return new TicketStaticData(findTicketByNumber(number, person));
	}

	public TicketEditableData getEditableData(Integer number, Person person) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(person);
		Preconditions.checkNotNull(person.getId());
		return new TicketEditableData(findTicketByNumber(number, person));
	}

	public CommunicationCase saveResponse(String response, Long ticketNumber) {
		Preconditions.checkNotNull(response);
		Preconditions.checkState(response.isEmpty()==false);
		Preconditions.checkNotNull(ticketNumber);
		CommunicationCase communicationCase = new CommunicationCase();
		communicationCase.setDirection(Direction.OUTGOING);
		Ticket ticket = em.find(Ticket.class, ticketNumber);
		communicationCase.setTicket(em.find(Ticket.class, ticketNumber));
		communicationCase.setResponsible(ticket.getAssignee());
		communicationCase.setDate(new Date());
		communicationCase.setContent(response);
		em.persist(communicationCase);
		return communicationCase;
	}

	public boolean saveAllAndSendResponse(TicketStaticData staticData,
			TicketEditableData editable, Person person) {
		Preconditions.checkNotNull(staticData);
		Preconditions.checkNotNull(editable);
		Preconditions.checkNotNull(staticData.getNumber());
		Preconditions.checkNotNull(person);
		Ticket ticket= findTicketByNumber(staticData.getNumber(), person);
		ticket.setStatus(editable.getStatus(), null);
		ticket.setAssignee(personService.getPerson(person.getEmail()), null);
		return saveAndSendResponse(staticData, editable, person);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean saveAndSendResponse(TicketStaticData staticData,
			TicketEditableData editable, Person person) {
		Preconditions.checkNotNull(staticData);
		Preconditions.checkNotNull(editable);
		Preconditions.checkNotNull(staticData.getNumber());
		Preconditions.checkNotNull(person);
		Ticket ticket= findTicketByNumber(staticData.getNumber(), person);
		if (editable.getResponse() != null && editable.getResponse().isEmpty() == false) {
			CommunicationCase communicationCase = saveResponse(editable.getResponse(), ticket.getId());
			try {
				sender.send(communicationCase);
			} catch (EmailException e) {
				log.severe(e.getMessage());
				context.setRollbackOnly();
				return false;
			}
		}
		return true;
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Ticket saveAndSendResponse(Ticket ticket, String email, String message) {
		Preconditions.checkNotNull(ticket);
		Preconditions.checkNotNull(ticket.getId());
		Preconditions.checkNotNull(email);
		Preconditions.checkNotNull(message);
		ticket= em.find(Ticket.class, ticket.getId());
		
		if (!email.isEmpty() && !message.isEmpty()) {
			Event event = new OutgoingEvent(loggedUser, message);
			ticket.getEvents().add(event);
    		ticket = save(ticket);
			sender.send(ticket, email, message, ticket.getFormResponse().getFormDefinition().getPartner().getPreferences());
		}
		
		return ticket;
	}

	public List<CommunicationCaseData> getCommunicationCasesForTicket(
			Integer number, Person person) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(person);
		Ticket ticket= findTicketByNumber(number, person);
		TypedQuery<CommunicationCaseData> query = em.createQuery(
				"SELECT new pomoc.customer.communication.CommunicationCaseData(cc.direction, cc.responsible.email, cc.date, cc.content) from CommunicationCase cc WHERE cc.ticket.id=:id ORDER BY cc.date DESC", 
				CommunicationCaseData.class);
		query.setParameter("id", ticket.getId());
		return query.getResultList();
		
	}

	public Ticket getTicket(Integer number) {
		Preconditions.checkNotNull(number);
		TypedQuery<Ticket> query = em.createQuery(
				"SELECT t FROM Ticket t JOIN FETCH t.formResponse fr JOIN FETCH fr.responses WHERE t.number=:number", 
				Ticket.class);
		query.setParameter("number", number);
		List<Ticket> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return null;
		}
		resultList.get(0).getEvents().size();
		return resultList.get(0);
	}

	public List<SelectItem> getUsersWithEditRights(Ticket ticket) {
		Preconditions.checkNotNull(ticket);
		Preconditions.checkNotNull(ticket.getId());
		
		ticket = em.find(Ticket.class, ticket.getId());
		TypedQuery<SelectItem> query = em.createQuery(
				"SELECT new javax.faces.model.SelectItem(p.id, p.name) from Person p WHERE KEY(p.formRights) = :publication AND :edit IN (VALUE(p.formRights))"
				+ " order by p.name", 
				SelectItem.class);
		query.setParameter("edit", Right.EDIT);
		query.setParameter("publication", ticket.getFormResponse().getFormDefinition().getPublication());
		return query.getResultList();
	}
	
}
