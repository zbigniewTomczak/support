package pomoc.customer.ticket;

import java.util.Date;

import pomoc.partner.person.Person;

public final class TicketData {
	private final String number;
	private final String formName;
	private final String fromName;
	private final String fromEmail;
	private final Date date;
	private final Status status;
	private final Person assignee;
	
	public TicketData(Ticket ticket) {
		this.number = ticket.getNumber();
		this.formName = ticket.getSupportFormResponse().getSupportForm().getName();
		this.fromName = ticket.getSupportFormResponse().getName();
		this.fromEmail = ticket.getSupportFormResponse().getEmail();
		this.date = ticket.getDate();
		this.status = ticket.getStatus();
		this.assignee = ticket.getAssignee();
	}
	
	public String getNumber() {
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
	
	public Person getAssignee() {
		return assignee;
	}

	@Override
	public int hashCode() {
		return number.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return this.equals(obj);
	}
	
}
