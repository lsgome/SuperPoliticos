package io.eduka.super.politicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eduka.super.politicos.domain.Politico;
import io.eduka.super.politicos.service.PoliticoService;
import io.eduka.super.politicos.web.rest.errors.BadRequestAlertException;
import io.eduka.super.politicos.web.rest.util.HeaderUtil;
import io.eduka.super.politicos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Politico.
 */
@RestController
@RequestMapping("/api")
public class PoliticoResource {

    private final Logger log = LoggerFactory.getLogger(PoliticoResource.class);

    private static final String ENTITY_NAME = "politico";

    private final PoliticoService politicoService;

    public PoliticoResource(PoliticoService politicoService) {
        this.politicoService = politicoService;
    }

    /**
     * POST  /politicos : Create a new politico.
     *
     * @param politico the politico to create
     * @return the ResponseEntity with status 201 (Created) and with body the new politico, or with status 400 (Bad Request) if the politico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/politicos")
    @Timed
    public ResponseEntity<Politico> createPolitico(@Valid @RequestBody Politico politico) throws URISyntaxException {
        log.debug("REST request to save Politico : {}", politico);
        if (politico.getId() != null) {
            throw new BadRequestAlertException("A new politico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Politico result = politicoService.save(politico);
        return ResponseEntity.created(new URI("/api/politicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /politicos : Updates an existing politico.
     *
     * @param politico the politico to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated politico,
     * or with status 400 (Bad Request) if the politico is not valid,
     * or with status 500 (Internal Server Error) if the politico couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/politicos")
    @Timed
    public ResponseEntity<Politico> updatePolitico(@Valid @RequestBody Politico politico) throws URISyntaxException {
        log.debug("REST request to update Politico : {}", politico);
        if (politico.getId() == null) {
            return createPolitico(politico);
        }
        Politico result = politicoService.save(politico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, politico.getId().toString()))
            .body(result);
    }

    /**
     * GET  /politicos : get all the politicos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of politicos in body
     */
    @GetMapping("/politicos")
    @Timed
    public ResponseEntity<List<Politico>> getAllPoliticos(Pageable pageable) {
        log.debug("REST request to get a page of Politicos");
        Page<Politico> page = politicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/politicos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /politicos/:id : get the "id" politico.
     *
     * @param id the id of the politico to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the politico, or with status 404 (Not Found)
     */
    @GetMapping("/politicos/{id}")
    @Timed
    public ResponseEntity<Politico> getPolitico(@PathVariable Long id) {
        log.debug("REST request to get Politico : {}", id);
        Politico politico = politicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(politico));
    }

    /**
     * DELETE  /politicos/:id : delete the "id" politico.
     *
     * @param id the id of the politico to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/politicos/{id}")
    @Timed
    public ResponseEntity<Void> deletePolitico(@PathVariable Long id) {
        log.debug("REST request to delete Politico : {}", id);
        politicoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/politicos?query=:query : search for the politico corresponding
     * to the query.
     *
     * @param query the query of the politico search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/politicos")
    @Timed
    public ResponseEntity<List<Politico>> searchPoliticos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Politicos for query {}", query);
        Page<Politico> page = politicoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/politicos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
