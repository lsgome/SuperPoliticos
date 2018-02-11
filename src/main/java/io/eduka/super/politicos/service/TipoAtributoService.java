package io.eduka.super.politicos.service;

import io.eduka.super.politicos.domain.TipoAtributo;
import java.util.List;

/**
 * Service Interface for managing TipoAtributo.
 */
public interface TipoAtributoService {

    /**
     * Save a tipoAtributo.
     *
     * @param tipoAtributo the entity to save
     * @return the persisted entity
     */
    TipoAtributo save(TipoAtributo tipoAtributo);

    /**
     * Get all the tipoAtributos.
     *
     * @return the list of entities
     */
    List<TipoAtributo> findAll();

    /**
     * Get the "id" tipoAtributo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TipoAtributo findOne(Long id);

    /**
     * Delete the "id" tipoAtributo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoAtributo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<TipoAtributo> search(String query);
}
