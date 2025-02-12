package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Artista {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "artista_generator", sequenceName = "artista_seq", allocationSize = 1)
	private Long id;
	
	@Column(unique = false, nullable = false, length = 20)
	@NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
	private String nome;
	
	@Column(unique = false, nullable = false, length = 20)
	@NotBlank(message = "Il cognome non può essere vuoto")
    @Size(min = 2, max = 50, message = "Il cognome deve avere tra 2 e 50 caratteri")
	private String cognome;

	@Column(nullable=false)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataNascita;
	
	@Column(unique = false, nullable = false, length = 20)
	@NotBlank(message = "il luogo di nascita non deve essere nullo")
	private String luogoNascita;
	
	@Column(nullable=true)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataMorte;
	
	@OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Opera> opereArtista = new ArrayList<Opera>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public LocalDate getDataMorte() {
		return dataMorte;
	}

	public void setDataMorte(LocalDate dataMorte) {
		this.dataMorte = dataMorte;
	}

	public List<Opera> getOpereArtista() {
		return opereArtista;
	}

	public void setOpereArtista(List<Opera> opereArtista) {
		this.opereArtista = opereArtista;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataMorte, dataNascita, id, luogoNascita, nome, opereArtista);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Artista other = (Artista) obj;
	    return Objects.equals(id, other.id) &&
	           Objects.equals(nome, other.nome) &&
	           Objects.equals(cognome, other.cognome);
	}

	
	
}
