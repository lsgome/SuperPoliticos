package io.eduka.super.politicos.web.rest;

import io.eduka.super.politicos.SuperPoliticosApp;

import io.eduka.super.politicos.domain.LogAtualizacao;
import io.eduka.super.politicos.repository.LogAtualizacaoRepository;
import io.eduka.super.politicos.service.LogAtualizacaoService;
import io.eduka.super.politicos.repository.search.LogAtualizacaoSearchRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.eduka.super.politicos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LogAtualizacaoResource REST controller.
 *
 * @see LogAtualizacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperPoliticosApp.class)
public class LogAtualizacaoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LogAtualizacaoRepository logAtualizacaoRepository;

    @Autowired
    private LogAtualizacaoService logAtualizacaoService;

    @Autowired
    private LogAtualizacaoSearchRepository logAtualizacaoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLogAtualizacaoMockMvc;

    private LogAtualizacao logAtualizacao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogAtualizacaoResource logAtualizacaoResource = new LogAtualizacaoResource(logAtualizacaoService);
        this.restLogAtualizacaoMockMvc = MockMvcBuilders.standaloneSetup(logAtualizacaoResource)
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
    public static LogAtualizacao createEntity(EntityManager em) {
        LogAtualizacao logAtualizacao = new LogAtualizacao()
            .descricao(DEFAULT_DESCRICAO)
            .data(DEFAULT_DATA);
        return logAtualizacao;
    }

    @Before
    public void initTest() {
        logAtualizacaoSearchRepository.deleteAll();
        logAtualizacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogAtualizacao() throws Exception {
        int databaseSizeBeforeCreate = logAtualizacaoRepository.findAll().size();

        // Create the LogAtualizacao
        restLogAtualizacaoMockMvc.perform(post("/api/log-atualizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logAtualizacao)))
            .andExpect(status().isCreated());

        // Validate the LogAtualizacao in the database
        List<LogAtualizacao> logAtualizacaoList = logAtualizacaoRepository.findAll();
        assertThat(logAtualizacaoList).hasSize(databaseSizeBeforeCreate + 1);
        LogAtualizacao testLogAtualizacao = logAtualizacaoList.get(logAtualizacaoList.size() - 1);
        assertThat(testLogAtualizacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testLogAtualizacao.getData()).isEqualTo(DEFAULT_DATA);

        // Validate the LogAtualizacao in Elasticsearch
        LogAtualizacao logAtualizacaoEs = logAtualizacaoSearchRepository.findOne(testLogAtualizacao.getId());
        assertThat(logAtualizacaoEs).isEqualToIgnoringGivenFields(testLogAtualizacao);
    }

    @Test
    @Transactional
    public void createLogAtualizacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logAtualizacaoRepository.findAll().size();

        // Create the LogAtualizacao with an existing ID
        logAtualizacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogAtualizacaoMockMvc.perform(post("/api/log-atualizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logAtualizacao)))
            .andExpect(status().isBadRequest());

        // Validate the LogAtualizacao in the database
        List<LogAtualizacao> logAtualizacaoList = logAtualizacaoRepository.findAll();
        assertThat(logAtualizacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLogAtualizacaos() throws Exception {
        // Initialize the database
        logAtualizacaoRepository.saveAndFlush(logAtualizacao);

        // Get all the logAtualizacaoList
        restLogAtualizacaoMockMvc.perform(get("/api/log-atualizacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logAtualizacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    @Transactional
    public void getLogAtualizacao() throws Exception {
        // Initialize the database
        logAtualizacaoRepository.saveAndFlush(logAtualizacao);

        // Get the logAtualizacao
        restLogAtualizacaoMockMvc.perform(get("/api/log-atualizacaos/{id}", logAtualizacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logAtualizacao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogAtualizacao() throws Exception {
        // Get the logAtualizacao
        restLogAtualizacaoMockMvc.perform(get("/api/log-atualizacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogAtualizacao() throws Exception {
        // Initialize the database
        logAtualizacaoService.save(logAtualizacao);

        int databaseSizeBeforeUpdate = logAtualizacaoRepository.findAll().size();

        // Update the logAtualizacao
        LogAtualizacao updatedLogAtualizacao = logAtualizacaoRepository.findOne(logAtualizacao.getId());
        // Disconnect from session so that the updates on updatedLogAtualizacao are not directly saved in db
        em.detach(updatedLogAtualizacao);
        updatedLogAtualizacao
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA);

        restLogAtualizacaoMockMvc.perform(put("/api/log-atualizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogAtualizacao)))
            .andExpect(status().isOk());

        // Validate the LogAtualizacao in the database
        List<LogAtualizacao> logAtualizacaoList = logAtualizacaoRepository.findAll();
        assertThat(logAtualizacaoList).hasSize(databaseSizeBeforeUpdate);
        LogAtualizacao testLogAtualizacao = logAtualizacaoList.get(logAtualizacaoList.size() - 1);
        assertThat(testLogAtualizacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLogAtualizacao.getData()).isEqualTo(UPDATED_DATA);

        // Validate the LogAtualizacao in Elasticsearch
        LogAtualizacao logAtualizacaoEs = logAtualizacaoSearchRepository.findOne(testLogAtualizacao.getId());
        assertThat(logAtualizacaoEs).isEqualToIgnoringGivenFields(testLogAtualizacao);
    }

    @Test
    @Transactional
    public void updateNonExistingLogAtualizacao() throws Exception {
        int databaseSizeBeforeUpdate = logAtualizacaoRepository.findAll().size();

        // Create the LogAtualizacao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogAtualizacaoMockMvc.perform(put("/api/log-atualizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logAtualizacao)))
            .andExpect(status().isCreated());

        // Validate the LogAtualizacao in the database
        List<LogAtualizacao> logAtualizacaoList = logAtualizacaoRepository.findAll();
        assertThat(logAtualizacaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogAtualizacao() throws Exception {
        // Initialize the database
        logAtualizacaoService.save(logAtualizacao);

        int databaseSizeBeforeDelete = logAtualizacaoRepository.findAll().size();

        // Get the logAtualizacao
        restLogAtualizacaoMockMvc.perform(delete("/api/log-atualizacaos/{id}", logAtualizacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean logAtualizacaoExistsInEs = logAtualizacaoSearchRepository.exists(logAtualizacao.getId());
        assertThat(logAtualizacaoExistsInEs).isFalse();

        // Validate the database is empty
        List<LogAtualizacao> logAtualizacaoList = logAtualizacaoRepository.findAll();
        assertThat(logAtualizacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLogAtualizacao() throws Exception {
        // Initialize the database
        logAtualizacaoService.save(logAtualizacao);

        // Search the logAtualizacao
        restLogAtualizacaoMockMvc.perform(get("/api/_search/log-atualizacaos?query=id:" + logAtualizacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logAtualizacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogAtualizacao.class);
        LogAtualizacao logAtualizacao1 = new LogAtualizacao();
        logAtualizacao1.setId(1L);
        LogAtualizacao logAtualizacao2 = new LogAtualizacao();
        logAtualizacao2.setId(logAtualizacao1.getId());
        assertThat(logAtualizacao1).isEqualTo(logAtualizacao2);
        logAtualizacao2.setId(2L);
        assertThat(logAtualizacao1).isNotEqualTo(logAtualizacao2);
        logAtualizacao1.setId(null);
        assertThat(logAtualizacao1).isNotEqualTo(logAtualizacao2);
    }
}
