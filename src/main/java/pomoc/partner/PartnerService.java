package pomoc.partner;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class PartnerService {

	@Inject
	private EntityManager em;

	public List<Partner> getAllPartners() {
		return em.createQuery("SELECT p FROM Partner p",Partner.class).getResultList();
	}
}
