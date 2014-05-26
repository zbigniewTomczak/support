package pomoc.partner;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class PartnersBean {
	@Inject
	private PartnerService partnerService;
	public List<Partner> getPartnersList() {
		return partnerService.getAllPartners();
	}
}
