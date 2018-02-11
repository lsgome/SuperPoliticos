package io.eduka.super.politicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eduka.super.politicos.domain.TipoAtributo;
import io.eduka.super.politicos.service.TipoAtributoService;
import io.eduka.super.politicos.web.rest.errors.BadRequestAlertException;
import io.eduka.super.politicos.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing TipoAtributo.
 */
@RestController
@RequestMapping("/api")
public class TipoAtributoResource {

    private final Logger log = LoggerFactory.getLogger(TipoAtributoResource.class);

    private static final String ENTITY_NAME = "tipoAtributo";

    private final TipoAtributoService tipoAtributoService;

    public TipoAtributoResource(TipoAtributoService tipoAtributoService) {
        this.tipoAtributoService = tipoAtributoService;
    }

    /**
     * POST  /tipo-atributos : Create a new tipoAtributo.
     *
     * @param tipoAtributo the tipoAtributo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoAtributo, or with status 400 (Bad Request) if the tipoAtributo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-atributos")
    @Timed
    public ResponseEntity<TipoAtributo> createTipoAtributo(@Valid @RequestBody TipoAtributo tipoAtributo) throws URISyntaxException {
        log.debug("REST request to save TipoAtributo : {}", tipoAtributo);
        if (tipoAtributo.getId() != null) {
            throw new BadRequestAlertException("A new tipoAtributo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoAtributo result = tipoAtributoService.save(tipoAtributo);
        return ResponseEntity.created(new URI("/api/tipo-atributos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-atributos : Updates an existing tipoAtributo.
     *
     * @param tipoAtributo the tipoAtributo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoAtributo,
     * or with status 400 (Bad Request) if the tipoAtributo is not valid,
     * or with status 500 (Internal Server Error) if the tipoAtributo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-atributos")
    @Timed
    public ResponseEntity<TipoAtributo> updateTipoAtributo(@Valid @RequestBody TipoAtributo tipoAtributo) throws URISyntaxException {
        log.debug("REST request to update TipoAtributo : {}", tipoAtributo);
        if (tipoAtributo.getId() == null) {
            return createTipoAtributo(tipoAtributo);
        }
        TipoAtributo result = tipoAtributoService.save(tipoAtributo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoAtributo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-atributos : get all the tipoAtributos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoAtributos in body
     */
    @GetMapping("/tipo-atributos")
    @Timed
    public List<TipoAtributo> getAllTipoAtributos() {
        log.debug("REST request to get all TipoAtributos");
        return tipoAtributoService.findAll();
        }

    /**
     * GET  /tipo-atributos/:id : get the "id" tipoAtributo.
     *
     * @param id the id of the tipoAtributo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoAtributo, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-atributos/{id}")
    @Timed
    public ResponseEntity<TipoAtributo> getTipoAtributo(@PathVariable Long id) {
        log.debug("REST request to get TipoAtributo : {}", id);
        TipoAtributo tipoAtributo = tipoAtributoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoAtributo));
    }

    /**
     * DELETE  /tipo-atributos/:id : delete the "id" tipoAtributo.
     *
     * @param id the id of the tipoAtributo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-atributos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoAtributo(@PathVariable Long id) {
        log.debug("REST request to delete TipoAtributo : {}", id);
        tipoAtributoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-atributos?query=:query : search for the tipoAtributo corresponding
     * to the query.
     *
     * @param query the query of the tipoAtributo search
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-atributos")
    @Timed
    public List<TipoAtributo> searchTipoAtributos(@RequestParam String query) {
        log.debug("REST request to search TipoAtributos for query {}", query);
        return tipoAtributoService.search(query);
    }

}
