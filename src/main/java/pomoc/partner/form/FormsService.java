package pomoc.partner.form;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pomoc.partner.Partner;
import pomoc.partner.form.model.FormDefinition;
import pomoc.partner.form.model.FormPublication;
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
		Map <String, Long> countMap = new HashMap<>();
		for (Object[] entry : countList) {
			countMap.put((String)entry[0], (Long)entry[1]);
		}
		
		for (FormDefinition form : list) {
			if (countMap.containsKey(form.getSkey())) {
				form.setResponsesTotal(countMap.get(form.getSkey()).intValue());
			}
		}
		return list;
	}

	public List<FormDefinition> getForms() {
		if (p == null || p.getId() == null) {
			log.warning("Partner null");
			return Collections.emptyList();
		}
		TypedQuery<FormDefinition> query = em.createQuery("SELECT f FROM FormDefinition f JOIN f.publication WHERE f.partner.id=:id", FormDefinition.class);
		query.setParameter("id", p.getId());
		List<FormDefinition> list = query.getResultList();
		return list;
	}

	public FormPublication getFormPublication(String skey) {
		if (p == null || p.getId() == null) {
			log.warning("Partner null");
			return null;
		}
		if (skey==null) {
			return null;
		}
		
		TypedQuery<FormPublication> query = em.createQuery(
				"SELECT f FROM FormPublication f WHERE f.formDefinition.skey=:skey AND f.formDefinition.partner.id=:id"
				, FormPublication.class);
		query.setParameter("id", p.getId());
		query.setParameter("skey", skey);
		List<FormPublication> list = query.getResultList();
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			log.warning("More than one FormDefinition for skey="+skey);
		}
		return list.get(0);
	}

	public FormDefinition getFormDefinition(String skey) {
		if (p == null || p.getId() == null) {
			log.warning("Partner null");
			return null;
		}
		if (skey==null) {
			return null;
		}
		
		TypedQuery<FormDefinition> query = em.createQuery(
				"SELECT f FROM FormDefinition f LEFT JOIN FETCH f.elements WHERE f.skey=:skey AND f.partner.id=:id"
				, FormDefinition.class);
		query.setParameter("id", p.getId());
		query.setParameter("skey", skey);
		List<FormDefinition> list = query.getResultList();
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			log.warning("More than one FormDefinition for skey="+skey);
		}
		return list.get(0);
	}

	public <T> T save(T entity) {
		return em.merge(entity);
	}

	public String newForm(String name) {
		String skey = name.toLowerCase();
    	skey = skey.replace(' ', '-');
    	try {
			skey = URLEncoder.encode(skey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	Long count;
    	int sufixer = 0;
    	String newSkey = skey;
    	do {
    		if (sufixer != 0) {
    			newSkey = skey + "-" + sufixer;
    		}
    		TypedQuery<Long> query = em.createQuery(
				"SELECT count(f) FROM FormDefinition f WHERE f.skey=:skey AND f.partner.id=:id"
				, Long.class);
    		query.setParameter("id", p.getId());
    		query.setParameter("skey", newSkey);
    		count = query.getSingleResult();
    		sufixer++;
    	} while (count != 0);
    	
    	FormDefinition fd = new FormDefinition();
    	fd.setPartner(p);
    	fd.setFormVersion(1);
    	fd.setSkey(newSkey);
    	fd.setName(name);
    	
    	em.persist(fd);
    	
    	FormPublication fp = new FormPublication();
    	fp.setActive(false);
    	fp.setFormDefinition(fd);
    	fp.setKey(UUID.randomUUID().toString());
    	em.persist(fp);
		return newSkey;
	}
	
	
}
