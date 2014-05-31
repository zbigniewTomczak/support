package pomoc.email;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pomoc.partner.Partner;

@Entity
public class MailboxCheckHistory {

	@Id
	@GeneratedValue
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private Integer processedNumber;
	@ManyToOne
	private Partner partner;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getProcessedNumber() {
		return processedNumber;
	}
	public void setProcessedNumber(Integer processedNumber) {
		this.processedNumber = processedNumber;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
}
