package pomoc.partner.ticket.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import pomoc.partner.person.Person;

@Entity
@DiscriminatorValue("new-asignee-event")
public class NewAssigneeEvent extends Event {

	@ManyToOne
	private Person assignee;
	
	public NewAssigneeEvent() {
	}
	public NewAssigneeEvent(Person assignee, Person performer) {
		super(performer);
		this.assignee = assignee;
	}

	public String toString() {
		String name = assignee != null ? assignee.getName() : "";
		return "Nowa odpowiedzialność: " + name;
	}

	public Person getAssignee() {
		return assignee;
	}
	public void setAssignee(Person assignee) {
		this.assignee = assignee;
	}

	
}
