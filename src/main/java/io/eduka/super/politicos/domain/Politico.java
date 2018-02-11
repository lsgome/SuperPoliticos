package io.eduka.super.politicos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.eduka.super.politicos.domain.enumeration.TipoPolitico;

/**
 * A Politico.
 */
@Entity
@Table(name = "politico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "politico")
public class Politico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_politico", nullable = false)
    private TipoPolitico tipoPolitico;

    @OneToMany(mappedBy = "politico")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PoliticoAtributo> atributos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Politico nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoPolitico getTipoPolitico() {
        return tipoPolitico;
    }

    public Politico tipoPolitico(TipoPolitico tipoPolitico) {
        this.tipoPolitico = tipoPolitico;
        return this;
    }

    public void setTipoPolitico(TipoPolitico tipoPolitico) {
        this.tipoPolitico = tipoPolitico;
    }

    public Set<PoliticoAtributo> getAtributos() {
        return atributos;
    }

    public Politico atributos(Set<PoliticoAtributo> politicoAtributos) {
        this.atributos = politicoAtributos;
        return this;
    }

    public Politico addAtributos(PoliticoAtributo politicoAtributo) {
        this.atributos.add(politicoAtributo);
        politicoAtributo.setPolitico(this);
        return this;
    }

    public Politico removeAtributos(PoliticoAtributo politicoAtributo) {
        this.atributos.remove(politicoAtributo);
        politicoAtributo.setPolitico(null);
        return this;
    }

    public void setAtributos(Set<PoliticoAtributo> politicoAtributos) {
        this.atributos = politicoAtributos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Politico politico = (Politico) o;
        if (politico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), politico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Politico{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipoPolitico='" + getTipoPolitico() + "'" +
            "}";
    }
}
