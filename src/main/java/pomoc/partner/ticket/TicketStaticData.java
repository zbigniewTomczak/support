package pomoc.partner.ticket;

import java.util.Date;


public class TicketStaticData {
	private final String number;
	private final String fromName;
	private final String fromEmail;
	private final String content;
	private final Date registeredDate;
	
	public TicketStaticData(Ticket ticket) {
		number = ticket.getNumber();
		fromName = ticket.getSupportFormResponse().getName();
		fromEmail = ticket.getSupportFormResponse().getEmail();
		content = ticket.getSupportFormResponse().getContent();
		registeredDate = ticket.getDate();
	}
	public String getNumber() {
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