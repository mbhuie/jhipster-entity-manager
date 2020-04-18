package com.msb.demo.web.rest;

import com.msb.demo.domain.Exchange;
import com.msb.demo.repository.ExchangeRepository;
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
 * REST controller for managing {@link com.msb.demo.domain.Exchange}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExchangeResource {

    private final Logger log = LoggerFactory.getLogger(ExchangeResource.class);

    private static final String ENTITY_NAME = "exchange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExchangeRepository exchangeRepository;

    public ExchangeResource(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    /**
     * {@code POST  /exchanges} : Create a new exchange.
     *
     * @param exchange the exchange to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exchange, or with status {@code 400 (Bad Request)} if the exchange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exchanges")
    public ResponseEntity<Exchange> createExchange(@RequestBody Exchange exchange) throws URISyntaxException {
        log.debug("REST request to save Exchange : {}", exchange);
        if (exchange.getId() != null) {
            throw new BadRequestAlertException("A new exchange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Exchange result = exchangeRepository.save(exchange);
        return ResponseEntity.created(new URI("/api/exchanges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exchanges} : Updates an existing exchange.
     *
     * @param exchange the exchange to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exchange,
     * or with status {@code 400 (Bad Request)} if the exchange is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exchange couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exchanges")
    public ResponseEntity<Exchange> updateExchange(@RequestBody Exchange exchange) throws URISyntaxException {
        log.debug("REST request to update Exchange : {}", exchange);
        if (exchange.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Exchange result = exchangeRepository.save(exchange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exchange.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exchanges} : get all the exchanges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exchanges in body.
     */
    @GetMapping("/exchanges")
    public List<Exchange> getAllExchanges() {
        log.debug("REST request to get all Exchanges");
        return exchangeRepository.findAll();
    }

    /**
     * {@code GET  /exchanges/:id} : get the "id" exchange.
     *
     * @param id the id of the exchange to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exchange, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exchanges/{id}")
    public ResponseEntity<Exchange> getExchange(@PathVariable Long id) {
        log.debug("REST request to get Exchange : {}", id);
        Optional<Exchange> exchange = exchangeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(exchange);
    }

    /**
     * {@code DELETE  /exchanges/:id} : delete the "id" exchange.
     *
     * @param id the id of the exchange to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exchanges/{id}")
    public ResponseEntity<Void> deleteExchange(@PathVariable Long id) {
        log.debug("REST request to delete Exchange : {}", id);
        exchangeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
