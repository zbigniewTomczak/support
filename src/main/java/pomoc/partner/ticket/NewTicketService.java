package pomoc.partner.ticket;

import java.util.Date;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class NewTicketService {
	@Inject
	private EntityManager em;
	
	private Integer getNewTicketNumber() {
		TypedQuery<Integer> query = em.createQuery(
				"SELECT max(t.number) from Ticket t", 
				Integer.class);
		Integer maxNumber = query.getSingleResult();
		if (maxNumber == null) {
			maxNumber = 0;
		}
		return maxNumber + 1;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public Ticket getNewTicket() {
		Ticket newTicket = new Ticket();
		newTicket.setNumber(getNewTicketNumber());
		newTicket.setDate(new Date());
		newTicket.setStatus(Status.NEW, null);
		return newTicket;
	}
}
