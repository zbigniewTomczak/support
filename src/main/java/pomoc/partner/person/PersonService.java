package pomoc.partner.person;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.login.LoggedPersonService;

import com.google.common.base.Preconditions;

@Stateless
public class PersonService {

	@Inject
	private Logger log;
	
	@Inject
	private LoggedPersonService loggedPersonService;
	
	@Inject
	private EntityManager em;
	
	public Person getPerson(String email) {
		Preconditions.checkNotNull(email);
		TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.email=:email",Person.class);
		query.setParameter("email", email);
		List<Person> list = query.getResultList();
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			log.severe("Found duplicated email in table Person: " + email);
		}
		return list.get(0);
	}

	public Person loginPerson(String email, String password) {
		Person person = getPerson(email);
		loggedPersonService.setLoggedPerson(person);
		return person;
	}

	public void logout() {
		loggedPersonService.setLoggedPerson(null);
	}

	public List<SelectItem> getColleagues(Person person) {
		Preconditions.checkNotNull(person);
		Preconditions.checkNotNull(person.getId());
		
		TypedQuery<SelectItem> query = em.createQuery("SELECT new javax.faces.model.SelectItem(p.email) FROM Person p WHERE p.partner.id=:id", SelectItem.class);
		query.setParameter("id", em.find(Person.class, person.getId()).getPartner().getId());
		return query.getResultList();
	}
}
