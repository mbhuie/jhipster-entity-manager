package com.msb.demo.web.rest;

import com.msb.demo.EntityManagerApp;
import com.msb.demo.domain.Exchange;
import com.msb.demo.repository.ExchangeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExchangeResource} REST controller.
 */
@SpringBootTest(classes = EntityManagerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ExchangeResourceIT {

    private static final String DEFAULT_MIC = "AAAAAAAAAA";
    private static final String UPDATED_MIC = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExchangeMockMvc;

    private Exchange exchange;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exchange createEntity(EntityManager em) {
        Exchange exchange = new Exchange()
            .mic(DEFAULT_MIC)
            .name(DEFAULT_NAME);
        return exchange;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exchange createUpdatedEntity(EntityManager em) {
        Exchange exchange = new Exchange()
            .mic(UPDATED_MIC)
            .name(UPDATED_NAME);
        return exchange;
    }

    @BeforeEach
    public void initTest() {
        exchange = createEntity(em);
    }

    @Test
    @Transactional
    public void createExchange() throws Exception {
        int databaseSizeBeforeCreate = exchangeRepository.findAll().size();

        // Create the Exchange
        restExchangeMockMvc.perform(post("/api/exchanges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exchange)))
            .andExpect(status().isCreated());

        // Validate the Exchange in the database
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeCreate + 1);
        Exchange testExchange = exchangeList.get(exchangeList.size() - 1);
        assertThat(testExchange.getMic()).isEqualTo(DEFAULT_MIC);
        assertThat(testExchange.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createExchangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exchangeRepository.findAll().size();

        // Create the Exchange with an existing ID
        exchange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExchangeMockMvc.perform(post("/api/exchanges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exchange)))
            .andExpect(status().isBadRequest());

        // Validate the Exchange in the database
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExchanges() throws Exception {
        // Initialize the database
        exchangeRepository.saveAndFlush(exchange);

        // Get all the exchangeList
        restExchangeMockMvc.perform(get("/api/exchanges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exchange.getId().intValue())))
            .andExpect(jsonPath("$.[*].mic").value(hasItem(DEFAULT_MIC)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getExchange() throws Exception {
        // Initialize the database
        exchangeRepository.saveAndFlush(exchange);

        // Get the exchange
        restExchangeMockMvc.perform(get("/api/exchanges/{id}", exchange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exchange.getId().intValue()))
            .andExpect(jsonPath("$.mic").value(DEFAULT_MIC))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingExchange() throws Exception {
        // Get the exchange
        restExchangeMockMvc.perform(get("/api/exchanges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExchange() throws Exception {
        // Initialize the database
        exchangeRepository.saveAndFlush(exchange);

        int databaseSizeBeforeUpdate = exchangeRepository.findAll().size();

        // Update the exchange
        Exchange updatedExchange = exchangeRepository.findById(exchange.getId()).get();
        // Disconnect from session so that the updates on updatedExchange are not directly saved in db
        em.detach(updatedExchange);
        updatedExchange
            .mic(UPDATED_MIC)
            .name(UPDATED_NAME);

        restExchangeMockMvc.perform(put("/api/exchanges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExchange)))
            .andExpect(status().isOk());

        // Validate the Exchange in the database
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeUpdate);
        Exchange testExchange = exchangeList.get(exchangeList.size() - 1);
        assertThat(testExchange.getMic()).isEqualTo(UPDATED_MIC);
        assertThat(testExchange.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingExchange() throws Exception {
        int databaseSizeBeforeUpdate = exchangeRepository.findAll().size();

        // Create the Exchange

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExchangeMockMvc.perform(put("/api/exchanges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exchange)))
            .andExpect(status().isBadRequest());

        // Validate the Exchange in the database
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExchange() throws Exception {
        // Initialize the database
        exchangeRepository.saveAndFlush(exchange);

        int databaseSizeBeforeDelete = exchangeRepository.findAll().size();

        // Delete the exchange
        restExchangeMockMvc.perform(delete("/api/exchanges/{id}", exchange.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
