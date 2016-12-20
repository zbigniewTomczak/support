package pomoc.email;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import pomoc.customer.communication.CommunicationCase;
import pomoc.partner.preferences.Preferences;
import pomoc.partner.ticket.Ticket;

import com.google.common.base.Preconditions;

@Stateless
public class EmailSender {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger log;
	
	public void send(CommunicationCase communicationCase) throws EmailException {
		Preconditions.checkNotNull(communicationCase);
		Preconditions.checkNotNull(communicationCase.getId());
		communicationCase = em.find(CommunicationCase.class, communicationCase.getId());
		Preferences preferences = communicationCase.getTicket().getPartner().getPreferences();
		Email email = new HtmlEmail();
		email.setHostName(preferences.getSmtpHost());
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(preferences.getAddress(), preferences.getPassword()));
		email.setSSLOnConnect(true);
		email.setFrom(preferences.getAddress());
		email.setSubject("#"+communicationCase.getTicket().getNumber());
		email.setMsg(communicationCase.getContent());
		email.addTo(communicationCase.getTicket().getFormResponse().getEmail());
		email.send();
		
	}
	
	public void send(Ticket ticket, String emailString, String message, Preferences preferences) {
		Preconditions.checkNotNull(ticket);
		Preconditions.checkNotNull(emailString);
		Preconditions.checkNotNull(message);
		Preconditions.checkNotNull(preferences);
		Email email = new HtmlEmail();
		email.setHostName(preferences.getSmtpHost());
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(preferences.getAddress(), preferences.getPassword()));
		email.setSSLOnConnect(true);
		email.setSubject("#"+ticket.getNumber());
		try {
			email.setFrom(preferences.getAddress());
			email.setMsg(message);
			email.addTo(emailString);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	//@Schedule(minute="*/2",hour="*", persistent=false)
	public void heatbeat() throws EmailException {
		log.info("Heartbeat");
	}
	
	//@Schedule(hour="*/2", persistent=false)
	public void heatbeatEmail() throws EmailException {
		Preferences preferences = em.find(Preferences.class, 0L);
		Email email = new SimpleEmail();
		email.setHostName(preferences.getSmtpHost());
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(preferences.getAddress(), preferences.getPassword()));
		email.setSSLOnConnect(true);
		email.setFrom(preferences.getAddress());
		email.setSubject("#111 Heartbeat");
		email.setMsg("Pinging");
		email.addTo(preferences.getAddress());
		email.send();
	}

}
