package pomoc.partner.ticket;


public class TicketEditableData {
	private Status status;
	private String assigneeEmail;
	private String response;
	public TicketEditableData(Ticket ticket) {
		status = ticket.getStatus();
		if (ticket.getAssignee() != null) {
			assigneeEmail = ticket.getAssignee().getEmail();
		}
		response = "";
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getAssigneeEmail() {
		return assigneeEmail;
	}
	public void setAssigneeEmail(String assigneeEmail) {
		this.assigneeEmail = assigneeEmail;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
	
}
