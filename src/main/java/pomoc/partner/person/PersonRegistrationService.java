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
	
	public void registerNewPartner(Person newAdmin, Partner newPartner) {
		checkNotNull(newAdmin);
		checkNotNull(newPartner);
		// TODO duplicate account validation 
		// TODO server side validation 
		newAdmin.setRole(Role.ADMIN);
		newAdmin.setPartner(newPartner);
		em.persist(newPartner);
		em.persist(newAdmin);
	}

	
}
