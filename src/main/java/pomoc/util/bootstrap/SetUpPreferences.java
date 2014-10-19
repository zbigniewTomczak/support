package pomoc.util.bootstrap;

import pomoc.partner.preferences.Preferences;

public class SetUpPreferences {
	public Preferences setUp() {
		Preferences pref = new Preferences();
		pref.setAddress("system.pomoc@gmail.com");
		pref.setFromName("Kwiaciarnia Różyczka");
		pref.setPassword("tojesthaslo");
		pref.setPop3Host("pop.gmail.com");
		pref.setSmtpHost("smtp.gmail.com");
		return pref;
//		insert into Preferences (id,  fromName, smtpHost, pop3Host, address, password) values 
//		(0, 'Kwiaciarnia Różyczka','smtp.gmail.com', 'pop.gmail.com','system.pomoc@gmail.com', 'tojesthaslo' );
	}
}
