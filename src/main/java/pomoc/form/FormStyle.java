package pomoc.form;

import pomoc.partner.SupportForm;

public class FormStyle {

	public FormStyle(SupportForm supportForm) {
		if (supportForm == null) {
			this.uiGrowlLeft = 100;
			this.uiGrowlTop = 100;
			this.panelWidth = 700;
			this.panelHeight = 300;
			this.submitButtonWidthStyle = "";
			this.submitButtonMarginLeftStyle = "";

			return;
		}
		
		this.panelWidth = (int) Math.round(supportForm.getWidth() * 0.98);;
		this.panelHeight = (int) Math.round(supportForm.getHeight() * 0.98);;
		this.uiGrowlLeft = (panelWidth - 301)/2;
		this.uiGrowlTop = panelHeight/8;
		this.submitButtonWidthStyle = "width: " + panelWidth/3 + "px;";
		this.submitButtonMarginLeftStyle = "margin-left: " + panelWidth/3 + "px;";
		
	}
	private final int uiGrowlLeft;
	private final int uiGrowlTop;
	private final int panelWidth;
	private final int panelHeight;
	private final String submitButtonWidthStyle;
	private final String submitButtonMarginLeftStyle;
	
	public int getUiGrowlLeft() {
		return uiGrowlLeft;
	}
	public int getUiGrowlTop() {
		return uiGrowlTop;
	}
	public int getPanelWidth() {
		return panelWidth;
	}
	public int getPanelHeight() {
		return panelHeight;
	}
	public String getSubmitButtonWidthStyle() {
		return submitButtonWidthStyle;
	}
	public String getSubmitButtonMarginLeftStyle() {
		return submitButtonMarginLeftStyle;
	}
	
	
}
