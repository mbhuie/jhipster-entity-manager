package com.msb.demo.web.rest;

import com.msb.demo.domain.IndexETF;
import com.msb.demo.repository.IndexETFRepository;
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
 * REST controller for managing {@link com.msb.demo.domain.IndexETF}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IndexETFResource {

    private final Logger log = LoggerFactory.getLogger(IndexETFResource.class);

    private static final String ENTITY_NAME = "indexETF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndexETFRepository indexETFRepository;

    public IndexETFResource(IndexETFRepository indexETFRepository) {
        this.indexETFRepository = indexETFRepository;
    }

    /**
     * {@code POST  /index-etfs} : Create a new indexETF.
     *
     * @param indexETF the indexETF to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indexETF, or with status {@code 400 (Bad Request)} if the indexETF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/index-etfs")
    public ResponseEntity<IndexETF> createIndexETF(@RequestBody IndexETF indexETF) throws URISyntaxException {
        log.debug("REST request to save IndexETF : {}", indexETF);
        if (indexETF.getId() != null) {
            throw new BadRequestAlertException("A new indexETF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndexETF result = indexETFRepository.save(indexETF);
        return ResponseEntity.created(new URI("/api/index-etfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /index-etfs} : Updates an existing indexETF.
     *
     * @param indexETF the indexETF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indexETF,
     * or with status {@code 400 (Bad Request)} if the indexETF is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indexETF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/index-etfs")
    public ResponseEntity<IndexETF> updateIndexETF(@RequestBody IndexETF indexETF) throws URISyntaxException {
        log.debug("REST request to update IndexETF : {}", indexETF);
        if (indexETF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IndexETF result = indexETFRepository.save(indexETF);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indexETF.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /index-etfs} : get all the indexETFS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indexETFS in body.
     */
    @GetMapping("/index-etfs")
    public List<IndexETF> getAllIndexETFS() {
        log.debug("REST request to get all IndexETFS");
        return indexETFRepository.findAll();
    }

    /**
     * {@code GET  /index-etfs/:id} : get the "id" indexETF.
     *
     * @param id the id of the indexETF to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indexETF, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/index-etfs/{id}")
    public ResponseEntity<IndexETF> getIndexETF(@PathVariable Long id) {
        log.debug("REST request to get IndexETF : {}", id);
        Optional<IndexETF> indexETF = indexETFRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(indexETF);
    }

    /**
     * {@code DELETE  /index-etfs/:id} : delete the "id" indexETF.
     *
     * @param id the id of the indexETF to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/index-etfs/{id}")
    public ResponseEntity<Void> deleteIndexETF(@PathVariable Long id) {
        log.debug("REST request to delete IndexETF : {}", id);
        indexETFRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
