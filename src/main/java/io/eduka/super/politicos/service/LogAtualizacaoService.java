package io.eduka.super.politicos.service;

import io.eduka.super.politicos.domain.LogAtualizacao;
import java.util.List;

/**
 * Service Interface for managing LogAtualizacao.
 */
public interface LogAtualizacaoService {

    /**
     * Save a logAtualizacao.
     *
     * @param logAtualizacao the entity to save
     * @return the persisted entity
     */
    LogAtualizacao save(LogAtualizacao logAtualizacao);

    /**
     * Get all the logAtualizacaos.
     *
     * @return the list of entities
     */
    List<LogAtualizacao> findAll();

    /**
     * Get the "id" logAtualizacao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LogAtualizacao findOne(Long id);

    /**
     * Delete the "id" logAtualizacao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the logAtualizacao corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<LogAtualizacao> search(String query);
}
