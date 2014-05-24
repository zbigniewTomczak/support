package pomoc.company.form;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import pomoc.customer.SupportFormResponse;
import pomoc.customer.SupportFormResponseService;
import pomoc.partner.SupportForm;

import com.google.common.base.Preconditions;

public class SupportFormService {

	@Inject
	private EntityManager em;
	
	@Inject
	private SupportFormResponseService supportFormResponseService;

	public SupportForm getSupportForm(String key) {
		return new SupportForm();
	}

	public void saveNewFormResponse(SupportFormResponse supportFormResponse,
			String key) {
		Preconditions.checkNotNull(supportFormResponse);
		Preconditions.checkNotNull(key);
		SupportForm supportForm = getSupportForm(key);
		supportFormResponse.setSupportForm(supportForm);
		em.persist(supportFormResponse);
	}

}
