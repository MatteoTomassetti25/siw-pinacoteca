package it.uniroma3.siw.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_generator")
    @SequenceGenerator(name = "area_generator", sequenceName = "area_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 2, max = 100, message = "Il nome deve essere compreso tra 2 e 100 caratteri")
    private String nome;

    @OneToOne
    @JoinColumn(name = "curatore_id", nullable = true, unique = true)
    @JsonIgnore // Evita loop di serializzazione JSON
    private Curatore curatore;

    // Metodo per mantenere la relazione bidirezionale
    public void setCuratore(Curatore curatore) {
        this.curatore = curatore;
        if (curatore != null && curatore.getArea() != this) {
            curatore.setArea(this);
        }
    }

    // Getters e Setters
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

    public Curatore getCuratore() {
        return curatore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(curatore, id, nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Area other = (Area) obj;
        return Objects.equals(curatore, other.curatore) && Objects.equals(id, other.id)
                && Objects.equals(nome, other.nome);
    }
}
