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

public class GmailSimpleEmailParseTest {

	static String messageText = "MIME-Version: 1.0"
			+"\nReturn-Path: <system.pomoc@gmail.com>"
			+"\nReceived: from ex-std-node148.prod.rhcloud.com (ec2-23-22-38-100.compute-1.amazonaws.com. [23.22.38.100])"
			+"\n        by mx.google.com with ESMTPSA id s2sm12073505qaj.36.2014.05.31.10.00.00"
			+"\n        for <system.pomoc@gmail.com>"
			+"\n        (version=TLSv1 cipher=RC4-SHA bits=128/128);"
			+"\n        Sat, 31 May 2014 10:00:01 -0700 (PDT)"
			+"\nDate: Sat, 31 May 2014 13:00:00 -0400 (EDT)"
			+"\nFrom: system.pomoc@gmail.com"
			+"\nTo: system.pomoc@gmail.com"
			+"\nMessage-ID: <25715309.21.1401555600009.JavaMail.5378a31be0b8cd0a280004cf@ex-std-node148.prod.rhcloud.com>"
			+"\nSubject: Heartbeat"
			+"\nMIME-Version: 1.0"
			+"\nContent-Type: text/plain; charset=us-ascii"
			+"\nContent-Transfer-Encoding: 7bit"
			+"\n"
			+"\nPinging";
	
	static String messageText2 = "MIME-Version: 1.0"
	+"\nReceived: by 10.152.147.37 with HTTP; Sat, 31 May 2014 10:31:49 -0700 (PDT)"
	+"\nDate: Sat, 31 May 2014 19:31:49 +0200"
	+"\nDelivered-To: system.pomoc@gmail.com"
	+"\nMessage-ID: <CALDiiswcmzdCu=r0fvKvp8ND2w1MiGsjFP5pDBR5FxQC8gbVeg@mail.gmail.com>"
	+"\nSubject: BLE"
	+"\nFrom: Program Pomoc <system.pomoc@gmail.com>"
	+"\nTo: Program Pomoc <system.pomoc@gmail.com>"
	+"\nContent-Type: text/plain; charset=UTF-8"
	+"\n"
	+"\nAC";
	
	@Test
	public void parseTest1() throws MimeException, IOException {
        final MessageBuilder builder = new DefaultMessageBuilder();
        final Message message = builder.parseMessage(new ByteArrayInputStream(messageText.getBytes()));

        MimeTree tree = new MimeTree(message);
        Assert.assertEquals("Pinging", tree.getPlainMessage());
	}
	
	@Test
	public void parseTest2() throws MimeException, IOException {
        final MessageBuilder builder = new DefaultMessageBuilder();
        final Message message = builder.parseMessage(new ByteArrayInputStream(messageText2.getBytes()));

        MimeTree tree = new MimeTree(message);
        Assert.assertEquals("AC", tree.getPlainMessage());
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
       Assert.assertEquals(new MailDateFormat().parse("Sat, 31 May 2014 13:00:00 -0400 (EDT)"), tree.getDate());
		
	}
	
	@Test
	public void subjectTest() throws MimeException, IOException, ParseException {
		final MessageBuilder builder = new DefaultMessageBuilder();
        final Message message = builder.parseMessage(new ByteArrayInputStream(messageText.getBytes()));

        MimeTree tree = new MimeTree(message);
       Assert.assertEquals("Heartbeat", tree.getSubject());
		
	}
	
	public static void main(String[] args) throws MimeException, IOException {
		MessageTree.main(new ByteArrayInputStream(messageText.getBytes()));
		
	}
}
