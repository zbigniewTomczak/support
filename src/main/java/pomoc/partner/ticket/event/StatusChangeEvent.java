package pomoc.partner.ticket.event;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import pomoc.partner.person.Person;
import pomoc.partner.ticket.Status;

@Entity
@DiscriminatorValue("status-change-event")
public class StatusChangeEvent extends Event {
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Status statusBefore;

	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Status statusAfter;
	
	public StatusChangeEvent() {
	}
	
	public StatusChangeEvent(Status statusBefore, Status statusAfter, Person performer) {
		super(performer);
		this.statusBefore = statusBefore;
		this.statusAfter = statusAfter;
	}

	@Override
	public String toString() {
		return "TICKET " + statusBefore + " -> " + statusAfter;
	}

	public Status getStatusBefore() {
		return statusBefore;
	}

	public void setStatusBefore(Status statusBefore) {
		this.statusBefore = statusBefore;
	}

	public Status getStatusAfter() {
		return statusAfter;
	}

	public void setStatusAfter(Status statusAfter) {
		this.statusAfter = statusAfter;
	}

	
}
