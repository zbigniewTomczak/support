package pomoc.partner.person;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import pomoc.partner.Partner;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	@ManyToOne
	private Partner partner;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public boolean isAdmin() {
		if (role != null && role == Role.ADMIN) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
