package com.msb.demo.web.rest;

import com.msb.demo.domain.Index;
import com.msb.demo.repository.IndexRepository;
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
 * REST controller for managing {@link com.msb.demo.domain.Index}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IndexResource {

    private final Logger log = LoggerFactory.getLogger(IndexResource.class);

    private static final String ENTITY_NAME = "index";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndexRepository indexRepository;

    public IndexResource(IndexRepository indexRepository) {
        this.indexRepository = indexRepository;
    }

    /**
     * {@code POST  /indices} : Create a new index.
     *
     * @param index the index to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new index, or with status {@code 400 (Bad Request)} if the index has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/indices")
    public ResponseEntity<Index> createIndex(@RequestBody Index index) throws URISyntaxException {
        log.debug("REST request to save Index : {}", index);
        if (index.getId() != null) {
            throw new BadRequestAlertException("A new index cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Index result = indexRepository.save(index);
        return ResponseEntity.created(new URI("/api/indices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /indices} : Updates an existing index.
     *
     * @param index the index to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated index,
     * or with status {@code 400 (Bad Request)} if the index is not valid,
     * or with status {@code 500 (Internal Server Error)} if the index couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/indices")
    public ResponseEntity<Index> updateIndex(@RequestBody Index index) throws URISyntaxException {
        log.debug("REST request to update Index : {}", index);
        if (index.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Index result = indexRepository.save(index);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, index.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /indices} : get all the indices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indices in body.
     */
    @GetMapping("/indices")
    public List<Index> getAllIndices() {
        log.debug("REST request to get all Indices");
        return indexRepository.findAll();
    }

    /**
     * {@code GET  /indices/:id} : get the "id" index.
     *
     * @param id the id of the index to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the index, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/indices/{id}")
    public ResponseEntity<Index> getIndex(@PathVariable Long id) {
        log.debug("REST request to get Index : {}", id);
        Optional<Index> index = indexRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(index);
    }

    /**
     * {@code DELETE  /indices/:id} : delete the "id" index.
     *
     * @param id the id of the index to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/indices/{id}")
    public ResponseEntity<Void> deleteIndex(@PathVariable Long id) {
        log.debug("REST request to delete Index : {}", id);
        indexRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
