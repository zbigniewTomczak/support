package pomoc.partner.ticket.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("incoming-event")
public class IncomingEvent extends CommunicationEvent {

	public IncomingEvent() {
	}
	
	public IncomingEvent(String message) {
		super(null, message);
	}
	
}
