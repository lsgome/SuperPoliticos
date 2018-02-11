package io.eduka.super.politicos.repository;

import io.eduka.super.politicos.domain.LogAtualizacao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LogAtualizacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogAtualizacaoRepository extends JpaRepository<LogAtualizacao, Long> {

}
