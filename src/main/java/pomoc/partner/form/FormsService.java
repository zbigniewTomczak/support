package pomoc.partner.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pomoc.partner.Partner;
import pomoc.partner.form.model.FormDefinition;
import pomoc.partner.form.qualifier.AllForms;
import pomoc.partner.login.Current;

@Stateless
public class FormsService {
	
	@Inject @Current
	private Partner p;
	
	@Inject
	private EntityManager em;

	@Inject
	private Logger log;
	
	@Produces @AllForms
	public List<FormDefinition> getAllForms() {
		if (p == null || p.getId() == null) {
			log.warning("Partner null");
		}
		List<FormDefinition> list = getForms();
		
		Query countQuery = em.createQuery(
				"SELECT f.skey, count(r) FROM FormResponse r JOIN r.formDefinition f WHERE f.partner.id=:id GROUP BY f.skey");
		countQuery.setParameter("id", p.getId());
		@SuppressWarnings("unchecked")
		List<Object[]> countList = countQuery.getResultList();
		Map <String, Integer> countMap = new HashMap<String, Integer>();
		for (Object[] entry : countList) {
			countMap.put((String)entry[0], (Integer)entry[1]);
		}
		
		for (FormDefinition form : list) {
			if (countMap.containsKey(form.getSkey())) {
				form.setResponsesTotal(countMap.get(form.getSkey()));
			}
		}
		return list;
	}

	public List<FormDefinition> getForms() {
		if (p == null || p.getId() == null) {
			log.warning("Partner null");
		}
		TypedQuery<FormDefinition> query = em.createQuery("SELECT f FROM FormDefinition f JOIN f.publication WHERE f.partner.id=:id", FormDefinition.class);
		query.setParameter("id", p.getId());
		List<FormDefinition> list = query.getResultList();
		return list;
	}
	
	
}
