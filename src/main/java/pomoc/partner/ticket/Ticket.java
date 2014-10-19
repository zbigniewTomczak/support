package pomoc.partner.ticket;

import java.util.Date;
import java.util.List;

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

import pomoc.customer.FormResponse;
import pomoc.customer.communication.CommunicationCase;
import pomoc.partner.Partner;
import pomoc.partner.person.Person;
@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private Long id;
	private String number;
	@ManyToOne
	private FormResponse formResponse;
	@Enumerated(EnumType.STRING)
	private Status status;
	@ManyToOne
	private Person assignee;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	@ManyToOne
	private Partner partner;
	@OrderBy(value="date DESC")
	@OneToMany(mappedBy="ticket")
	private List<CommunicationCase> communicationCases;
	
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
	public FormResponse getFormResponse() {
		return formResponse;
	}
	public void setFormResponse(FormResponse formResponse) {
		this.formResponse = formResponse;
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
	public List<CommunicationCase> getCommunicationCases() {
		return communicationCases;
	}
	public void setCommunicationCases(List<CommunicationCase> communicationCases) {
		this.communicationCases = communicationCases;
	}
	
	
}
