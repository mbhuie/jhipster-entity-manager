package com.msb.demo.web.rest;

import com.msb.demo.EntityManagerApp;
import com.msb.demo.domain.Constituent;
import com.msb.demo.repository.ConstituentRepository;

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
 * Integration tests for the {@link ConstituentResource} REST controller.
 */
@SpringBootTest(classes = EntityManagerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ConstituentResourceIT {

    private static final String DEFAULT_RIC = "AAAAAAAAAA";
    private static final String UPDATED_RIC = "BBBBBBBBBB";

    private static final String DEFAULT_ISIN = "AAAAAAAAAA";
    private static final String UPDATED_ISIN = "BBBBBBBBBB";

    private static final String DEFAULT_SEDOL = "AAAAAAAAAA";
    private static final String UPDATED_SEDOL = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MIC = "AAAAAAAAAA";
    private static final String UPDATED_MIC = "BBBBBBBBBB";

    private static final String DEFAULT_CENTER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CENTER_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_SHARES_OUTSTANDING = 1L;
    private static final Long UPDATED_SHARES_OUTSTANDING = 2L;

    @Autowired
    private ConstituentRepository constituentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConstituentMockMvc;

    private Constituent constituent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Constituent createEntity(EntityManager em) {
        Constituent constituent = new Constituent()
            .ric(DEFAULT_RIC)
            .isin(DEFAULT_ISIN)
            .sedol(DEFAULT_SEDOL)
            .currency(DEFAULT_CURRENCY)
            .issueType(DEFAULT_ISSUE_TYPE)
            .mic(DEFAULT_MIC)
            .centerCode(DEFAULT_CENTER_CODE)
            .sharesOutstanding(DEFAULT_SHARES_OUTSTANDING);
        return constituent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Constituent createUpdatedEntity(EntityManager em) {
        Constituent constituent = new Constituent()
            .ric(UPDATED_RIC)
            .isin(UPDATED_ISIN)
            .sedol(UPDATED_SEDOL)
            .currency(UPDATED_CURRENCY)
            .issueType(UPDATED_ISSUE_TYPE)
            .mic(UPDATED_MIC)
            .centerCode(UPDATED_CENTER_CODE)
            .sharesOutstanding(UPDATED_SHARES_OUTSTANDING);
        return constituent;
    }

    @BeforeEach
    public void initTest() {
        constituent = createEntity(em);
    }

    @Test
    @Transactional
    public void createConstituent() throws Exception {
        int databaseSizeBeforeCreate = constituentRepository.findAll().size();

        // Create the Constituent
        restConstituentMockMvc.perform(post("/api/constituents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constituent)))
            .andExpect(status().isCreated());

        // Validate the Constituent in the database
        List<Constituent> constituentList = constituentRepository.findAll();
        assertThat(constituentList).hasSize(databaseSizeBeforeCreate + 1);
        Constituent testConstituent = constituentList.get(constituentList.size() - 1);
        assertThat(testConstituent.getRic()).isEqualTo(DEFAULT_RIC);
        assertThat(testConstituent.getIsin()).isEqualTo(DEFAULT_ISIN);
        assertThat(testConstituent.getSedol()).isEqualTo(DEFAULT_SEDOL);
        assertThat(testConstituent.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testConstituent.getIssueType()).isEqualTo(DEFAULT_ISSUE_TYPE);
        assertThat(testConstituent.getMic()).isEqualTo(DEFAULT_MIC);
        assertThat(testConstituent.getCenterCode()).isEqualTo(DEFAULT_CENTER_CODE);
        assertThat(testConstituent.getSharesOutstanding()).isEqualTo(DEFAULT_SHARES_OUTSTANDING);
    }

    @Test
    @Transactional
    public void createConstituentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = constituentRepository.findAll().size();

        // Create the Constituent with an existing ID
        constituent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConstituentMockMvc.perform(post("/api/constituents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constituent)))
            .andExpect(status().isBadRequest());

        // Validate the Constituent in the database
        List<Constituent> constituentList = constituentRepository.findAll();
        assertThat(constituentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConstituents() throws Exception {
        // Initialize the database
        constituentRepository.saveAndFlush(constituent);

        // Get all the constituentList
        restConstituentMockMvc.perform(get("/api/constituents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(constituent.getId().intValue())))
            .andExpect(jsonPath("$.[*].ric").value(hasItem(DEFAULT_RIC)))
            .andExpect(jsonPath("$.[*].isin").value(hasItem(DEFAULT_ISIN)))
            .andExpect(jsonPath("$.[*].sedol").value(hasItem(DEFAULT_SEDOL)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].issueType").value(hasItem(DEFAULT_ISSUE_TYPE)))
            .andExpect(jsonPath("$.[*].mic").value(hasItem(DEFAULT_MIC)))
            .andExpect(jsonPath("$.[*].centerCode").value(hasItem(DEFAULT_CENTER_CODE)))
            .andExpect(jsonPath("$.[*].sharesOutstanding").value(hasItem(DEFAULT_SHARES_OUTSTANDING.intValue())));
    }
    
    @Test
    @Transactional
    public void getConstituent() throws Exception {
        // Initialize the database
        constituentRepository.saveAndFlush(constituent);

        // Get the constituent
        restConstituentMockMvc.perform(get("/api/constituents/{id}", constituent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(constituent.getId().intValue()))
            .andExpect(jsonPath("$.ric").value(DEFAULT_RIC))
            .andExpect(jsonPath("$.isin").value(DEFAULT_ISIN))
            .andExpect(jsonPath("$.sedol").value(DEFAULT_SEDOL))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.issueType").value(DEFAULT_ISSUE_TYPE))
            .andExpect(jsonPath("$.mic").value(DEFAULT_MIC))
            .andExpect(jsonPath("$.centerCode").value(DEFAULT_CENTER_CODE))
            .andExpect(jsonPath("$.sharesOutstanding").value(DEFAULT_SHARES_OUTSTANDING.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConstituent() throws Exception {
        // Get the constituent
        restConstituentMockMvc.perform(get("/api/constituents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConstituent() throws Exception {
        // Initialize the database
        constituentRepository.saveAndFlush(constituent);

        int databaseSizeBeforeUpdate = constituentRepository.findAll().size();

        // Update the constituent
        Constituent updatedConstituent = constituentRepository.findById(constituent.getId()).get();
        // Disconnect from session so that the updates on updatedConstituent are not directly saved in db
        em.detach(updatedConstituent);
        updatedConstituent
            .ric(UPDATED_RIC)
            .isin(UPDATED_ISIN)
            .sedol(UPDATED_SEDOL)
            .currency(UPDATED_CURRENCY)
            .issueType(UPDATED_ISSUE_TYPE)
            .mic(UPDATED_MIC)
            .centerCode(UPDATED_CENTER_CODE)
            .sharesOutstanding(UPDATED_SHARES_OUTSTANDING);

        restConstituentMockMvc.perform(put("/api/constituents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConstituent)))
            .andExpect(status().isOk());

        // Validate the Constituent in the database
        List<Constituent> constituentList = constituentRepository.findAll();
        assertThat(constituentList).hasSize(databaseSizeBeforeUpdate);
        Constituent testConstituent = constituentList.get(constituentList.size() - 1);
        assertThat(testConstituent.getRic()).isEqualTo(UPDATED_RIC);
        assertThat(testConstituent.getIsin()).isEqualTo(UPDATED_ISIN);
        assertThat(testConstituent.getSedol()).isEqualTo(UPDATED_SEDOL);
        assertThat(testConstituent.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testConstituent.getIssueType()).isEqualTo(UPDATED_ISSUE_TYPE);
        assertThat(testConstituent.getMic()).isEqualTo(UPDATED_MIC);
        assertThat(testConstituent.getCenterCode()).isEqualTo(UPDATED_CENTER_CODE);
        assertThat(testConstituent.getSharesOutstanding()).isEqualTo(UPDATED_SHARES_OUTSTANDING);
    }

    @Test
    @Transactional
    public void updateNonExistingConstituent() throws Exception {
        int databaseSizeBeforeUpdate = constituentRepository.findAll().size();

        // Create the Constituent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConstituentMockMvc.perform(put("/api/constituents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constituent)))
            .andExpect(status().isBadRequest());

        // Validate the Constituent in the database
        List<Constituent> constituentList = constituentRepository.findAll();
        assertThat(constituentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConstituent() throws Exception {
        // Initialize the database
        constituentRepository.saveAndFlush(constituent);

        int databaseSizeBeforeDelete = constituentRepository.findAll().size();

        // Delete the constituent
        restConstituentMockMvc.perform(delete("/api/constituents/{id}", constituent.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Constituent> constituentList = constituentRepository.findAll();
        assertThat(constituentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
