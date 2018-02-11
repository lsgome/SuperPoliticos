package io.eduka.super.politicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eduka.super.politicos.domain.PoliticoAtributo;
import io.eduka.super.politicos.service.PoliticoAtributoService;
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
 * REST controller for managing PoliticoAtributo.
 */
@RestController
@RequestMapping("/api")
public class PoliticoAtributoResource {

    private final Logger log = LoggerFactory.getLogger(PoliticoAtributoResource.class);

    private static final String ENTITY_NAME = "politicoAtributo";

    private final PoliticoAtributoService politicoAtributoService;

    public PoliticoAtributoResource(PoliticoAtributoService politicoAtributoService) {
        this.politicoAtributoService = politicoAtributoService;
    }

    /**
     * POST  /politico-atributos : Create a new politicoAtributo.
     *
     * @param politicoAtributo the politicoAtributo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new politicoAtributo, or with status 400 (Bad Request) if the politicoAtributo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/politico-atributos")
    @Timed
    public ResponseEntity<PoliticoAtributo> createPoliticoAtributo(@RequestBody PoliticoAtributo politicoAtributo) throws URISyntaxException {
        log.debug("REST request to save PoliticoAtributo : {}", politicoAtributo);
        if (politicoAtributo.getId() != null) {
            throw new BadRequestAlertException("A new politicoAtributo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoliticoAtributo result = politicoAtributoService.save(politicoAtributo);
        return ResponseEntity.created(new URI("/api/politico-atributos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /politico-atributos : Updates an existing politicoAtributo.
     *
     * @param politicoAtributo the politicoAtributo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated politicoAtributo,
     * or with status 400 (Bad Request) if the politicoAtributo is not valid,
     * or with status 500 (Internal Server Error) if the politicoAtributo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/politico-atributos")
    @Timed
    public ResponseEntity<PoliticoAtributo> updatePoliticoAtributo(@RequestBody PoliticoAtributo politicoAtributo) throws URISyntaxException {
        log.debug("REST request to update PoliticoAtributo : {}", politicoAtributo);
        if (politicoAtributo.getId() == null) {
            return createPoliticoAtributo(politicoAtributo);
        }
        PoliticoAtributo result = politicoAtributoService.save(politicoAtributo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, politicoAtributo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /politico-atributos : get all the politicoAtributos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of politicoAtributos in body
     */
    @GetMapping("/politico-atributos")
    @Timed
    public List<PoliticoAtributo> getAllPoliticoAtributos() {
        log.debug("REST request to get all PoliticoAtributos");
        return politicoAtributoService.findAll();
        }

    /**
     * GET  /politico-atributos/:id : get the "id" politicoAtributo.
     *
     * @param id the id of the politicoAtributo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the politicoAtributo, or with status 404 (Not Found)
     */
    @GetMapping("/politico-atributos/{id}")
    @Timed
    public ResponseEntity<PoliticoAtributo> getPoliticoAtributo(@PathVariable Long id) {
        log.debug("REST request to get PoliticoAtributo : {}", id);
        PoliticoAtributo politicoAtributo = politicoAtributoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(politicoAtributo));
    }

    /**
     * DELETE  /politico-atributos/:id : delete the "id" politicoAtributo.
     *
     * @param id the id of the politicoAtributo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/politico-atributos/{id}")
    @Timed
    public ResponseEntity<Void> deletePoliticoAtributo(@PathVariable Long id) {
        log.debug("REST request to delete PoliticoAtributo : {}", id);
        politicoAtributoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/politico-atributos?query=:query : search for the politicoAtributo corresponding
     * to the query.
     *
     * @param query the query of the politicoAtributo search
     * @return the result of the search
     */
    @GetMapping("/_search/politico-atributos")
    @Timed
    public List<PoliticoAtributo> searchPoliticoAtributos(@RequestParam String query) {
        log.debug("REST request to search PoliticoAtributos for query {}", query);
        return politicoAtributoService.search(query);
    }

}
