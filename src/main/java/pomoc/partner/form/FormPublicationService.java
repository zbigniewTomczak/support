package pomoc.partner.form;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.Partner;
import pomoc.partner.form.model.FormPublication;
import pomoc.partner.person.Person;

import com.google.common.base.Preconditions;

@Stateless
public class FormPublicationService {

	@Inject
	private EntityManager em;

	public FormPublication getFormPublicationFormPublication(String key) {
		TypedQuery<FormPublication> query = em.createQuery("SELECT sp from FormPublication sp WHERE sp.key=:key", FormPublication.class);
		query.setParameter("key", key);
		List<FormPublication> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public List<FormPublicationListData> getPartnerForms(Person loggedPerson) {
		if (loggedPerson == null || loggedPerson.getId() == null) {
			return Collections.emptyList();
		}
		Preconditions.checkNotNull(loggedPerson.getId());
		loggedPerson = em.find(Person.class, loggedPerson.getId());
		Preconditions.checkNotNull(loggedPerson);
		
		TypedQuery<FormPublicationListData> query = em.createQuery(
				"SELECT new pomoc.company.form.FormPublicationListData(sp) FROM FormPublication sp  WHERE sp.partner.id=:id ORDER BY sp.name, sp.title", 
				FormPublicationListData.class);
		query.setParameter("id", loggedPerson.getPartner().getId());
		return query.getResultList();
	}

	public boolean hasAccess(FormPublication sF, Person loggedPerson) {
		// TODO Auto-generated method stub
		return true;
	}

	public void save(FormPublication formPublication) {
		em.merge(formPublication);
	}

	public String getFormFile(String key) {
		FormPublication formPublication= getFormPublicationFormPublication(key);
		if (formPublication.getFormDefinition() != null) {
			return formPublication.getFormDefinition().getFilename();
		}
		return null;
	}

	public List<FormPublication> getPartnerForms(Partner partner) {
		Preconditions.checkNotNull(partner);
		Preconditions.checkNotNull(partner.getId());
		TypedQuery<FormPublication> query = em.createQuery("SELECT sp from FormPublication sp WHERE sp.formDefinition.partner.id=:id", FormPublication.class);
		query.setParameter("id", partner.getId());
		return query.getResultList();
	}

}
