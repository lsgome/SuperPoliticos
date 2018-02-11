package io.eduka.super.politicos.service;

import io.eduka.super.politicos.domain.PoliticoAtributo;
import java.util.List;

/**
 * Service Interface for managing PoliticoAtributo.
 */
public interface PoliticoAtributoService {

    /**
     * Save a politicoAtributo.
     *
     * @param politicoAtributo the entity to save
     * @return the persisted entity
     */
    PoliticoAtributo save(PoliticoAtributo politicoAtributo);

    /**
     * Get all the politicoAtributos.
     *
     * @return the list of entities
     */
    List<PoliticoAtributo> findAll();

    /**
     * Get the "id" politicoAtributo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PoliticoAtributo findOne(Long id);

    /**
     * Delete the "id" politicoAtributo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the politicoAtributo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PoliticoAtributo> search(String query);
}
