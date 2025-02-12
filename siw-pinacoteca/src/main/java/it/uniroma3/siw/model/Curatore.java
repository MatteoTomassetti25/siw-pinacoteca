package it.uniroma3.siw.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Curatore {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curatore_generator")
    @SequenceGenerator(name = "curatore_generator", sequenceName = "curatore_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codFiscale;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
    private String nome;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Il cognome non può essere vuoto")
    @Size(min = 2, max = 50, message = "Il cognome deve avere tra 2 e 50 caratteri")
    private String cognome;

    @Column(nullable = false)
    @NotNull(message = "la data di nascita non deve essere nulla")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascita;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Il luogo di nascita non può essere vuoto")
    private String luogoNascita;

    @OneToOne(mappedBy = "curatore", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore // Evita la serializzazione ricorsiva JSON
    private Area area;

    // Metodo per generare un codice fiscale fittizio
    private String generaCodiceFiscale() {
        return (cognome.length() >= 3 ? cognome.substring(0, 3) : cognome + "X").toUpperCase()
                + (nome.length() >= 3 ? nome.substring(0, 3) : nome + "X").toUpperCase()
                + (dataNascita != null ? String.valueOf(dataNascita.getYear()).substring(2, 4) : "00")
                + (luogoNascita.length() >= 2 ? luogoNascita.substring(0, 2) : "XX").toUpperCase()
                + (int) (Math.random() * 900 + 100); // Numero casuale per evitare duplicati
    }

    public void setCodicefiscaleNull() {
    	this.codFiscale = null;
    }
    
    @PrePersist
    public void assegnaCodiceFiscale() {
        if (this.codFiscale == null || this.codFiscale.isEmpty()) {
            this.codFiscale = generaCodiceFiscale();
        }
    }

    // Metodo per impostare l'area e mantenere la relazione bidirezionale
    public void setArea(Area area) {
        this.area = area;
        if (area != null && area.getCuratore() != this) {
            area.setCuratore(this);
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getCodFiscale() {
        return codFiscale;
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

    public Area getArea() {
        return area;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codFiscale, area, cognome, dataNascita, id, luogoNascita, nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Curatore other = (Curatore) obj;
        return Objects.equals(codFiscale, other.codFiscale) && Objects.equals(area, other.area)
                && Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
                && Objects.equals(id, other.id) && Objects.equals(luogoNascita, other.luogoNascita)
                && Objects.equals(nome, other.nome);
    }
}
