package io.eduka.super.politicos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LogAtualizacao.
 */
@Entity
@Table(name = "log_atualizacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "logatualizacao")
public class LogAtualizacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data")
    private Instant data;

    @OneToMany(mappedBy = "logAtualizacao")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PoliticoAtributo> logReferencias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LogAtualizacao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getData() {
        return data;
    }

    public LogAtualizacao data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Set<PoliticoAtributo> getLogReferencias() {
        return logReferencias;
    }

    public LogAtualizacao logReferencias(Set<PoliticoAtributo> politicoAtributos) {
        this.logReferencias = politicoAtributos;
        return this;
    }

    public LogAtualizacao addLogReferencia(PoliticoAtributo politicoAtributo) {
        this.logReferencias.add(politicoAtributo);
        politicoAtributo.setLogAtualizacao(this);
        return this;
    }

    public LogAtualizacao removeLogReferencia(PoliticoAtributo politicoAtributo) {
        this.logReferencias.remove(politicoAtributo);
        politicoAtributo.setLogAtualizacao(null);
        return this;
    }

    public void setLogReferencias(Set<PoliticoAtributo> politicoAtributos) {
        this.logReferencias = politicoAtributos;
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
        LogAtualizacao logAtualizacao = (LogAtualizacao) o;
        if (logAtualizacao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logAtualizacao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogAtualizacao{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
