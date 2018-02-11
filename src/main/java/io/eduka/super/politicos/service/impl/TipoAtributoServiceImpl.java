package io.eduka.super.politicos.service.impl;

import io.eduka.super.politicos.service.TipoAtributoService;
import io.eduka.super.politicos.domain.TipoAtributo;
import io.eduka.super.politicos.repository.TipoAtributoRepository;
import io.eduka.super.politicos.repository.search.TipoAtributoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TipoAtributo.
 */
@Service
@Transactional
public class TipoAtributoServiceImpl implements TipoAtributoService {

    private final Logger log = LoggerFactory.getLogger(TipoAtributoServiceImpl.class);

    private final TipoAtributoRepository tipoAtributoRepository;

    private final TipoAtributoSearchRepository tipoAtributoSearchRepository;

    public TipoAtributoServiceImpl(TipoAtributoRepository tipoAtributoRepository, TipoAtributoSearchRepository tipoAtributoSearchRepository) {
        this.tipoAtributoRepository = tipoAtributoRepository;
        this.tipoAtributoSearchRepository = tipoAtributoSearchRepository;
    }

    /**
     * Save a tipoAtributo.
     *
     * @param tipoAtributo the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoAtributo save(TipoAtributo tipoAtributo) {
        log.debug("Request to save TipoAtributo : {}", tipoAtributo);
        TipoAtributo result = tipoAtributoRepository.save(tipoAtributo);
        tipoAtributoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tipoAtributos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoAtributo> findAll() {
        log.debug("Request to get all TipoAtributos");
        return tipoAtributoRepository.findAll();
    }

    /**
     * Get one tipoAtributo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoAtributo findOne(Long id) {
        log.debug("Request to get TipoAtributo : {}", id);
        return tipoAtributoRepository.findOne(id);
    }

    /**
     * Delete the tipoAtributo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoAtributo : {}", id);
        tipoAtributoRepository.delete(id);
        tipoAtributoSearchRepository.delete(id);
    }

    /**
     * Search for the tipoAtributo corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoAtributo> search(String query) {
        log.debug("Request to search TipoAtributos for query {}", query);
        return StreamSupport
            .stream(tipoAtributoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
