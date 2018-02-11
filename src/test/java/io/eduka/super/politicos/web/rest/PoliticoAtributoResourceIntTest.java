package io.eduka.super.politicos.web.rest;

import io.eduka.super.politicos.SuperPoliticosApp;

import io.eduka.super.politicos.domain.PoliticoAtributo;
import io.eduka.super.politicos.repository.PoliticoAtributoRepository;
import io.eduka.super.politicos.service.PoliticoAtributoService;
import io.eduka.super.politicos.repository.search.PoliticoAtributoSearchRepository;
import io.eduka.super.politicos.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.eduka.super.politicos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PoliticoAtributoResource REST controller.
 *
 * @see PoliticoAtributoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperPoliticosApp.class)
public class PoliticoAtributoResourceIntTest {

    private static final Long DEFAULT_VALOR = 1L;
    private static final Long UPDATED_VALOR = 2L;

    @Autowired
    private PoliticoAtributoRepository politicoAtributoRepository;

    @Autowired
    private PoliticoAtributoService politicoAtributoService;

    @Autowired
    private PoliticoAtributoSearchRepository politicoAtributoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPoliticoAtributoMockMvc;

    private PoliticoAtributo politicoAtributo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoliticoAtributoResource politicoAtributoResource = new PoliticoAtributoResource(politicoAtributoService);
        this.restPoliticoAtributoMockMvc = MockMvcBuilders.standaloneSetup(politicoAtributoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoliticoAtributo createEntity(EntityManager em) {
        PoliticoAtributo politicoAtributo = new PoliticoAtributo()
            .valor(DEFAULT_VALOR);
        return politicoAtributo;
    }

    @Before
    public void initTest() {
        politicoAtributoSearchRepository.deleteAll();
        politicoAtributo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoliticoAtributo() throws Exception {
        int databaseSizeBeforeCreate = politicoAtributoRepository.findAll().size();

        // Create the PoliticoAtributo
        restPoliticoAtributoMockMvc.perform(post("/api/politico-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(politicoAtributo)))
            .andExpect(status().isCreated());

        // Validate the PoliticoAtributo in the database
        List<PoliticoAtributo> politicoAtributoList = politicoAtributoRepository.findAll();
        assertThat(politicoAtributoList).hasSize(databaseSizeBeforeCreate + 1);
        PoliticoAtributo testPoliticoAtributo = politicoAtributoList.get(politicoAtributoList.size() - 1);
        assertThat(testPoliticoAtributo.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the PoliticoAtributo in Elasticsearch
        PoliticoAtributo politicoAtributoEs = politicoAtributoSearchRepository.findOne(testPoliticoAtributo.getId());
        assertThat(politicoAtributoEs).isEqualToIgnoringGivenFields(testPoliticoAtributo);
    }

    @Test
    @Transactional
    public void createPoliticoAtributoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = politicoAtributoRepository.findAll().size();

        // Create the PoliticoAtributo with an existing ID
        politicoAtributo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoliticoAtributoMockMvc.perform(post("/api/politico-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(politicoAtributo)))
            .andExpect(status().isBadRequest());

        // Validate the PoliticoAtributo in the database
        List<PoliticoAtributo> politicoAtributoList = politicoAtributoRepository.findAll();
        assertThat(politicoAtributoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPoliticoAtributos() throws Exception {
        // Initialize the database
        politicoAtributoRepository.saveAndFlush(politicoAtributo);

        // Get all the politicoAtributoList
        restPoliticoAtributoMockMvc.perform(get("/api/politico-atributos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(politicoAtributo.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }

    @Test
    @Transactional
    public void getPoliticoAtributo() throws Exception {
        // Initialize the database
        politicoAtributoRepository.saveAndFlush(politicoAtributo);

        // Get the politicoAtributo
        restPoliticoAtributoMockMvc.perform(get("/api/politico-atributos/{id}", politicoAtributo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(politicoAtributo.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPoliticoAtributo() throws Exception {
        // Get the politicoAtributo
        restPoliticoAtributoMockMvc.perform(get("/api/politico-atributos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoliticoAtributo() throws Exception {
        // Initialize the database
        politicoAtributoService.save(politicoAtributo);

        int databaseSizeBeforeUpdate = politicoAtributoRepository.findAll().size();

        // Update the politicoAtributo
        PoliticoAtributo updatedPoliticoAtributo = politicoAtributoRepository.findOne(politicoAtributo.getId());
        // Disconnect from session so that the updates on updatedPoliticoAtributo are not directly saved in db
        em.detach(updatedPoliticoAtributo);
        updatedPoliticoAtributo
            .valor(UPDATED_VALOR);

        restPoliticoAtributoMockMvc.perform(put("/api/politico-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoliticoAtributo)))
            .andExpect(status().isOk());

        // Validate the PoliticoAtributo in the database
        List<PoliticoAtributo> politicoAtributoList = politicoAtributoRepository.findAll();
        assertThat(politicoAtributoList).hasSize(databaseSizeBeforeUpdate);
        PoliticoAtributo testPoliticoAtributo = politicoAtributoList.get(politicoAtributoList.size() - 1);
        assertThat(testPoliticoAtributo.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the PoliticoAtributo in Elasticsearch
        PoliticoAtributo politicoAtributoEs = politicoAtributoSearchRepository.findOne(testPoliticoAtributo.getId());
        assertThat(politicoAtributoEs).isEqualToIgnoringGivenFields(testPoliticoAtributo);
    }

    @Test
    @Transactional
    public void updateNonExistingPoliticoAtributo() throws Exception {
        int databaseSizeBeforeUpdate = politicoAtributoRepository.findAll().size();

        // Create the PoliticoAtributo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPoliticoAtributoMockMvc.perform(put("/api/politico-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(politicoAtributo)))
            .andExpect(status().isCreated());

        // Validate the PoliticoAtributo in the database
        List<PoliticoAtributo> politicoAtributoList = politicoAtributoRepository.findAll();
        assertThat(politicoAtributoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePoliticoAtributo() throws Exception {
        // Initialize the database
        politicoAtributoService.save(politicoAtributo);

        int databaseSizeBeforeDelete = politicoAtributoRepository.findAll().size();

        // Get the politicoAtributo
        restPoliticoAtributoMockMvc.perform(delete("/api/politico-atributos/{id}", politicoAtributo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean politicoAtributoExistsInEs = politicoAtributoSearchRepository.exists(politicoAtributo.getId());
        assertThat(politicoAtributoExistsInEs).isFalse();

        // Validate the database is empty
        List<PoliticoAtributo> politicoAtributoList = politicoAtributoRepository.findAll();
        assertThat(politicoAtributoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPoliticoAtributo() throws Exception {
        // Initialize the database
        politicoAtributoService.save(politicoAtributo);

        // Search the politicoAtributo
        restPoliticoAtributoMockMvc.perform(get("/api/_search/politico-atributos?query=id:" + politicoAtributo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(politicoAtributo.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoliticoAtributo.class);
        PoliticoAtributo politicoAtributo1 = new PoliticoAtributo();
        politicoAtributo1.setId(1L);
        PoliticoAtributo politicoAtributo2 = new PoliticoAtributo();
        politicoAtributo2.setId(politicoAtributo1.getId());
        assertThat(politicoAtributo1).isEqualTo(politicoAtributo2);
        politicoAtributo2.setId(2L);
        assertThat(politicoAtributo1).isNotEqualTo(politicoAtributo2);
        politicoAtributo1.setId(null);
        assertThat(politicoAtributo1).isNotEqualTo(politicoAtributo2);
    }
}
