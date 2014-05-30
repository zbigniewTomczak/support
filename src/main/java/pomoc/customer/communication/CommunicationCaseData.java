package pomoc.customer.communication;

import java.util.Date;


public class CommunicationCaseData {
	private final Direction direction;
	private final String responsibleEmail;
	private final Date date;
	private final String content;

	public CommunicationCaseData(Direction direction, String responsibleEmail, Date date, String content) {
		this.direction = direction;
		this.responsibleEmail = responsibleEmail;
		this.date = date;       
		this.content = content;  
	}

	public Direction getDirection() {
		return direction;
	}



	public String getResponsibleEmail() {
		return responsibleEmail;
	}



	public Date getDate() {
		return (Date) date.clone();
	}

	public String getContent() {
		return content;
	}
	
	
}
