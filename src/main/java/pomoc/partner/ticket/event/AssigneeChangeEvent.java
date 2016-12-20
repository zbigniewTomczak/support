package pomoc.partner.ticket.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import pomoc.partner.person.Person;

@Entity
@DiscriminatorValue("asignee-change-event")
public class AssigneeChangeEvent extends Event {

	@ManyToOne
	private Person assigneeBefore;
	@ManyToOne
	private Person assigneeAfter;
	
	public AssigneeChangeEvent() {
	}
	
	public AssigneeChangeEvent(Person assigneeBefore, Person assigneeAfter, Person performer) {
		super(performer);
		this.assigneeBefore = assigneeBefore; 
		this.assigneeAfter = assigneeAfter;
	}

	@Override
	public String toString() {
		String before = assigneeBefore != null ? assigneeBefore.getName() : "";
		String after = assigneeAfter != null ? assigneeAfter.getName() : "";
		return "Odpowiedzialność: " + before + " -> " + after;
	}
	
	public Person getAssigneeBefore() {
		return assigneeBefore;
	}

	public void setAssigneeBefore(Person assigneeBefore) {
		this.assigneeBefore = assigneeBefore;
	}

	public Person getAssigneeAfter() {
		return assigneeAfter;
	}

	public void setAssigneeAfter(Person assigneeAfter) {
		this.assigneeAfter = assigneeAfter;
	}

	
}
