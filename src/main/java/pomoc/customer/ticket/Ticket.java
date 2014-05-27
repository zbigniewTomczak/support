package pomoc.customer.ticket;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pomoc.customer.SupportFormResponse;
import pomoc.partner.Partner;
import pomoc.partner.person.Person;
@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private Long id;
	private String number;
	@ManyToOne
	private SupportFormResponse supportFormResponse;
	@Enumerated(EnumType.STRING)
	private Status status;
	@ManyToOne
	private Person assignee;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	@ManyToOne
	private Partner partner;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public SupportFormResponse getSupportFormResponse() {
		return supportFormResponse;
	}
	public void setSupportFormResponse(SupportFormResponse supportFormResponse) {
		this.supportFormResponse = supportFormResponse;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Person getAssignee() {
		return assignee;
	}
	public void setAssignee(Person assignee) {
		this.assignee = assignee;
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
	
	
}
