package pomoc.util.bootstrap;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pomoc.partner.Partner;
import pomoc.partner.form.model.FormDefinition;
import pomoc.partner.form.model.FormPublication;
import pomoc.partner.form.response.FormResponse;
import pomoc.partner.person.Person;
import pomoc.partner.preferences.Preferences;
import pomoc.partner.ticket.Ticket;

@Stateless
public class SetUpSampleData {
	@Inject
	private EntityManager em;

	public void setUp() {
		Preferences preferences = new SetUpPreferences().setUp();
		em.persist(preferences);
		Partner partner = new SetUpPartner().setUp(preferences);
		em.persist(partner);
		Person admin = new SetUpAdmin().setUp(partner);
		em.persist(admin);
		FormDefinition form = new SetUpForm().setUp(partner);
		em.persist(form);
		FormPublication formPub = new SetUpForm().setUpPublication(form);
		em.persist(formPub);
		Person user = new SetUpUser().setUp(partner, formPub);
		em.persist(user);
		FormResponse formRes = new SetUpResponse().setUp(form);
		em.persist(formRes);
		Ticket ticket = new SetUpTicket().setUp(formRes);
		em.persist(ticket);
		
		

//
//		insert into FormResponse (content, email, name, formPublication_id, id) values ('Nie działa drukarka przy stanowisku obsługi klienta.', 'system.pomoc@gmail.com', 'Tadeusz Boniek', '0', 0)
//		insert into Ticket (date, number, partner_id, status, formResponse_id, id) values ('2014-05-26 22:19:05.000', '1111', 0, 'NEW', 0, 0)
		
	}
	
	
}
