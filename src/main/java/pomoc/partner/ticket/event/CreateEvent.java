package pomoc.partner.ticket.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("create-event")
public class CreateEvent extends Event {

	public CreateEvent() {
		super(null);
	}
	
	@Override
	public String toString() {
		return "TICKET NEW";
	}
}
