package pomoc.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;
import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.dom.Message;
import org.apache.james.mime4j.dom.MessageBuilder;
import org.apache.james.mime4j.message.DefaultMessageBuilder;

import pomoc.customer.communication.CommunicationService;
import pomoc.partner.Partner;
import pomoc.partner.preferences.Preferences;

@Stateless
public class EmailParser {
	
	@Inject
	private CommunicationService communicationService;
	@Inject
	private EntityManager em;
	@Inject
	private Logger log;
	
	@Schedule(minute="*/5",hour="*/1", persistent=false)
	public void checkMailbox() throws MimeException {
		Partner partner = em.find(Partner.class, 0L);
		Preferences preferences = partner.getPreferences();
		List<MailboxCheckHistory> history = partner.getHistory();
		Date lastCheck = null;
		if (!history.isEmpty()) {
			lastCheck = history.get(0).getDate(); 
		}
		POP3Client pop3 = new POP3SClient("TLS", true);
        pop3.setDefaultPort(995);
        pop3.setDefaultTimeout(60000);
        //pop3.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        Date now = new Date();
        int counter = 0;
        log.info("Connecting to server "+preferences.getPop3Host()+" on "+pop3.getDefaultPort());
        try
        {
            pop3.connect(preferences.getPop3Host());
        }
        catch (IOException e)
        {
            log.severe("Could not connect to server.");
            e.printStackTrace();
            return;
        }
        try
        {
            if (!pop3.login(preferences.getAddress(), preferences.getPassword()))
            {
            	log.severe("Could not login to server.  Check password.");
                pop3.disconnect();
                return;
            }

            POP3MessageInfo[] messages = pop3.listMessages();
            ArrayUtils.reverse(messages);
            if (messages == null)
            {
            	log.severe("Could not retrieve message list.");
                pop3.disconnect();
                return;
            }
            else if (messages.length == 0)
            {
                log.info("No messages");
                pop3.logout();
                pop3.disconnect();
                return;
            }

            for (POP3MessageInfo msginfo : messages) {
                BufferedReader reader = (BufferedReader) pop3.retrieveMessage(msginfo.number);
                if (reader == null) {
                	log.severe("Could not retrieve message header.");
                    pop3.disconnect();
                    return;
                }
                ReaderInputStream iStream = new ReaderInputStream(reader);
                if (handleMessage(iStream, msginfo.number, lastCheck, partner)) {
                	counter++;
                }
            }

            pop3.logout();
            pop3.disconnect();
            
            log.info("Found "+counter+" respnses.");
            MailboxCheckHistory h = new MailboxCheckHistory();
            h.setDate(now);
            h.setPartner(partner);
            h.setProcessedNumber(counter);
            em.persist(h);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
		
	}
	private boolean handleMessage(InputStream iStream, int messageOrdinal,
			Date lastCheck, Partner partner) throws IOException, MimeException {
		final MessageBuilder builder = new DefaultMessageBuilder();
        final Message message = builder.parseMessage(iStream);
		MimeTree tree = new MimeTree(message);
		Date date = tree.getDate();
		if (lastCheck != null && lastCheck.before(date)) {
			String subject = tree.getSubject();
			Pattern pattern = Pattern.compile("#\\d+");
    		Matcher matcher = pattern.matcher(subject);
         	if(matcher.find()) {
         		String number = matcher.group().substring(1);
         		log.info("Found message with number: " + number);
         		communicationService. registerClientResponse(number, tree.getFrom(), date, tree.getPlainMessage(), partner);
         		return true;
         	}
		}
       	return false;
	}
}
