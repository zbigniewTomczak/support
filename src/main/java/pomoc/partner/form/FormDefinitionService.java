package pomoc.partner.form;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pomoc.partner.form.model.FormDefinition;
import pomoc.partner.form.model.FormPublication;

@Stateless
public class FormDefinitionService {

	@Inject
	private EntityManager em;

	public FormDefinition getFormDefinition(FormPublication pub) {
		TypedQuery<FormDefinition> query = em.createQuery("SELECT fd from FormDefinition fd LEFT JOIN FETCH fd.elements WHERE fd.publication.id=:id", FormDefinition.class);
		query.setParameter("id", pub.getId());
		List<FormDefinition> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}