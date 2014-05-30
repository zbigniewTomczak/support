package pomoc.partner.ticket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import pomoc.customer.communication.CommunicationCaseData;
import pomoc.partner.login.LoggedPersonService;

@Model
public class TicketEditBean {
	@Inject
	private Logger log;
	
	@Inject
	private LoggedPersonService loggedPersonService;

	@Inject
	private TicketService ticketService;
	
	
	private TicketStaticData staticData;
	private TicketEditableData editable;

	@PostConstruct
	public void init() {
		String number = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("number");
		if (number == null) {
			//todo post faces error
			return;
		}
		if (loggedPersonService.getLoggedPerson() == null) {
			//todo post faces error
			return;
		}
		staticData =  ticketService.getStaticData(number, loggedPersonService.getLoggedPerson());
		editable =  ticketService.getEditableData(number, loggedPersonService.getLoggedPerson());
	}
	
	public String save() {
		if (staticData == null || editable == null) {
			//todo post faces error
			return null;
		}
		if (loggedPersonService.getLoggedPerson() == null) {
			//todo post faces error
			return null;
		}
		try {
			ticketService.saveAllAndSendResponse(staticData, editable, loggedPersonService.getLoggedPerson());
		} catch (EJBException e) {
			//todo post faces error
			//todo log error
			return null;
		}
		return "dashboard";
	}

	public void send() {
		if (staticData == null || editable == null) {
			//todo post faces error
			return;
		}
		if (loggedPersonService.getLoggedPerson() == null) {
			//todo post faces error
			return;
		}
		try {
			ticketService.saveAndSendResponse(staticData, editable, loggedPersonService.getLoggedPerson());
		} catch (EJBException e) {
			//todo post faces error
			//todo log error
			return;
		}

	}
	
	private List<CommunicationCaseData> communicationHistory;
	public List<CommunicationCaseData> getCommunicationHistory() {
		if (communicationHistory == null) {
			communicationHistory = ticketService.getCommunicationCasesForTicket(staticData.getNumber(), loggedPersonService.getLoggedPerson());
		}
		return communicationHistory;
	}
	
	public List<SelectItem> getEmptyOption() {
		if (editable.getAssigneeEmail() == null) {
			return Arrays.asList(new SelectItem(null, "---"));
		}
		return Collections.emptyList();
	}
	
	public TicketStaticData getStatic() {
		return staticData;
	}

	public void setStatic(TicketStaticData staticData) {
		this.staticData = staticData;
	}

	public TicketEditableData getEditable() {
		return editable;
	}

	public void setEditable(TicketEditableData editable) {
		this.editable = editable;
	}
	

	
}
