package pomoc.partner.ticket.event;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import pomoc.partner.person.Person;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class Event {
	
	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	
	@Column(insertable = false, updatable = false)
    private String type;
	
	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;

	@ManyToOne
	private Person performer;
	
	public Event() {
	}
	
	public Event(Person performer) {
		this.performer = performer;
		date = new Date();
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public Person getPerformer() {
		return performer;
	}

	public void setPerformer(Person performer) {
		this.performer = performer;
	}
	
	

}
