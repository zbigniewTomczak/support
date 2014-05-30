package pomoc.partner;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pomoc.partner.person.Person;
import pomoc.partner.preferences.Preferences;

import com.google.common.base.Preconditions;

@Stateless
public class PartnerService {

	@Inject
	private EntityManager em;

	public List<Partner> getAllPartners() {
		return em.createQuery("SELECT p FROM Partner p",Partner.class).getResultList();
	}

	public Preferences getPreferences(Person loggedPerson) {
		Preconditions.checkNotNull(loggedPerson);
		Preconditions.checkNotNull(loggedPerson.getId());
		return getPartner(loggedPerson).getPreferences();
	}

	public Partner getPartner(Person person) {
		Preconditions.checkNotNull(person);
		Preconditions.checkNotNull(person.getId());
		return em.find(Person.class, person.getId()).getPartner();
	}

	public void save(Preferences preferences) {
		Preconditions.checkNotNull(preferences);
		if (preferences.getId() != null) {
			em.merge(preferences);
		} else {
			em.persist(preferences);
		}
		
	}
}
