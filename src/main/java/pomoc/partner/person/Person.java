package pomoc.partner.person;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import pomoc.partner.Partner;
import pomoc.partner.form.model.FormPublication;

@Entity
public class Person {

	@PrePersist
	@PreUpdate
	public void pre() {
		System.out.println("Name: " + name);
	}
	
    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;
	private String email;
	private String password;
	private String name;
	@Enumerated(EnumType.STRING)
	private Role role;
	@ManyToOne
	private Partner partner;
	@ElementCollection(fetch=FetchType.EAGER)
	@MapKeyJoinColumn(name="FormPublication_id")
	private Map<FormPublication, Right> formRights; 
	
	public boolean isAdmin() {
		if (role != null && role == Role.ADMIN) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canEdit(FormPublication sf) {
		return hasRight(sf, Right.EDIT);
	}

	public boolean canView(FormPublication sf) {
		return hasRight(sf, Right.READ_ONLY) || canEdit(sf);
	}

	private boolean hasRight(FormPublication sf, Right r) {
		if (isAdmin()) {
			return true;
		}
		if (sf == null || formRights==null) {
			return false;
		}
		if (formRights.get(sf) == null) {
			return false;
		}
		return formRights.get(sf)==r;
	}

	/* Getters & Setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Map<FormPublication, Right> getFormRights() {
		return formRights;
	}

	public void setFormRights(Map<FormPublication, Right> formRights) {
		this.formRights = formRights;
	}

	
	
}
