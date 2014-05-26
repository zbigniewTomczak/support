package pomoc.company.form;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.SupportForm;

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


}
