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

import io.eduka.super.politicos.domain.enumeration.TipoValorAtributo;

/**
 * A TipoAtributo.
 */
@Entity
@Table(name = "tipo_atributo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tipoatributo")
public class TipoAtributo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_valor_atributo", nullable = false)
    private TipoValorAtributo tipoValorAtributo;

    @OneToMany(mappedBy = "tipoAtributo")
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

    public TipoAtributo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public TipoAtributo tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public TipoValorAtributo getTipoValorAtributo() {
        return tipoValorAtributo;
    }

    public TipoAtributo tipoValorAtributo(TipoValorAtributo tipoValorAtributo) {
        this.tipoValorAtributo = tipoValorAtributo;
        return this;
    }

    public void setTipoValorAtributo(TipoValorAtributo tipoValorAtributo) {
        this.tipoValorAtributo = tipoValorAtributo;
    }

    public Set<PoliticoAtributo> getAtributos() {
        return atributos;
    }

    public TipoAtributo atributos(Set<PoliticoAtributo> politicoAtributos) {
        this.atributos = politicoAtributos;
        return this;
    }

    public TipoAtributo addAtributos(PoliticoAtributo politicoAtributo) {
        this.atributos.add(politicoAtributo);
        politicoAtributo.setTipoAtributo(this);
        return this;
    }

    public TipoAtributo removeAtributos(PoliticoAtributo politicoAtributo) {
        this.atributos.remove(politicoAtributo);
        politicoAtributo.setTipoAtributo(null);
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
        TipoAtributo tipoAtributo = (TipoAtributo) o;
        if (tipoAtributo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoAtributo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoAtributo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", tipoValorAtributo='" + getTipoValorAtributo() + "'" +
            "}";
    }
}
