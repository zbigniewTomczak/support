package pomoc.email.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.mail.internet.MailDateFormat;

import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.dom.Message;
import org.apache.james.mime4j.dom.MessageBuilder;
import org.apache.james.mime4j.message.DefaultMessageBuilder;
import org.junit.Assert;
import org.junit.Test;

import pomoc.email.MimeTree;

public class GmailMultipartEmailParseTest {

	static String messageText = "MIME-Version: 1.0"
+"\nReceived: by 10.152.147.37 with HTTP; Sat, 31 May 2014 10:20:01 -0700 (PDT)"
+"\nIn-Reply-To: <25715309.21.1401555600009.JavaMail.5378a31be0b8cd0a280004cf@ex-std-node148.prod.rhcloud.com>"
+"\nReferences: <25715309.21.1401555600009.JavaMail.5378a31be0b8cd0a280004cf@ex-std-node148.prod.rhcloud.com>"
+"\nDate: Sat, 31 May 2014 19:20:01 +0200"
+"\nDelivered-To: system.pomoc@gmail.com"
+"\nMessage-ID: <CALDiisweF9xXPEQW3q3xYbNCKo3ORT-NLXG0M-N5rn1DBzKc+w@mail.gmail.com>"
+"\nSubject: Re: Heartbeat"
+"\nFrom: Program Pomoc <system.pomoc@gmail.com>"
+"\nTo: Program Pomoc <system.pomoc@gmail.com>"
+"\nContent-Type: multipart/alternative; boundary=f46d04083a6b4a31f704fab55f5b"
+"\n"
+"\n--f46d04083a6b4a31f704fab55f5b"
+"\nContent-Type: text/plain; charset=UTF-8"
+"\n"
+"\nAAAA"
+"\n"
+"\n"
+"\n2014-05-31 19:00 GMT+02:00 <system.pomoc@gmail.com>:"
+"\n"
+"\n> Pinging"
+"\n>"
+"\n"
+"\n--f46d04083a6b4a31f704fab55f5b"
+"\nContent-Type: text/html; charset=UTF-8"
+"\n"
+"\n<div dir=\"ltr\">AAAA<br></div><div class=\"gmail_extra\"><br><br><div class=\"gmail_quote\">2014-05-31 19:00 GMT+02:00  <span dir=\"ltr\">&lt;<a href=\"mailto:system.pomoc@gmail.com\" target=\"_blank\">system.pomoc@gmail.com</a>&gt;</span>:<br>"
+"\n<blockquote class=\"gmail_quote\" style=\"margin:0 0 0 .8ex;border-left:1px #ccc solid;padding-left:1ex\">Pinging<br>"
+"\n</blockquote></div><br></div>"
+"\n"
+"\n--f46d04083a6b4a31f704fab55f5b--";
	
	@Test
	public void parseTest() throws MimeException, IOException {
        final MessageBuilder builder = new DefaultMessageBuilder();
        final Message message = builder.parseMessage(new ByteArrayInputStream(messageText.getBytes()));

        MimeTree tree = new MimeTree(message);
        String expected = "AAAA"
                +"\n"
                +"\n"
                +"\n2014-05-31 19:00 GMT+02:00 <system.pomoc@gmail.com>:"
                +"\n"
                +"\n> Pinging"
                +"\n>"
                +"\n";
       Assert.assertEquals(expected, tree.getPlainMessage());
	}
	
	@Test
	public void fromTest() throws MimeException, IOException {
		final MessageBuilder builder = new DefaultMessageBuilder();
        final Message message = builder.parseMessage(new ByteArrayInputStream(messageText.getBytes()));

        MimeTree tree = new MimeTree(message);
       Assert.assertEquals("system.pomoc@gmail.com", tree.getFrom());
		
	}
	
	@Test
	public void dateTest() throws MimeException, IOException, ParseException {
		final MessageBuilder builder = new DefaultMessageBuilder();
        final Message message = builder.parseMessage(new ByteArrayInputStream(messageText.getBytes()));

        MimeTree tree = new MimeTree(message);
       Assert.assertEquals(new MailDateFormat().parse("Sat, 31 May 2014 19:20:01 +0200"), tree.getDate());
		
	}
	
	@Test
	public void subjectTest() throws MimeException, IOException, ParseException {
		final MessageBuilder builder = new DefaultMessageBuilder();
        final Message message = builder.parseMessage(new ByteArrayInputStream(messageText.getBytes()));

        MimeTree tree = new MimeTree(message);
       Assert.assertEquals("Re: Heartbeat", tree.getSubject());
		
	}
	
	public static void main(String[] args) throws MimeException, IOException {
		MessageTree.main(new ByteArrayInputStream(messageText.getBytes()));
		
	}
}
