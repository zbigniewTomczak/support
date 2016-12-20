package pomoc.partner.person;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.Partner;
import pomoc.partner.form.model.FormPublication;
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
				+ "CASE WHEN count(p)=0 THEN false "
				+ "ELSE true END "
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

	public Long getPartnerPeopleCount(Partner partner) {
		Preconditions.checkNotNull(partner);
		Preconditions.checkNotNull(partner.getId());
		return em.createQuery("SELECT count(p) FROM Person p WHERE p.partner.id=:id", Long.class)
			.setParameter("id", partner.getId()).getSingleResult();
		
	}
	
	public List<Person> getPartnerPeople(Partner partner) {
		Preconditions.checkNotNull(partner);
		Preconditions.checkNotNull(partner.getId());
		return em.createQuery("SELECT p FROM Person p WHERE p.partner.id=:id", Person.class)
			.setParameter("id", partner.getId()).getResultList();
		
	}

	public Person getUserById(Long id) {
		Preconditions.checkNotNull(id);
		return em.find(Person.class, id);
	}

	public boolean willBeNoAdmin(Long id, Partner partner) {
		Preconditions.checkNotNull(id);
		Preconditions.checkNotNull(partner);
		Preconditions.checkNotNull(partner.getId());
		if ( em.createQuery("SELECT count(p) FROM Person p WHERE p.partner.id=:partnerId AND p.role=:role AND p.id != :id", Long.class)
				.setParameter("partnerId", partner.getId())
				.setParameter("role", Role.ADMIN)
				.setParameter("id", id)
				.getSingleResult() > 0) {
			return false;
		}
		return true;
	}

	public Map<FormPublication, Right> getFormRights(Person person) {
		Preconditions.checkNotNull(person);
		Preconditions.checkNotNull(person.getId());
		Map<FormPublication, Right> formRights = em.find(Person.class, person.getId()).getFormRights();
		formRights.size();
		return formRights;
	}
	
	public List<SelectItem> getAdministrators(Partner partner) {
		Preconditions.checkNotNull(partner);
		Preconditions.checkNotNull(partner.getId());
		TypedQuery<SelectItem> query = em.createQuery(
				"SELECT new javax.faces.model.SelectItem(p.id, p.name) from Person p WHERE p.partner.id=:id AND p.role=:role"
				+ " order by p.name", 
				SelectItem.class);
		query.setParameter("id", partner.getId());
		query.setParameter("role", Role.ADMIN);
		return query.getResultList();
	}
}
