package pomoc.util.bootstrap;

import pomoc.partner.Partner;
import pomoc.partner.form.model.FormDefinition;
import pomoc.partner.form.model.FormPublication;

public class SetUpForm {

	public FormDefinition setUp(Partner partner) {
//		insert into FormDefinition (id, filename) values (0, 'simple_contact_form_1.xhtml')
		FormDefinition form = new FormDefinition();
		form.setName("Formularz kontaktowy");
		form.setSkey("formularz-kontaktowy");
		form.setFormVersion(1);
		form.setPartner(partner);
		return form;
	}

	public FormPublication setUpPublication(FormDefinition form) {
//		insert into FormPublication (id, name, partner_id, supportFormTemplate_id, key, title, confirmationMessage, width, height) values 
//		(0, 'Formularz kontaktowy', 0, 0, '6374fc35-24d2-4cc1-9a57-326696a2f6f6', 
//		'Skontaktuj się z nami poprzez poniższy formularz','Dziękujemy! Twoja wiadomość została wysłana.', 710, 300);
		
		FormPublication fp = new FormPublication();
		fp.setTitle("Skontaktuj się z nami poprzez poniższy formularz");
		fp.setKey("6374fc35-24d2-4cc1-9a57-326696a2f6f6");
		fp.setFormDefinition(form);
		fp.setConfirmationMessage("Dziękujemy! Twoja wiadomość została wysłana.");
		fp.setHeight(300);
		fp.setWidth(710);
		return fp;
	}

}
