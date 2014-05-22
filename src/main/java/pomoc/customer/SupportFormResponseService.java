package pomoc.customer;

import java.util.logging.Logger;

import javax.inject.Inject;

public class SupportFormResponseService {

	@Inject 
	private Logger logger;
	
	public void save(SupportFormResponse supportFormResponse) {
		logger.info("Saved " + supportFormResponse);
		
	}

}
