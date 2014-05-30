package pomoc.customer.communication;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pomoc.partner.person.Person;
import pomoc.partner.ticket.Ticket;

@Entity
public class CommunicationCase {

	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private Direction direction;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@ManyToOne
	private Person responsible;
	@ManyToOne
	private Ticket ticket;
	private String content;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Person getResponsible() {
		return responsible;
	}
	public void setResponsible(Person responsible) {
		this.responsible = responsible;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
