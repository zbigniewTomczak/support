package pomoc.partner.ticket.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import pomoc.partner.person.Person;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("communication-event")
public abstract class CommunicationEvent extends Event {
	private String message;

	public CommunicationEvent() {
	}

	public CommunicationEvent(Person performer, String message) {
		super(performer);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
