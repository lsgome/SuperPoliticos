package io.eduka.super.politicos.web.rest;

import io.eduka.super.politicos.SuperPoliticosApp;

import io.eduka.super.politicos.domain.Politico;
import io.eduka.super.politicos.repository.PoliticoRepository;
import io.eduka.super.politicos.service.PoliticoService;
import io.eduka.super.politicos.repository.search.PoliticoSearchRepository;
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

import io.eduka.super.politicos.domain.enumeration.TipoPolitico;
/**
 * Test class for the PoliticoResource REST controller.
 *
 * @see PoliticoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperPoliticosApp.class)
public class PoliticoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final TipoPolitico DEFAULT_TIPO_POLITICO = TipoPolitico.VEREADORES;
    private static final TipoPolitico UPDATED_TIPO_POLITICO = TipoPolitico.DEPUTADOS_ESTADUAIS;

    @Autowired
    private PoliticoRepository politicoRepository;

    @Autowired
    private PoliticoService politicoService;

    @Autowired
    private PoliticoSearchRepository politicoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPoliticoMockMvc;

    private Politico politico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoliticoResource politicoResource = new PoliticoResource(politicoService);
        this.restPoliticoMockMvc = MockMvcBuilders.standaloneSetup(politicoResource)
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
    public static Politico createEntity(EntityManager em) {
        Politico politico = new Politico()
            .nome(DEFAULT_NOME)
            .tipoPolitico(DEFAULT_TIPO_POLITICO);
        return politico;
    }

    @Before
    public void initTest() {
        politicoSearchRepository.deleteAll();
        politico = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolitico() throws Exception {
        int databaseSizeBeforeCreate = politicoRepository.findAll().size();

        // Create the Politico
        restPoliticoMockMvc.perform(post("/api/politicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(politico)))
            .andExpect(status().isCreated());

        // Validate the Politico in the database
        List<Politico> politicoList = politicoRepository.findAll();
        assertThat(politicoList).hasSize(databaseSizeBeforeCreate + 1);
        Politico testPolitico = politicoList.get(politicoList.size() - 1);
        assertThat(testPolitico.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPolitico.getTipoPolitico()).isEqualTo(DEFAULT_TIPO_POLITICO);

        // Validate the Politico in Elasticsearch
        Politico politicoEs = politicoSearchRepository.findOne(testPolitico.getId());
        assertThat(politicoEs).isEqualToIgnoringGivenFields(testPolitico);
    }

    @Test
    @Transactional
    public void createPoliticoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = politicoRepository.findAll().size();

        // Create the Politico with an existing ID
        politico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoliticoMockMvc.perform(post("/api/politicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(politico)))
            .andExpect(status().isBadRequest());

        // Validate the Politico in the database
        List<Politico> politicoList = politicoRepository.findAll();
        assertThat(politicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = politicoRepository.findAll().size();
        // set the field null
        politico.setNome(null);

        // Create the Politico, which fails.

        restPoliticoMockMvc.perform(post("/api/politicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(politico)))
            .andExpect(status().isBadRequest());

        List<Politico> politicoList = politicoRepository.findAll();
        assertThat(politicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoPoliticoIsRequired() throws Exception {
        int databaseSizeBeforeTest = politicoRepository.findAll().size();
        // set the field null
        politico.setTipoPolitico(null);

        // Create the Politico, which fails.

        restPoliticoMockMvc.perform(post("/api/politicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(politico)))
            .andExpect(status().isBadRequest());

        List<Politico> politicoList = politicoRepository.findAll();
        assertThat(politicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPoliticos() throws Exception {
        // Initialize the database
        politicoRepository.saveAndFlush(politico);

        // Get all the politicoList
        restPoliticoMockMvc.perform(get("/api/politicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(politico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipoPolitico").value(hasItem(DEFAULT_TIPO_POLITICO.toString())));
    }

    @Test
    @Transactional
    public void getPolitico() throws Exception {
        // Initialize the database
        politicoRepository.saveAndFlush(politico);

        // Get the politico
        restPoliticoMockMvc.perform(get("/api/politicos/{id}", politico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(politico.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.tipoPolitico").value(DEFAULT_TIPO_POLITICO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolitico() throws Exception {
        // Get the politico
        restPoliticoMockMvc.perform(get("/api/politicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolitico() throws Exception {
        // Initialize the database
        politicoService.save(politico);

        int databaseSizeBeforeUpdate = politicoRepository.findAll().size();

        // Update the politico
        Politico updatedPolitico = politicoRepository.findOne(politico.getId());
        // Disconnect from session so that the updates on updatedPolitico are not directly saved in db
        em.detach(updatedPolitico);
        updatedPolitico
            .nome(UPDATED_NOME)
            .tipoPolitico(UPDATED_TIPO_POLITICO);

        restPoliticoMockMvc.perform(put("/api/politicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolitico)))
            .andExpect(status().isOk());

        // Validate the Politico in the database
        List<Politico> politicoList = politicoRepository.findAll();
        assertThat(politicoList).hasSize(databaseSizeBeforeUpdate);
        Politico testPolitico = politicoList.get(politicoList.size() - 1);
        assertThat(testPolitico.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPolitico.getTipoPolitico()).isEqualTo(UPDATED_TIPO_POLITICO);

        // Validate the Politico in Elasticsearch
        Politico politicoEs = politicoSearchRepository.findOne(testPolitico.getId());
        assertThat(politicoEs).isEqualToIgnoringGivenFields(testPolitico);
    }

    @Test
    @Transactional
    public void updateNonExistingPolitico() throws Exception {
        int databaseSizeBeforeUpdate = politicoRepository.findAll().size();

        // Create the Politico

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPoliticoMockMvc.perform(put("/api/politicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(politico)))
            .andExpect(status().isCreated());

        // Validate the Politico in the database
        List<Politico> politicoList = politicoRepository.findAll();
        assertThat(politicoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePolitico() throws Exception {
        // Initialize the database
        politicoService.save(politico);

        int databaseSizeBeforeDelete = politicoRepository.findAll().size();

        // Get the politico
        restPoliticoMockMvc.perform(delete("/api/politicos/{id}", politico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean politicoExistsInEs = politicoSearchRepository.exists(politico.getId());
        assertThat(politicoExistsInEs).isFalse();

        // Validate the database is empty
        List<Politico> politicoList = politicoRepository.findAll();
        assertThat(politicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPolitico() throws Exception {
        // Initialize the database
        politicoService.save(politico);

        // Search the politico
        restPoliticoMockMvc.perform(get("/api/_search/politicos?query=id:" + politico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(politico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipoPolitico").value(hasItem(DEFAULT_TIPO_POLITICO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Politico.class);
        Politico politico1 = new Politico();
        politico1.setId(1L);
        Politico politico2 = new Politico();
        politico2.setId(politico1.getId());
        assertThat(politico1).isEqualTo(politico2);
        politico2.setId(2L);
        assertThat(politico1).isNotEqualTo(politico2);
        politico1.setId(null);
        assertThat(politico1).isNotEqualTo(politico2);
    }
}
