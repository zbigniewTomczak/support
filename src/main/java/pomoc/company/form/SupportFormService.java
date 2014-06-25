package pomoc.company.form;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.SupportForm;
import pomoc.partner.person.Person;

import com.google.common.base.Preconditions;

@Stateless
public class SupportFormService {

	@Inject
	private EntityManager em;

	public SupportForm getSupportForm(String key) {
		TypedQuery<SupportForm> query = em.createQuery("SELECT sp from SupportForm sp WHERE sp.key=:key", SupportForm.class);
		query.setParameter("key", key);
		List<SupportForm> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public List<SupportFormListData> getPartnerForms(Person loggedPerson) {
		if (loggedPerson == null || loggedPerson.getId() == null) {
			return Collections.emptyList();
		}
		Preconditions.checkNotNull(loggedPerson.getId());
		loggedPerson = em.find(Person.class, loggedPerson.getId());
		Preconditions.checkNotNull(loggedPerson);
		
		TypedQuery<SupportFormListData> query = em.createQuery(
				"SELECT new pomoc.company.form.SupportFormListData(sp) FROM SupportForm sp  WHERE sp.partner.id=:id ORDER BY sp.name, sp.title", 
				SupportFormListData.class);
		query.setParameter("id", loggedPerson.getPartner().getId());
		return query.getResultList();
	}


}
