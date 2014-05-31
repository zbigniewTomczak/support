package pomoc.email.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;

public class EmailClient {
	public static final void printMessageInfo(BufferedReader reader, int id)
			throws IOException {
		String from = "";
		String subject = "";
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			String lower = line.toLowerCase(Locale.ENGLISH);

			if (lower.startsWith("from: ")) {
				from = line.substring(6).trim();
			} else if (lower.startsWith("subject: ")) {
				subject = line.substring(9).trim();
			}
		}

		System.out.println(Integer.toString(id) + " From: " + from
				+ "  Subject: " + subject);
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.err
					.println("Usage: POP3Mail <pop3 server hostname> <username> <password> [TLS [true=implicit]]");
			System.exit(1);
		}

		String server = args[0];
		String username = args[1];
		String password = args[2];

		String proto = args.length > 3 ? args[3] : null;
		boolean implicit = args.length > 4 ? Boolean.parseBoolean(args[4])
				: false;

		POP3Client pop3;

		if (proto != null) {
			System.out.println("Using secure protocol: " + proto
					+ " and implicit: " + implicit);
			pop3 = new POP3SClient(proto, implicit);
		} else {
			pop3 = new POP3Client();
		}
		pop3.setDefaultPort(995);
		System.out.println("Connecting to server " + server + " on "
				+ pop3.getDefaultPort());

		// We want to timeout if a response takes longer than 60 seconds
		pop3.setDefaultTimeout(60000);

		// suppress login details
		pop3.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out), true));

		try {
			pop3.connect(server);
		} catch (IOException e) {
			System.err.println("Could not connect to server.");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			if (!pop3.login(username, password)) {
				System.err
						.println("Could not login to server.  Check password.");
				pop3.disconnect();
				System.exit(1);
			}

			pop3.reset();
			POP3MessageInfo[] messages = pop3.listMessages();
			POP3MessageInfo[] uniqueIdentifiers = pop3.listUniqueIdentifiers();
			ArrayUtils.reverse(messages);

			if (messages == null) {
				System.err.println("Could not retrieve message list.");
				pop3.disconnect();
				return;
			} else if (messages.length == 0) {
				System.out.println("No messages");
				pop3.logout();
				pop3.disconnect();
				return;
			}

			for (POP3MessageInfo msginfo : messages) {
				BufferedReader reader = (BufferedReader) pop3
						.retrieveMessage(msginfo.number);
				//ReaderInputStream i = new ReaderInputStream(reader);
				//MessageTree.main(i);
				/*MimeStreamParser parser = new MimeStreamParser();
				parser.setContentHandler(new AbstractContentHandler() {
					@Override
					public void field(org.apache.james.mime4j.stream.Field field)
							throws MimeException {
						System.out.println(field.getBody());
					}

					@Override
					public void startMessage() throws MimeException {
						System.out.println("startmessage");
						super.startMessage();
					}

					@Override
					public void endMessage() throws MimeException {
						System.out.println("endmessage");
						super.endMessage();
					}
				});
				parser.parse(i);*/
				// MimeMessage mm = new MimeMessage(null, i);
				// MimeMessageParser parser = new MimeMessageParser(mm);
				// System.out.println("OOOOOOO");
				// System.out.println(parser.hasHtmlContent());
				// System.out.println(parser.hasPlainContent());
				// System.out.println(parser.getSubject());
				if (reader == null) {
					System.err.println("Could not retrieve message header.");
					pop3.disconnect();
					System.exit(1);
				}
				// reader = (BufferedReader)
				//pop3.retrieveMessage(msginfo.number);
				printMessageInfo(reader, msginfo.number);
			}

			pop3.logout();
			pop3.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
