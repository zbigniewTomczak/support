package pomoc.partner.ticket.event;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import pomoc.partner.person.Person;
import pomoc.partner.ticket.Status;

@Entity
@DiscriminatorValue("close-event")
public class CloseEvent extends Event {
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Status statusClosed;

	public CloseEvent() {
	}
	
	public CloseEvent(Status status, Person performer) {
		super(performer);
		this.statusClosed = status;
	}

	public Status getStatusClosed() {
		return statusClosed;
	}

	public void setStatusClosed(Status statusClosed) {
		this.statusClosed = statusClosed;
	}
	
	@Override
	public String toString() {
		return "TICKET " + statusClosed;
	}
	
}
