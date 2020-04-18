package com.msb.demo.web.rest;

import com.msb.demo.domain.Constituent;
import com.msb.demo.repository.ConstituentRepository;
import com.msb.demo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.msb.demo.domain.Constituent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConstituentResource {

    private final Logger log = LoggerFactory.getLogger(ConstituentResource.class);

    private static final String ENTITY_NAME = "constituent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConstituentRepository constituentRepository;

    public ConstituentResource(ConstituentRepository constituentRepository) {
        this.constituentRepository = constituentRepository;
    }

    /**
     * {@code POST  /constituents} : Create a new constituent.
     *
     * @param constituent the constituent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new constituent, or with status {@code 400 (Bad Request)} if the constituent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/constituents")
    public ResponseEntity<Constituent> createConstituent(@RequestBody Constituent constituent) throws URISyntaxException {
        log.debug("REST request to save Constituent : {}", constituent);
        if (constituent.getId() != null) {
            throw new BadRequestAlertException("A new constituent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Constituent result = constituentRepository.save(constituent);
        return ResponseEntity.created(new URI("/api/constituents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /constituents} : Updates an existing constituent.
     *
     * @param constituent the constituent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated constituent,
     * or with status {@code 400 (Bad Request)} if the constituent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the constituent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/constituents")
    public ResponseEntity<Constituent> updateConstituent(@RequestBody Constituent constituent) throws URISyntaxException {
        log.debug("REST request to update Constituent : {}", constituent);
        if (constituent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Constituent result = constituentRepository.save(constituent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, constituent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /constituents} : get all the constituents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of constituents in body.
     */
    @GetMapping("/constituents")
    public List<Constituent> getAllConstituents() {
        log.debug("REST request to get all Constituents");
        return constituentRepository.findAll();
    }

    /**
     * {@code GET  /constituents/:id} : get the "id" constituent.
     *
     * @param id the id of the constituent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the constituent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/constituents/{id}")
    public ResponseEntity<Constituent> getConstituent(@PathVariable Long id) {
        log.debug("REST request to get Constituent : {}", id);
        Optional<Constituent> constituent = constituentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(constituent);
    }

    /**
     * {@code DELETE  /constituents/:id} : delete the "id" constituent.
     *
     * @param id the id of the constituent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/constituents/{id}")
    public ResponseEntity<Void> deleteConstituent(@PathVariable Long id) {
        log.debug("REST request to delete Constituent : {}", id);
        constituentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
