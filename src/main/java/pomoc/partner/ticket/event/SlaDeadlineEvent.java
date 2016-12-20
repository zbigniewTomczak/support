package pomoc.partner.ticket.event;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import pomoc.partner.form.sla.SlaPlan;

@Entity
@DiscriminatorValue("sla-deadline")
public class SlaDeadlineEvent extends Event {

	public SlaDeadlineEvent() {
	}

	public SlaDeadlineEvent(SlaPlan slaPlan, Integer planHours) {
		this.slaPlan = slaPlan;
		this.setDate(calculateDate(planHours));
	}

	private Date calculateDate(Integer planHours) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, planHours);
		return cal.getTime();
	}

	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private SlaPlan slaPlan;

	public SlaPlan getSlaPlan() {
		return slaPlan;
	}

	public void setSlaPlan(SlaPlan slaPlan) {
		this.slaPlan = slaPlan;
	}

	@Override
	public String toString() {
		String plan = slaPlan == SlaPlan.FOR_OPENING ? "OPEN" : "RESOLVED";
		return "SLA Deadline " + plan;
	}
	
}
