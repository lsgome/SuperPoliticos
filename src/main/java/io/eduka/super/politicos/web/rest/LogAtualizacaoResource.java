package io.eduka.super.politicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eduka.super.politicos.domain.LogAtualizacao;
import io.eduka.super.politicos.service.LogAtualizacaoService;
import io.eduka.super.politicos.web.rest.errors.BadRequestAlertException;
import io.eduka.super.politicos.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing LogAtualizacao.
 */
@RestController
@RequestMapping("/api")
public class LogAtualizacaoResource {

    private final Logger log = LoggerFactory.getLogger(LogAtualizacaoResource.class);

    private static final String ENTITY_NAME = "logAtualizacao";

    private final LogAtualizacaoService logAtualizacaoService;

    public LogAtualizacaoResource(LogAtualizacaoService logAtualizacaoService) {
        this.logAtualizacaoService = logAtualizacaoService;
    }

    /**
     * POST  /log-atualizacaos : Create a new logAtualizacao.
     *
     * @param logAtualizacao the logAtualizacao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logAtualizacao, or with status 400 (Bad Request) if the logAtualizacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/log-atualizacaos")
    @Timed
    public ResponseEntity<LogAtualizacao> createLogAtualizacao(@RequestBody LogAtualizacao logAtualizacao) throws URISyntaxException {
        log.debug("REST request to save LogAtualizacao : {}", logAtualizacao);
        if (logAtualizacao.getId() != null) {
            throw new BadRequestAlertException("A new logAtualizacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogAtualizacao result = logAtualizacaoService.save(logAtualizacao);
        return ResponseEntity.created(new URI("/api/log-atualizacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-atualizacaos : Updates an existing logAtualizacao.
     *
     * @param logAtualizacao the logAtualizacao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logAtualizacao,
     * or with status 400 (Bad Request) if the logAtualizacao is not valid,
     * or with status 500 (Internal Server Error) if the logAtualizacao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/log-atualizacaos")
    @Timed
    public ResponseEntity<LogAtualizacao> updateLogAtualizacao(@RequestBody LogAtualizacao logAtualizacao) throws URISyntaxException {
        log.debug("REST request to update LogAtualizacao : {}", logAtualizacao);
        if (logAtualizacao.getId() == null) {
            return createLogAtualizacao(logAtualizacao);
        }
        LogAtualizacao result = logAtualizacaoService.save(logAtualizacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logAtualizacao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /log-atualizacaos : get all the logAtualizacaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of logAtualizacaos in body
     */
    @GetMapping("/log-atualizacaos")
    @Timed
    public List<LogAtualizacao> getAllLogAtualizacaos() {
        log.debug("REST request to get all LogAtualizacaos");
        return logAtualizacaoService.findAll();
        }

    /**
     * GET  /log-atualizacaos/:id : get the "id" logAtualizacao.
     *
     * @param id the id of the logAtualizacao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logAtualizacao, or with status 404 (Not Found)
     */
    @GetMapping("/log-atualizacaos/{id}")
    @Timed
    public ResponseEntity<LogAtualizacao> getLogAtualizacao(@PathVariable Long id) {
        log.debug("REST request to get LogAtualizacao : {}", id);
        LogAtualizacao logAtualizacao = logAtualizacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logAtualizacao));
    }

    /**
     * DELETE  /log-atualizacaos/:id : delete the "id" logAtualizacao.
     *
     * @param id the id of the logAtualizacao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/log-atualizacaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogAtualizacao(@PathVariable Long id) {
        log.debug("REST request to delete LogAtualizacao : {}", id);
        logAtualizacaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/log-atualizacaos?query=:query : search for the logAtualizacao corresponding
     * to the query.
     *
     * @param query the query of the logAtualizacao search
     * @return the result of the search
     */
    @GetMapping("/_search/log-atualizacaos")
    @Timed
    public List<LogAtualizacao> searchLogAtualizacaos(@RequestParam String query) {
        log.debug("REST request to search LogAtualizacaos for query {}", query);
        return logAtualizacaoService.search(query);
    }

}
