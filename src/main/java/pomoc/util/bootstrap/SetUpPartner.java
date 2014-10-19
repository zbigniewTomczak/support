package pomoc.util.bootstrap;

import pomoc.partner.Partner;
import pomoc.partner.preferences.Preferences;

public class SetUpPartner {

	public Partner setUp(Preferences pref) {
//		insert into Partner (id, name, preferences_id) values (0, 'Kwiaciarnia różyczka', 0);
		Partner p = new Partner();
		p.setName("Kwiaciarnia różyczka");
		p.setPreferences(pref);
		return p;
	}

}
