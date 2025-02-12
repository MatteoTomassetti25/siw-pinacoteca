package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Opera {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "opera_generator", sequenceName = "opera_seq", allocationSize = 1)
	private Long id;
	
	@Column(unique = false, nullable = false, length = 20)
	@NotBlank(message = "il titolo non puo' essere nullo")
	@Size(min = 1, max = 100, message = "il titolo deve essere compreso tra 1 e 100 caratteri")
	private String titolo;
	
	@Column(unique = false, nullable = false, length = 20)
	@NotBlank(message = "la tecnca non puo' essere nullo")
	@Size(min = 1, max = 100, message = "il titolo deve essere compreso tra 1 e 100 caratteri")
	private String tecnica;
	
	@Column(unique = false, nullable = false, length = 20)
	@NotNull(message = "L'anno non puo' essere nullo")
	private Integer anno;
	
	@OneToOne
	@JoinColumn(name = "id_area", nullable = false)
	private Area collocazione;
	
	@ManyToOne
	@JoinColumn(name = "id_artista", nullable = false)
	private Artista artista;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTecnica() {
		return tecnica;
	}

	public void setTecnica(String tecnica) {
		this.tecnica = tecnica;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Area getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(Area collocazione) {
		this.collocazione = collocazione;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anno, artista, collocazione, id, tecnica, titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Opera other = (Opera) obj;
		return Objects.equals(anno, other.anno) && Objects.equals(artista, other.artista)
				&& Objects.equals(collocazione, other.collocazione) && Objects.equals(id, other.id)
				&& Objects.equals(tecnica, other.tecnica) && Objects.equals(titolo, other.titolo);
	}

	
	
}
