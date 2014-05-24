package pomoc.customer;

import pomoc.partner.SupportForm;

public class SupportFormResponse {
	private String name;
	private String email;
	private String content;
	private SupportForm supportForm;
	
	@Override
	public String toString() {
		return String.format("SupportFormResponse(%s, %s)",name, email);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public SupportForm getSupportForm() {
		return supportForm;
	}
	public void setSupportForm(SupportForm supportForm) {
		this.supportForm = supportForm;
	}
	
	
}
