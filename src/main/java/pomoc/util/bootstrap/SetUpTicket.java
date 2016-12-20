package pomoc.util.bootstrap;

import java.util.Date;

import pomoc.partner.form.response.FormResponse;
import pomoc.partner.ticket.Ticket;

public class SetUpTicket {

	public Ticket setUp(FormResponse formResponse) {
		Ticket ticket = new Ticket();
		ticket.setDate(new Date());
		ticket.setNumber(1);
		ticket.setFormResponse(formResponse);
		return ticket;
	}

}
