package pomoc.partner.ticket;

import java.util.Date;


public class TicketStaticData {
	private final Integer number;
	private final String fromName;
	private final String fromEmail;
	private final String content;
	private final Date registeredDate;
	
	public TicketStaticData(Ticket ticket) {
		number = ticket.getNumber();
		fromName = ticket.getFormResponse().getName();
		fromEmail = ticket.getFormResponse().getEmail();
		content = ticket.getFormResponse().getContent();
		registeredDate = ticket.getDate();
	}
	public Integer getNumber() {
		return number;
	}
	public String getFromName() {
		return fromName;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public String getContent() {
		return content;
	}
	public Date getRegisteredDate() {
		return (Date) registeredDate.clone();
	}
}
