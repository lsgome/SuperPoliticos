package io.eduka.super.politicos.service.impl;

import io.eduka.super.politicos.service.LogAtualizacaoService;
import io.eduka.super.politicos.domain.LogAtualizacao;
import io.eduka.super.politicos.repository.LogAtualizacaoRepository;
import io.eduka.super.politicos.repository.search.LogAtualizacaoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing LogAtualizacao.
 */
@Service
@Transactional
public class LogAtualizacaoServiceImpl implements LogAtualizacaoService {

    private final Logger log = LoggerFactory.getLogger(LogAtualizacaoServiceImpl.class);

    private final LogAtualizacaoRepository logAtualizacaoRepository;

    private final LogAtualizacaoSearchRepository logAtualizacaoSearchRepository;

    public LogAtualizacaoServiceImpl(LogAtualizacaoRepository logAtualizacaoRepository, LogAtualizacaoSearchRepository logAtualizacaoSearchRepository) {
        this.logAtualizacaoRepository = logAtualizacaoRepository;
        this.logAtualizacaoSearchRepository = logAtualizacaoSearchRepository;
    }

    /**
     * Save a logAtualizacao.
     *
     * @param logAtualizacao the entity to save
     * @return the persisted entity
     */
    @Override
    public LogAtualizacao save(LogAtualizacao logAtualizacao) {
        log.debug("Request to save LogAtualizacao : {}", logAtualizacao);
        LogAtualizacao result = logAtualizacaoRepository.save(logAtualizacao);
        logAtualizacaoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the logAtualizacaos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LogAtualizacao> findAll() {
        log.debug("Request to get all LogAtualizacaos");
        return logAtualizacaoRepository.findAll();
    }

    /**
     * Get one logAtualizacao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LogAtualizacao findOne(Long id) {
        log.debug("Request to get LogAtualizacao : {}", id);
        return logAtualizacaoRepository.findOne(id);
    }

    /**
     * Delete the logAtualizacao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogAtualizacao : {}", id);
        logAtualizacaoRepository.delete(id);
        logAtualizacaoSearchRepository.delete(id);
    }

    /**
     * Search for the logAtualizacao corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LogAtualizacao> search(String query) {
        log.debug("Request to search LogAtualizacaos for query {}", query);
        return StreamSupport
            .stream(logAtualizacaoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
