package io.eduka.super.politicos.service.impl;

import io.eduka.super.politicos.service.PoliticoService;
import io.eduka.super.politicos.domain.Politico;
import io.eduka.super.politicos.repository.PoliticoRepository;
import io.eduka.super.politicos.repository.search.PoliticoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Politico.
 */
@Service
@Transactional
public class PoliticoServiceImpl implements PoliticoService {

    private final Logger log = LoggerFactory.getLogger(PoliticoServiceImpl.class);

    private final PoliticoRepository politicoRepository;

    private final PoliticoSearchRepository politicoSearchRepository;

    public PoliticoServiceImpl(PoliticoRepository politicoRepository, PoliticoSearchRepository politicoSearchRepository) {
        this.politicoRepository = politicoRepository;
        this.politicoSearchRepository = politicoSearchRepository;
    }

    /**
     * Save a politico.
     *
     * @param politico the entity to save
     * @return the persisted entity
     */
    @Override
    public Politico save(Politico politico) {
        log.debug("Request to save Politico : {}", politico);
        Politico result = politicoRepository.save(politico);
        politicoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the politicos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Politico> findAll(Pageable pageable) {
        log.debug("Request to get all Politicos");
        return politicoRepository.findAll(pageable);
    }

    /**
     * Get one politico by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Politico findOne(Long id) {
        log.debug("Request to get Politico : {}", id);
        return politicoRepository.findOne(id);
    }

    /**
     * Delete the politico by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Politico : {}", id);
        politicoRepository.delete(id);
        politicoSearchRepository.delete(id);
    }

    /**
     * Search for the politico corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Politico> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Politicos for query {}", query);
        Page<Politico> result = politicoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
