package io.eduka.super.politicos.web.rest;

import io.eduka.super.politicos.SuperPoliticosApp;

import io.eduka.super.politicos.domain.TipoAtributo;
import io.eduka.super.politicos.repository.TipoAtributoRepository;
import io.eduka.super.politicos.service.TipoAtributoService;
import io.eduka.super.politicos.repository.search.TipoAtributoSearchRepository;
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

import io.eduka.super.politicos.domain.enumeration.TipoValorAtributo;
/**
 * Test class for the TipoAtributoResource REST controller.
 *
 * @see TipoAtributoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperPoliticosApp.class)
public class TipoAtributoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final TipoValorAtributo DEFAULT_TIPO_VALOR_ATRIBUTO = TipoValorAtributo.INTEIRO_CRESCENTE;
    private static final TipoValorAtributo UPDATED_TIPO_VALOR_ATRIBUTO = TipoValorAtributo.INTEIRO_DECRESCENTE;

    @Autowired
    private TipoAtributoRepository tipoAtributoRepository;

    @Autowired
    private TipoAtributoService tipoAtributoService;

    @Autowired
    private TipoAtributoSearchRepository tipoAtributoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoAtributoMockMvc;

    private TipoAtributo tipoAtributo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoAtributoResource tipoAtributoResource = new TipoAtributoResource(tipoAtributoService);
        this.restTipoAtributoMockMvc = MockMvcBuilders.standaloneSetup(tipoAtributoResource)
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
    public static TipoAtributo createEntity(EntityManager em) {
        TipoAtributo tipoAtributo = new TipoAtributo()
            .nome(DEFAULT_NOME)
            .tipo(DEFAULT_TIPO)
            .tipoValorAtributo(DEFAULT_TIPO_VALOR_ATRIBUTO);
        return tipoAtributo;
    }

    @Before
    public void initTest() {
        tipoAtributoSearchRepository.deleteAll();
        tipoAtributo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoAtributo() throws Exception {
        int databaseSizeBeforeCreate = tipoAtributoRepository.findAll().size();

        // Create the TipoAtributo
        restTipoAtributoMockMvc.perform(post("/api/tipo-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAtributo)))
            .andExpect(status().isCreated());

        // Validate the TipoAtributo in the database
        List<TipoAtributo> tipoAtributoList = tipoAtributoRepository.findAll();
        assertThat(tipoAtributoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoAtributo testTipoAtributo = tipoAtributoList.get(tipoAtributoList.size() - 1);
        assertThat(testTipoAtributo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoAtributo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTipoAtributo.getTipoValorAtributo()).isEqualTo(DEFAULT_TIPO_VALOR_ATRIBUTO);

        // Validate the TipoAtributo in Elasticsearch
        TipoAtributo tipoAtributoEs = tipoAtributoSearchRepository.findOne(testTipoAtributo.getId());
        assertThat(tipoAtributoEs).isEqualToIgnoringGivenFields(testTipoAtributo);
    }

    @Test
    @Transactional
    public void createTipoAtributoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoAtributoRepository.findAll().size();

        // Create the TipoAtributo with an existing ID
        tipoAtributo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoAtributoMockMvc.perform(post("/api/tipo-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAtributo)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAtributo in the database
        List<TipoAtributo> tipoAtributoList = tipoAtributoRepository.findAll();
        assertThat(tipoAtributoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAtributoRepository.findAll().size();
        // set the field null
        tipoAtributo.setNome(null);

        // Create the TipoAtributo, which fails.

        restTipoAtributoMockMvc.perform(post("/api/tipo-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAtributo)))
            .andExpect(status().isBadRequest());

        List<TipoAtributo> tipoAtributoList = tipoAtributoRepository.findAll();
        assertThat(tipoAtributoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAtributoRepository.findAll().size();
        // set the field null
        tipoAtributo.setTipo(null);

        // Create the TipoAtributo, which fails.

        restTipoAtributoMockMvc.perform(post("/api/tipo-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAtributo)))
            .andExpect(status().isBadRequest());

        List<TipoAtributo> tipoAtributoList = tipoAtributoRepository.findAll();
        assertThat(tipoAtributoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoValorAtributoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAtributoRepository.findAll().size();
        // set the field null
        tipoAtributo.setTipoValorAtributo(null);

        // Create the TipoAtributo, which fails.

        restTipoAtributoMockMvc.perform(post("/api/tipo-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAtributo)))
            .andExpect(status().isBadRequest());

        List<TipoAtributo> tipoAtributoList = tipoAtributoRepository.findAll();
        assertThat(tipoAtributoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoAtributos() throws Exception {
        // Initialize the database
        tipoAtributoRepository.saveAndFlush(tipoAtributo);

        // Get all the tipoAtributoList
        restTipoAtributoMockMvc.perform(get("/api/tipo-atributos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAtributo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].tipoValorAtributo").value(hasItem(DEFAULT_TIPO_VALOR_ATRIBUTO.toString())));
    }

    @Test
    @Transactional
    public void getTipoAtributo() throws Exception {
        // Initialize the database
        tipoAtributoRepository.saveAndFlush(tipoAtributo);

        // Get the tipoAtributo
        restTipoAtributoMockMvc.perform(get("/api/tipo-atributos/{id}", tipoAtributo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoAtributo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.tipoValorAtributo").value(DEFAULT_TIPO_VALOR_ATRIBUTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoAtributo() throws Exception {
        // Get the tipoAtributo
        restTipoAtributoMockMvc.perform(get("/api/tipo-atributos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoAtributo() throws Exception {
        // Initialize the database
        tipoAtributoService.save(tipoAtributo);

        int databaseSizeBeforeUpdate = tipoAtributoRepository.findAll().size();

        // Update the tipoAtributo
        TipoAtributo updatedTipoAtributo = tipoAtributoRepository.findOne(tipoAtributo.getId());
        // Disconnect from session so that the updates on updatedTipoAtributo are not directly saved in db
        em.detach(updatedTipoAtributo);
        updatedTipoAtributo
            .nome(UPDATED_NOME)
            .tipo(UPDATED_TIPO)
            .tipoValorAtributo(UPDATED_TIPO_VALOR_ATRIBUTO);

        restTipoAtributoMockMvc.perform(put("/api/tipo-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoAtributo)))
            .andExpect(status().isOk());

        // Validate the TipoAtributo in the database
        List<TipoAtributo> tipoAtributoList = tipoAtributoRepository.findAll();
        assertThat(tipoAtributoList).hasSize(databaseSizeBeforeUpdate);
        TipoAtributo testTipoAtributo = tipoAtributoList.get(tipoAtributoList.size() - 1);
        assertThat(testTipoAtributo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoAtributo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTipoAtributo.getTipoValorAtributo()).isEqualTo(UPDATED_TIPO_VALOR_ATRIBUTO);

        // Validate the TipoAtributo in Elasticsearch
        TipoAtributo tipoAtributoEs = tipoAtributoSearchRepository.findOne(testTipoAtributo.getId());
        assertThat(tipoAtributoEs).isEqualToIgnoringGivenFields(testTipoAtributo);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoAtributo() throws Exception {
        int databaseSizeBeforeUpdate = tipoAtributoRepository.findAll().size();

        // Create the TipoAtributo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoAtributoMockMvc.perform(put("/api/tipo-atributos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAtributo)))
            .andExpect(status().isCreated());

        // Validate the TipoAtributo in the database
        List<TipoAtributo> tipoAtributoList = tipoAtributoRepository.findAll();
        assertThat(tipoAtributoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoAtributo() throws Exception {
        // Initialize the database
        tipoAtributoService.save(tipoAtributo);

        int databaseSizeBeforeDelete = tipoAtributoRepository.findAll().size();

        // Get the tipoAtributo
        restTipoAtributoMockMvc.perform(delete("/api/tipo-atributos/{id}", tipoAtributo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tipoAtributoExistsInEs = tipoAtributoSearchRepository.exists(tipoAtributo.getId());
        assertThat(tipoAtributoExistsInEs).isFalse();

        // Validate the database is empty
        List<TipoAtributo> tipoAtributoList = tipoAtributoRepository.findAll();
        assertThat(tipoAtributoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTipoAtributo() throws Exception {
        // Initialize the database
        tipoAtributoService.save(tipoAtributo);

        // Search the tipoAtributo
        restTipoAtributoMockMvc.perform(get("/api/_search/tipo-atributos?query=id:" + tipoAtributo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAtributo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].tipoValorAtributo").value(hasItem(DEFAULT_TIPO_VALOR_ATRIBUTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoAtributo.class);
        TipoAtributo tipoAtributo1 = new TipoAtributo();
        tipoAtributo1.setId(1L);
        TipoAtributo tipoAtributo2 = new TipoAtributo();
        tipoAtributo2.setId(tipoAtributo1.getId());
        assertThat(tipoAtributo1).isEqualTo(tipoAtributo2);
        tipoAtributo2.setId(2L);
        assertThat(tipoAtributo1).isNotEqualTo(tipoAtributo2);
        tipoAtributo1.setId(null);
        assertThat(tipoAtributo1).isNotEqualTo(tipoAtributo2);
    }
}
