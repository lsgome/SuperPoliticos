package io.eduka.super.politicos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PoliticoAtributo.
 */
@Entity
@Table(name = "politico_atributo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "politicoatributo")
public class PoliticoAtributo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private Long valor;

    @ManyToOne
    private Politico politico;

    @ManyToOne
    private TipoAtributo tipoAtributo;

    @ManyToOne
    private LogAtualizacao logAtualizacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValor() {
        return valor;
    }

    public PoliticoAtributo valor(Long valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Politico getPolitico() {
        return politico;
    }

    public PoliticoAtributo politico(Politico politico) {
        this.politico = politico;
        return this;
    }

    public void setPolitico(Politico politico) {
        this.politico = politico;
    }

    public TipoAtributo getTipoAtributo() {
        return tipoAtributo;
    }

    public PoliticoAtributo tipoAtributo(TipoAtributo tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
        return this;
    }

    public void setTipoAtributo(TipoAtributo tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public LogAtualizacao getLogAtualizacao() {
        return logAtualizacao;
    }

    public PoliticoAtributo logAtualizacao(LogAtualizacao logAtualizacao) {
        this.logAtualizacao = logAtualizacao;
        return this;
    }

    public void setLogAtualizacao(LogAtualizacao logAtualizacao) {
        this.logAtualizacao = logAtualizacao;
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
        PoliticoAtributo politicoAtributo = (PoliticoAtributo) o;
        if (politicoAtributo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), politicoAtributo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoliticoAtributo{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            "}";
    }
}
