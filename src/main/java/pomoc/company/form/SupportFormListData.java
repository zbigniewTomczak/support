package pomoc.company.form;

import pomoc.partner.SupportForm;

public class SupportFormListData {
	
	private final String name;
	private final String key;
	private final String title;
	
	public SupportFormListData(SupportForm form) {
		this.key = form.getKey();
		this.name = form.getName();
		this.title = form.getTitle();
	}

	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}
	
	
}
