package pomoc.partner;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import pomoc.email.MailboxCheckHistory;
import pomoc.partner.person.Person;
import pomoc.partner.preferences.Preferences;

@Entity
public class Partner {

    @Id
    @GeneratedValue
    private Long id;
	private String name;
	
	@OneToMany(mappedBy="partner", fetch=FetchType.EAGER)
	List<Person> admins;
	
	@ManyToOne
	private Preferences preferences;
	
	@OneToMany(mappedBy="partner")
	@OrderBy("date DESC")
	private List<MailboxCheckHistory> history;
	
	@OneToMany(mappedBy="partner")
	@OrderBy("name")
	private List<FormPublication> partnerForms;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasForms() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Person> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Person> admins) {
		this.admins = admins;
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	public List<MailboxCheckHistory> getHistory() {
		return history;
	}

	public void setHistory(List<MailboxCheckHistory> history) {
		this.history = history;
	}

	public List<FormPublication> getPartnerForms() {
		return partnerForms;
	}

	public void setPartnerForms(List<FormPublication> partnerForms) {
		this.partnerForms = partnerForms;
	}

	
	
}
