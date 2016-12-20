package pomoc.partner.ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import pomoc.customer.communication.CommunicationCase;
import pomoc.partner.Partner;
import pomoc.partner.form.response.FormResponse;
import pomoc.partner.form.sla.SlaPlan;
import pomoc.partner.person.Person;
import pomoc.partner.ticket.event.AssigneeChangeEvent;
import pomoc.partner.ticket.event.CloseEvent;
import pomoc.partner.ticket.event.CreateEvent;
import pomoc.partner.ticket.event.Event;
import pomoc.partner.ticket.event.NewAssigneeEvent;
import pomoc.partner.ticket.event.SlaDeadlineEvent;
import pomoc.partner.ticket.event.StatusChangeEvent;
@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private Long id;
	private Integer number;
	@ManyToOne
	private FormResponse formResponse;
	@Enumerated(EnumType.STRING)
	private Status status;
	@ManyToOne
	private Person assignee;
	@OneToMany(cascade=CascadeType.ALL)
	@OrderBy(value="date DESC")
	private List<Event> events;
	
	@Transient
	private Long assigneeId;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Deprecated
	private Date date;
	@ManyToOne
	@Deprecated
	private Partner partner;
	@OrderBy(value="date DESC")
	@OneToMany(mappedBy="ticket")
	@Deprecated
	private List<CommunicationCase> communicationCases;
	
	public Ticket() {
		events = new ArrayList<>();
		events.add(new CreateEvent());
		status = Status.NEW;
	}
	
	public List<SelectItem> getAvailableStatuses() {
		switch (status) {
			case NEW: 
				return Arrays.asList(new SelectItem(Status.NEW),
						new SelectItem(Status.OPEN),
					new SelectItem(Status.CLOSED));
			case OPEN:
				return Arrays.asList(new SelectItem(Status.OPEN),
					new SelectItem(Status.RESOLVED));
			case RESOLVED:
				return Arrays.asList(new SelectItem(Status.RESOLVED));
			case CLOSED:
				return Arrays.asList(new SelectItem(Status.CLOSED));
			default: 
				return Collections.emptyList();
		}
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public FormResponse getFormResponse() {
		return formResponse;
	}
	public void setFormResponse(FormResponse formResponse) {
		this.formResponse = formResponse;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status, Person performer) {
		if (this.status == status) {
			return;
		}
		if (status == Status.CLOSED || status == Status.RESOLVED) {
			events.add(new CloseEvent(status, performer));
		} else {
			events.add(new StatusChangeEvent(this.status, status, performer));
			if (Status.NEW == this.status && Status.OPEN == status) {
				Map<SlaPlan, Integer> slaPlans = this.getFormResponse().getFormDefinition().getPublication().getSlaPlans();
				if (slaPlans != null && slaPlans.get(SlaPlan.FOR_RESOLVING) != null ) {
					events.add(new SlaDeadlineEvent(SlaPlan.FOR_RESOLVING, slaPlans.get(SlaPlan.FOR_RESOLVING)));
				}
			}
		}
		this.status = status;
	}
	public Person getAssignee() {
		return assignee;
	}
	public void setAssignee(Person assignee, Person performer) {
		if (this.assignee == null && assignee == null) {
			return;
		}
		if (this.assignee == null && assignee != null) {
			events.add(new NewAssigneeEvent(assignee, performer));
		} else {
			events.add(new AssigneeChangeEvent(this.assignee, assignee, performer));
		}
		this.assignee = assignee;
	}
	
	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	
	public Long getAssigneeId() {
		return assigneeId != null ? assigneeId : (this.assignee != null ? this.assignee.getId() : null);
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public List<CommunicationCase> getCommunicationCases() {
		return communicationCases;
	}
	public void setCommunicationCases(List<CommunicationCase> communicationCases) {
		this.communicationCases = communicationCases;
	}
	
	
}
