package pomoc.partner.person;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pomoc.partner.Partner;

@Stateless
public class PersonRegistrationService {

	@Inject
	private EntityManager em;
	
	public void registerNewAdmin(Person newAdmin, Partner newPartner) {
		checkNotNull(newAdmin);
		checkNotNull(newPartner);
		newAdmin.setRole(Role.ADMIN);
		newAdmin.setPartner(newPartner);
		em.persist(newPartner);
		em.persist(newAdmin);
	}

	
}
