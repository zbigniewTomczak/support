package pomoc.partner.ticket.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import pomoc.partner.person.Person;

@Entity
@DiscriminatorValue("outgoing-event")
public class OutgoingEvent extends CommunicationEvent {

	public OutgoingEvent() {
	}

	public OutgoingEvent(Person performer, String message) {
		super(performer,message);
	}

}
