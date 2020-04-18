package com.msb.demo.web.rest;

import com.msb.demo.EntityManagerApp;
import com.msb.demo.domain.IndexETF;
import com.msb.demo.repository.IndexETFRepository;

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
 * Integration tests for the {@link IndexETFResource} REST controller.
 */
@SpringBootTest(classes = EntityManagerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IndexETFResourceIT {

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
    private IndexETFRepository indexETFRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndexETFMockMvc;

    private IndexETF indexETF;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndexETF createEntity(EntityManager em) {
        IndexETF indexETF = new IndexETF()
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
        return indexETF;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndexETF createUpdatedEntity(EntityManager em) {
        IndexETF indexETF = new IndexETF()
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
        return indexETF;
    }

    @BeforeEach
    public void initTest() {
        indexETF = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndexETF() throws Exception {
        int databaseSizeBeforeCreate = indexETFRepository.findAll().size();

        // Create the IndexETF
        restIndexETFMockMvc.perform(post("/api/index-etfs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indexETF)))
            .andExpect(status().isCreated());

        // Validate the IndexETF in the database
        List<IndexETF> indexETFList = indexETFRepository.findAll();
        assertThat(indexETFList).hasSize(databaseSizeBeforeCreate + 1);
        IndexETF testIndexETF = indexETFList.get(indexETFList.size() - 1);
        assertThat(testIndexETF.getBasketCurrency()).isEqualTo(DEFAULT_BASKET_CURRENCY);
        assertThat(testIndexETF.getAsAtDate()).isEqualTo(DEFAULT_AS_AT_DATE);
        assertThat(testIndexETF.getBasketInstrument()).isEqualTo(DEFAULT_BASKET_INSTRUMENT);
        assertThat(testIndexETF.getBasketInstrumentType()).isEqualTo(DEFAULT_BASKET_INSTRUMENT_TYPE);
        assertThat(testIndexETF.getBasketMinorUnits()).isEqualTo(DEFAULT_BASKET_MINOR_UNITS);
        assertThat(testIndexETF.getBasketName()).isEqualTo(DEFAULT_BASKET_NAME);
        assertThat(testIndexETF.getBasketRic()).isEqualTo(DEFAULT_BASKET_RIC);
        assertThat(testIndexETF.getBasketType()).isEqualTo(DEFAULT_BASKET_TYPE);
        assertThat(testIndexETF.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testIndexETF.getFundCurrency()).isEqualTo(DEFAULT_FUND_CURRENCY);
        assertThat(testIndexETF.isIsEtf()).isEqualTo(DEFAULT_IS_ETF);
        assertThat(testIndexETF.isIsEtc()).isEqualTo(DEFAULT_IS_ETC);
    }

    @Test
    @Transactional
    public void createIndexETFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indexETFRepository.findAll().size();

        // Create the IndexETF with an existing ID
        indexETF.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndexETFMockMvc.perform(post("/api/index-etfs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indexETF)))
            .andExpect(status().isBadRequest());

        // Validate the IndexETF in the database
        List<IndexETF> indexETFList = indexETFRepository.findAll();
        assertThat(indexETFList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIndexETFS() throws Exception {
        // Initialize the database
        indexETFRepository.saveAndFlush(indexETF);

        // Get all the indexETFList
        restIndexETFMockMvc.perform(get("/api/index-etfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indexETF.getId().intValue())))
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
    public void getIndexETF() throws Exception {
        // Initialize the database
        indexETFRepository.saveAndFlush(indexETF);

        // Get the indexETF
        restIndexETFMockMvc.perform(get("/api/index-etfs/{id}", indexETF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indexETF.getId().intValue()))
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
    public void getNonExistingIndexETF() throws Exception {
        // Get the indexETF
        restIndexETFMockMvc.perform(get("/api/index-etfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndexETF() throws Exception {
        // Initialize the database
        indexETFRepository.saveAndFlush(indexETF);

        int databaseSizeBeforeUpdate = indexETFRepository.findAll().size();

        // Update the indexETF
        IndexETF updatedIndexETF = indexETFRepository.findById(indexETF.getId()).get();
        // Disconnect from session so that the updates on updatedIndexETF are not directly saved in db
        em.detach(updatedIndexETF);
        updatedIndexETF
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

        restIndexETFMockMvc.perform(put("/api/index-etfs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndexETF)))
            .andExpect(status().isOk());

        // Validate the IndexETF in the database
        List<IndexETF> indexETFList = indexETFRepository.findAll();
        assertThat(indexETFList).hasSize(databaseSizeBeforeUpdate);
        IndexETF testIndexETF = indexETFList.get(indexETFList.size() - 1);
        assertThat(testIndexETF.getBasketCurrency()).isEqualTo(UPDATED_BASKET_CURRENCY);
        assertThat(testIndexETF.getAsAtDate()).isEqualTo(UPDATED_AS_AT_DATE);
        assertThat(testIndexETF.getBasketInstrument()).isEqualTo(UPDATED_BASKET_INSTRUMENT);
        assertThat(testIndexETF.getBasketInstrumentType()).isEqualTo(UPDATED_BASKET_INSTRUMENT_TYPE);
        assertThat(testIndexETF.getBasketMinorUnits()).isEqualTo(UPDATED_BASKET_MINOR_UNITS);
        assertThat(testIndexETF.getBasketName()).isEqualTo(UPDATED_BASKET_NAME);
        assertThat(testIndexETF.getBasketRic()).isEqualTo(UPDATED_BASKET_RIC);
        assertThat(testIndexETF.getBasketType()).isEqualTo(UPDATED_BASKET_TYPE);
        assertThat(testIndexETF.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testIndexETF.getFundCurrency()).isEqualTo(UPDATED_FUND_CURRENCY);
        assertThat(testIndexETF.isIsEtf()).isEqualTo(UPDATED_IS_ETF);
        assertThat(testIndexETF.isIsEtc()).isEqualTo(UPDATED_IS_ETC);
    }

    @Test
    @Transactional
    public void updateNonExistingIndexETF() throws Exception {
        int databaseSizeBeforeUpdate = indexETFRepository.findAll().size();

        // Create the IndexETF

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndexETFMockMvc.perform(put("/api/index-etfs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indexETF)))
            .andExpect(status().isBadRequest());

        // Validate the IndexETF in the database
        List<IndexETF> indexETFList = indexETFRepository.findAll();
        assertThat(indexETFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIndexETF() throws Exception {
        // Initialize the database
        indexETFRepository.saveAndFlush(indexETF);

        int databaseSizeBeforeDelete = indexETFRepository.findAll().size();

        // Delete the indexETF
        restIndexETFMockMvc.perform(delete("/api/index-etfs/{id}", indexETF.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndexETF> indexETFList = indexETFRepository.findAll();
        assertThat(indexETFList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
