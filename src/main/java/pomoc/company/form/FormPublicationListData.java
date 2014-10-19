package pomoc.company.form;

import pomoc.partner.FormPublication;

public class FormPublicationListData {
	
	private final String name;
	private final String key;
	private final String title;
	
	public FormPublicationListData(FormPublication form) {
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
