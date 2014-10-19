package pomoc.partner.ticket;

import java.util.Date;

public final class TicketData {
	private final String number;
	private final String formName;
	private final String fromName;
	private final String fromEmail;
	private final Date date;
	private final Status status;
	private final String assigneeEmail;
	
	public TicketData(Ticket ticket) {
		this.number = ticket.getNumber();
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
	
}
