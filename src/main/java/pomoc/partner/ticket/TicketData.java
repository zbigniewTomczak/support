package pomoc.partner.ticket;

import java.util.Date;
import java.util.List;

import pomoc.partner.ticket.event.Event;
import pomoc.partner.ticket.event.SlaDeadlineEvent;

public final class TicketData implements Comparable<TicketData> {
	private final Integer number;
	private final String formName;
	@Deprecated
	private final String fromName;
	@Deprecated
	private final String fromEmail;
	private final Date date;
	private final Status status;
	private final String assigneeEmail;
	private Date slaDeadline;
	
	public TicketData(Ticket ticket) {
		this.number = ticket.getNumber();
		this.setSlaDeadline(getLastDeadline(ticket.getEvents()));
		this.formName = ticket.getFormResponse().getFormDefinition().getName();
		this.fromName = ticket.getFormResponse().getName();
		this.fromEmail = ticket.getFormResponse().getEmail();
		this.date = ticket.getDate();
		this.status = ticket.getStatus();
		if (ticket.getAssignee() != null) {
			this.assigneeEmail = ticket.getAssignee().getEmail();
		} else {
			this.assigneeEmail = null;
		}
		
	}
	
	private Date getLastDeadline(List<Event> events) {
		for (Event event : events) {
			if (event instanceof SlaDeadlineEvent) {
				return event.getDate();
			}
		}
		return null;
	}

	public Integer getNumber() {
		return number;
	}
	
	public String getFormName() {
		return formName;
	}
	
	public String getFromName() {
		return fromName;
	}
	
	public String getFromEmail() {
		return fromEmail;
	}
	
	public Date getDate() {
		return (Date) date.clone();
	}
	public Status getStatus() {
		return status;
	}
	
	public String getAssigneeEmail() {
		return assigneeEmail;
	}
	
	@Override
	public int hashCode() {
		return number.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return this.equals(obj);
	}

	public Date getSlaDeadline() {
		return slaDeadline;
	}

	public void setSlaDeadline(Date slaDeadline) {
		this.slaDeadline = slaDeadline;
	}

	@Override
	public int compareTo(TicketData o) {
		if (number != null && o.number != null) {
			return number.compareTo(o.number);
		}
		return 0;
	}
}
