package io.eduka.super.politicos.service.impl;

import io.eduka.super.politicos.service.PoliticoAtributoService;
import io.eduka.super.politicos.domain.PoliticoAtributo;
import io.eduka.super.politicos.repository.PoliticoAtributoRepository;
import io.eduka.super.politicos.repository.search.PoliticoAtributoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PoliticoAtributo.
 */
@Service
@Transactional
public class PoliticoAtributoServiceImpl implements PoliticoAtributoService {

    private final Logger log = LoggerFactory.getLogger(PoliticoAtributoServiceImpl.class);

    private final PoliticoAtributoRepository politicoAtributoRepository;

    private final PoliticoAtributoSearchRepository politicoAtributoSearchRepository;

    public PoliticoAtributoServiceImpl(PoliticoAtributoRepository politicoAtributoRepository, PoliticoAtributoSearchRepository politicoAtributoSearchRepository) {
        this.politicoAtributoRepository = politicoAtributoRepository;
        this.politicoAtributoSearchRepository = politicoAtributoSearchRepository;
    }

    /**
     * Save a politicoAtributo.
     *
     * @param politicoAtributo the entity to save
     * @return the persisted entity
     */
    @Override
    public PoliticoAtributo save(PoliticoAtributo politicoAtributo) {
        log.debug("Request to save PoliticoAtributo : {}", politicoAtributo);
        PoliticoAtributo result = politicoAtributoRepository.save(politicoAtributo);
        politicoAtributoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the politicoAtributos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PoliticoAtributo> findAll() {
        log.debug("Request to get all PoliticoAtributos");
        return politicoAtributoRepository.findAll();
    }

    /**
     * Get one politicoAtributo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PoliticoAtributo findOne(Long id) {
        log.debug("Request to get PoliticoAtributo : {}", id);
        return politicoAtributoRepository.findOne(id);
    }

    /**
     * Delete the politicoAtributo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PoliticoAtributo : {}", id);
        politicoAtributoRepository.delete(id);
        politicoAtributoSearchRepository.delete(id);
    }

    /**
     * Search for the politicoAtributo corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PoliticoAtributo> search(String query) {
        log.debug("Request to search PoliticoAtributos for query {}", query);
        return StreamSupport
            .stream(politicoAtributoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
