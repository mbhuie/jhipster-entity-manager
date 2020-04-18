package com.msb.demo.web.rest;

import com.msb.demo.EntityManagerApp;
import com.msb.demo.domain.Index;
import com.msb.demo.repository.IndexRepository;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IndexResource} REST controller.
 */
@SpringBootTest(classes = EntityManagerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IndexResourceIT {

    private static final String DEFAULT_BASKET_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_BASKET_CURRENCY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_AS_AT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AS_AT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BASKET_INSTRUMENT = "AAAAAAAAAA";
    private static final String UPDATED_BASKET_INSTRUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_BASKET_INSTRUMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BASKET_INSTRUMENT_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BASKET_MINOR_UNITS = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASKET_MINOR_UNITS = new BigDecimal(2);

    private static final String DEFAULT_BASKET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BASKET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BASKET_RIC = "AAAAAAAAAA";
    private static final String UPDATED_BASKET_RIC = "BBBBBBBBBB";

    private static final String DEFAULT_BASKET_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BASKET_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY = "BBBBBBBBBB";

    private static final String DEFAULT_FUND_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_FUND_CURRENCY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ETF = false;
    private static final Boolean UPDATED_IS_ETF = true;

    private static final Boolean DEFAULT_IS_ETC = false;
    private static final Boolean UPDATED_IS_ETC = true;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndexMockMvc;

    private Index index;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Index createEntity(EntityManager em) {
        Index index = new Index()
            .basketCurrency(DEFAULT_BASKET_CURRENCY)
            .asAtDate(DEFAULT_AS_AT_DATE)
            .basketInstrument(DEFAULT_BASKET_INSTRUMENT)
            .basketInstrumentType(DEFAULT_BASKET_INSTRUMENT_TYPE)
            .basketMinorUnits(DEFAULT_BASKET_MINOR_UNITS)
            .basketName(DEFAULT_BASKET_NAME)
            .basketRic(DEFAULT_BASKET_RIC)
            .basketType(DEFAULT_BASKET_TYPE)
            .family(DEFAULT_FAMILY)
            .fundCurrency(DEFAULT_FUND_CURRENCY)
            .isEtf(DEFAULT_IS_ETF)
            .isEtc(DEFAULT_IS_ETC);
        return index;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Index createUpdatedEntity(EntityManager em) {
        Index index = new Index()
            .basketCurrency(UPDATED_BASKET_CURRENCY)
            .asAtDate(UPDATED_AS_AT_DATE)
            .basketInstrument(UPDATED_BASKET_INSTRUMENT)
            .basketInstrumentType(UPDATED_BASKET_INSTRUMENT_TYPE)
            .basketMinorUnits(UPDATED_BASKET_MINOR_UNITS)
            .basketName(UPDATED_BASKET_NAME)
            .basketRic(UPDATED_BASKET_RIC)
            .basketType(UPDATED_BASKET_TYPE)
            .family(UPDATED_FAMILY)
            .fundCurrency(UPDATED_FUND_CURRENCY)
            .isEtf(UPDATED_IS_ETF)
            .isEtc(UPDATED_IS_ETC);
        return index;
    }

    @BeforeEach
    public void initTest() {
        index = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndex() throws Exception {
        int databaseSizeBeforeCreate = indexRepository.findAll().size();

        // Create the Index
        restIndexMockMvc.perform(post("/api/indices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(index)))
            .andExpect(status().isCreated());

        // Validate the Index in the database
        List<Index> indexList = indexRepository.findAll();
        assertThat(indexList).hasSize(databaseSizeBeforeCreate + 1);
        Index testIndex = indexList.get(indexList.size() - 1);
        assertThat(testIndex.getBasketCurrency()).isEqualTo(DEFAULT_BASKET_CURRENCY);
        assertThat(testIndex.getAsAtDate()).isEqualTo(DEFAULT_AS_AT_DATE);
        assertThat(testIndex.getBasketInstrument()).isEqualTo(DEFAULT_BASKET_INSTRUMENT);
        assertThat(testIndex.getBasketInstrumentType()).isEqualTo(DEFAULT_BASKET_INSTRUMENT_TYPE);
        assertThat(testIndex.getBasketMinorUnits()).isEqualTo(DEFAULT_BASKET_MINOR_UNITS);
        assertThat(testIndex.getBasketName()).isEqualTo(DEFAULT_BASKET_NAME);
        assertThat(testIndex.getBasketRic()).isEqualTo(DEFAULT_BASKET_RIC);
        assertThat(testIndex.getBasketType()).isEqualTo(DEFAULT_BASKET_TYPE);
        assertThat(testIndex.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testIndex.getFundCurrency()).isEqualTo(DEFAULT_FUND_CURRENCY);
        assertThat(testIndex.isIsEtf()).isEqualTo(DEFAULT_IS_ETF);
        assertThat(testIndex.isIsEtc()).isEqualTo(DEFAULT_IS_ETC);
    }

    @Test
    @Transactional
    public void createIndexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indexRepository.findAll().size();

        // Create the Index with an existing ID
        index.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndexMockMvc.perform(post("/api/indices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(index)))
            .andExpect(status().isBadRequest());

        // Validate the Index in the database
        List<Index> indexList = indexRepository.findAll();
        assertThat(indexList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIndices() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        // Get all the indexList
        restIndexMockMvc.perform(get("/api/indices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(index.getId().intValue())))
            .andExpect(jsonPath("$.[*].basketCurrency").value(hasItem(DEFAULT_BASKET_CURRENCY)))
            .andExpect(jsonPath("$.[*].asAtDate").value(hasItem(DEFAULT_AS_AT_DATE.toString())))
            .andExpect(jsonPath("$.[*].basketInstrument").value(hasItem(DEFAULT_BASKET_INSTRUMENT)))
            .andExpect(jsonPath("$.[*].basketInstrumentType").value(hasItem(DEFAULT_BASKET_INSTRUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].basketMinorUnits").value(hasItem(DEFAULT_BASKET_MINOR_UNITS.intValue())))
            .andExpect(jsonPath("$.[*].basketName").value(hasItem(DEFAULT_BASKET_NAME)))
            .andExpect(jsonPath("$.[*].basketRic").value(hasItem(DEFAULT_BASKET_RIC)))
            .andExpect(jsonPath("$.[*].basketType").value(hasItem(DEFAULT_BASKET_TYPE)))
            .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY)))
            .andExpect(jsonPath("$.[*].fundCurrency").value(hasItem(DEFAULT_FUND_CURRENCY)))
            .andExpect(jsonPath("$.[*].isEtf").value(hasItem(DEFAULT_IS_ETF.booleanValue())))
            .andExpect(jsonPath("$.[*].isEtc").value(hasItem(DEFAULT_IS_ETC.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getIndex() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        // Get the index
        restIndexMockMvc.perform(get("/api/indices/{id}", index.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(index.getId().intValue()))
            .andExpect(jsonPath("$.basketCurrency").value(DEFAULT_BASKET_CURRENCY))
            .andExpect(jsonPath("$.asAtDate").value(DEFAULT_AS_AT_DATE.toString()))
            .andExpect(jsonPath("$.basketInstrument").value(DEFAULT_BASKET_INSTRUMENT))
            .andExpect(jsonPath("$.basketInstrumentType").value(DEFAULT_BASKET_INSTRUMENT_TYPE))
            .andExpect(jsonPath("$.basketMinorUnits").value(DEFAULT_BASKET_MINOR_UNITS.intValue()))
            .andExpect(jsonPath("$.basketName").value(DEFAULT_BASKET_NAME))
            .andExpect(jsonPath("$.basketRic").value(DEFAULT_BASKET_RIC))
            .andExpect(jsonPath("$.basketType").value(DEFAULT_BASKET_TYPE))
            .andExpect(jsonPath("$.family").value(DEFAULT_FAMILY))
            .andExpect(jsonPath("$.fundCurrency").value(DEFAULT_FUND_CURRENCY))
            .andExpect(jsonPath("$.isEtf").value(DEFAULT_IS_ETF.booleanValue()))
            .andExpect(jsonPath("$.isEtc").value(DEFAULT_IS_ETC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIndex() throws Exception {
        // Get the index
        restIndexMockMvc.perform(get("/api/indices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndex() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        int databaseSizeBeforeUpdate = indexRepository.findAll().size();

        // Update the index
        Index updatedIndex = indexRepository.findById(index.getId()).get();
        // Disconnect from session so that the updates on updatedIndex are not directly saved in db
        em.detach(updatedIndex);
        updatedIndex
            .basketCurrency(UPDATED_BASKET_CURRENCY)
            .asAtDate(UPDATED_AS_AT_DATE)
            .basketInstrument(UPDATED_BASKET_INSTRUMENT)
            .basketInstrumentType(UPDATED_BASKET_INSTRUMENT_TYPE)
            .basketMinorUnits(UPDATED_BASKET_MINOR_UNITS)
            .basketName(UPDATED_BASKET_NAME)
            .basketRic(UPDATED_BASKET_RIC)
            .basketType(UPDATED_BASKET_TYPE)
            .family(UPDATED_FAMILY)
            .fundCurrency(UPDATED_FUND_CURRENCY)
            .isEtf(UPDATED_IS_ETF)
            .isEtc(UPDATED_IS_ETC);

        restIndexMockMvc.perform(put("/api/indices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndex)))
            .andExpect(status().isOk());

        // Validate the Index in the database
        List<Index> indexList = indexRepository.findAll();
        assertThat(indexList).hasSize(databaseSizeBeforeUpdate);
        Index testIndex = indexList.get(indexList.size() - 1);
        assertThat(testIndex.getBasketCurrency()).isEqualTo(UPDATED_BASKET_CURRENCY);
        assertThat(testIndex.getAsAtDate()).isEqualTo(UPDATED_AS_AT_DATE);
        assertThat(testIndex.getBasketInstrument()).isEqualTo(UPDATED_BASKET_INSTRUMENT);
        assertThat(testIndex.getBasketInstrumentType()).isEqualTo(UPDATED_BASKET_INSTRUMENT_TYPE);
        assertThat(testIndex.getBasketMinorUnits()).isEqualTo(UPDATED_BASKET_MINOR_UNITS);
        assertThat(testIndex.getBasketName()).isEqualTo(UPDATED_BASKET_NAME);
        assertThat(testIndex.getBasketRic()).isEqualTo(UPDATED_BASKET_RIC);
        assertThat(testIndex.getBasketType()).isEqualTo(UPDATED_BASKET_TYPE);
        assertThat(testIndex.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testIndex.getFundCurrency()).isEqualTo(UPDATED_FUND_CURRENCY);
        assertThat(testIndex.isIsEtf()).isEqualTo(UPDATED_IS_ETF);
        assertThat(testIndex.isIsEtc()).isEqualTo(UPDATED_IS_ETC);
    }

    @Test
    @Transactional
    public void updateNonExistingIndex() throws Exception {
        int databaseSizeBeforeUpdate = indexRepository.findAll().size();

        // Create the Index

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndexMockMvc.perform(put("/api/indices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(index)))
            .andExpect(status().isBadRequest());

        // Validate the Index in the database
        List<Index> indexList = indexRepository.findAll();
        assertThat(indexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIndex() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        int databaseSizeBeforeDelete = indexRepository.findAll().size();

        // Delete the index
        restIndexMockMvc.perform(delete("/api/indices/{id}", index.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Index> indexList = indexRepository.findAll();
        assertThat(indexList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
