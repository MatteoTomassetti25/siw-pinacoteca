package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Credenziali {

	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credenziali_generator")
	@SequenceGenerator(name = "credenziali_generator", sequenceName = "credenziali_seq", allocationSize = 1)
	private Long id;

	@Column(unique = true)
	@NotBlank(message = "Username non può essere vuoto")
	@Size(min = 3, max = 50, message = "Username deve avere tra 3 e 50 caratteri")
	private String username;

	@NotBlank(message = "Password non può essere vuota")
    @Size(min = 3, message = "Password deve avere almeno 3 caratteri")
	private String password;
	
	
	private String role;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Utente utente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public static String getDefaultRole() {
		return DEFAULT_ROLE;
	}

	public static String getAdminRole() {
		return ADMIN_ROLE;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, password, role, username, utente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credenziali other = (Credenziali) obj;
		return Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(username, other.username)
				&& Objects.equals(utente, other.utente);
	}
	
	
}
