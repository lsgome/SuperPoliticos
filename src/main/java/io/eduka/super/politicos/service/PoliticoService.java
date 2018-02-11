package io.eduka.super.politicos.service;

import io.eduka.super.politicos.domain.Politico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Politico.
 */
public interface PoliticoService {

    /**
     * Save a politico.
     *
     * @param politico the entity to save
     * @return the persisted entity
     */
    Politico save(Politico politico);

    /**
     * Get all the politicos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Politico> findAll(Pageable pageable);

    /**
     * Get the "id" politico.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Politico findOne(Long id);

    /**
     * Delete the "id" politico.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the politico corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Politico> search(String query, Pageable pageable);
}
