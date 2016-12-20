package pomoc.partner.ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.annotation.Rule;
import org.ocpsoft.rewrite.faces.annotation.Deferred;

import pomoc.form.FormElement;
import pomoc.form.FormEmail;
import pomoc.partner.Partner;
import pomoc.partner.login.Current;
import pomoc.partner.person.Person;
import pomoc.partner.person.PersonService;
import pomoc.util.faces.FacesMessage;

@Model
@Rule("formEdit")
@Join(path="/ticket/{number}", to="/support/ticket2.jsf")
public class TicketEditBean2 {
	@Parameter
    private Integer number;
    
	@Produces
	@Named
	private Ticket ticket;
	
	private Status ticketStatus;
	
	@Produces
	@Named
	private List<FormElement> elements;
	
	@Produces
	@Named
	private List<Object> responses;
	
	@Inject
	private TicketService ts;
	
	@Inject
	private FacesMessage facesMessage;
	
	private Boolean editRights = false;

	private String message;
	
	private Boolean canRespond = false;
	
	@Inject @Current
	private Person loggedPerson;
	
	@Inject @Current
	private Partner partner;
	
	@Produces
	@Named
	private List<SelectItem> peopleWithEditRights;

	@Inject
	private PersonService ps;

	private String email;
	
    @RequestAction
	@Deferred
	public void load() throws IOException
	{
    	if (ticket != null) {
    		return;
    	}
		if (number != null) {
			ticket = ts.getTicket(number);
	    } else {
	    	ticket = new Ticket();
	    }
		ticketStatus = ticket.getStatus();
		elements = new ArrayList<>();
		responses = new ArrayList<>();
		SortedMap<FormElement, Object> map=new TreeMap<>(new Comparator<FormElement>() {
			@Override
			public int compare(FormElement f1, FormElement f2) {
				return f1.getElementOrder().compareTo(f2.getElementOrder());
			}
		});
		map.putAll(ticket.getFormResponse().getResponses());
		for(FormElement element : map.keySet()) {
			elements.add(element);
			responses.add(ticket.getFormResponse().getResponses().get(element));
		}
		
		if (loggedPerson.isAdmin() || loggedPerson.canEdit(ticket.getFormResponse().getFormDefinition().getPublication())) {
			editRights = true;
			peopleWithEditRights = new ArrayList<>(ps.getAdministrators(partner));
			peopleWithEditRights.addAll(ts.getUsersWithEditRights(ticket));
			if (ticket.getAssignee() == null) {
				peopleWithEditRights.add(0, new SelectItem(null, " --- "));
			}
			email = getEmail();
			if (email != null && !email.isEmpty()) {
				canRespond = true;
			}
		}
	}
    
    private String getEmail() {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i) instanceof FormEmail && responses.get(i) instanceof String) {
				return (String) responses.get(i);
			}
		}
		return null;
	}

	public String save() {
    	try {
    		ticket.setStatus(ticketStatus, loggedPerson);
    		ticket = ts.save(ticket);
    		facesMessage.postInfo("Zapisano zmiany #"+number);
    	} catch (EJBException e) {
    		e.printStackTrace();
    		facesMessage.postError("Błąd zapisu #"+number);
    	}
    	return "/support/ticket2.jsf?faces-redirect=true&number=" + number;
    }
    
    public String sendMessage() {
    	try {
    		InternetAddress emailAddr = new InternetAddress(email);
    		emailAddr.validate();
    		ticket = ts.saveAndSendResponse(ticket, email, message);
    		facesMessage.postInfo("Wysłano wiadomość do " + email);
    	} catch (EJBException e) {
    		e.printStackTrace();
    		facesMessage.postError("Błąd wysyłania wiadomości");
    	} catch (AddressException e) {
    		e.printStackTrace();
    		facesMessage.postError("Niepoprawny adres email: " + email);
	    }
    	return "/support/ticket2.jsf?faces-redirect=true&number=" + number;
    }
    
    @Produces
	@Named
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public List<FormElement> getElements() {
		return elements;
	}

	public void setElements(List<FormElement> elements) {
		this.elements = elements;
	}

	public List<Object> getResponses() {
		return responses;
	}

	public void setResponses(List<Object> responses) {
		this.responses = responses;
	}

	public Boolean getEditRights() {
		return editRights;
	}

	public List<SelectItem> getPeopleWithEditRights() {
		return peopleWithEditRights;
	}

	public Status getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Status ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getCanRespond() {
		return canRespond;
	}

	public void setCanRespond(Boolean canRespond) {
		this.canRespond = canRespond;
	}
    
    
}
