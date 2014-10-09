package pomoc.partner.person;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.login.LoggedPersonService;
import pomoc.util.Mailing;

import com.google.common.base.Preconditions;

@Stateless
public class PersonService {

	@Inject
	private Logger log;
	
	@Inject
	private LoggedPersonService loggedPersonService;
	
	@Inject
	private EntityManager em;
	
	@Inject Mailing mailing;
	
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
	
	public Boolean emailExists(String email) {
		Preconditions.checkNotNull(email);
		TypedQuery<Boolean> query = em.createQuery("SELECT "
				+ "CASE WHEN count(p)=0 THEN 0 "
				+ "ELSE 1 END "
				+ "FROM Person p WHERE p.email=:email",Boolean.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}

	public Person saveAndNotifyUser(Person user) {
		Person p = saveUser(user);
		mailing.notifyNewUser(user);
		return p;
	}

	public Person saveUser(Person user) {
		return em.merge(user);
	}
}
