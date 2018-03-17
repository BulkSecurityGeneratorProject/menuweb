package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Plat;
import com.mycompany.myapp.service.PlatService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.PlatCriteria;
import com.mycompany.myapp.service.PlatQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Plat.
 */
@RestController
@RequestMapping("/api")
public class PlatResource {

    private final Logger log = LoggerFactory.getLogger(PlatResource.class);

    private static final String ENTITY_NAME = "plat";

    private final PlatService platService;

    private final PlatQueryService platQueryService;

    public PlatResource(PlatService platService, PlatQueryService platQueryService) {
        this.platService = platService;
        this.platQueryService = platQueryService;
    }

    /**
     * POST  /plats : Create a new plat.
     *
     * @param plat the plat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plat, or with status 400 (Bad Request) if the plat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plats")
    @Timed
    public ResponseEntity<Plat> createPlat(@Valid @RequestBody Plat plat) throws URISyntaxException {
        log.debug("REST request to save Plat : {}", plat);
        if (plat.getId() != null) {
            throw new BadRequestAlertException("A new plat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plat result = platService.save(plat);
        return ResponseEntity.created(new URI("/api/plats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plats : Updates an existing plat.
     *
     * @param plat the plat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plat,
     * or with status 400 (Bad Request) if the plat is not valid,
     * or with status 500 (Internal Server Error) if the plat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plats")
    @Timed
    public ResponseEntity<Plat> updatePlat(@Valid @RequestBody Plat plat) throws URISyntaxException {
        log.debug("REST request to update Plat : {}", plat);
        if (plat.getId() == null) {
            return createPlat(plat);
        }
        Plat result = platService.save(plat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plats : get all the plats.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of plats in body
     */
    @GetMapping("/plats")
    @Timed
    public ResponseEntity<List<Plat>> getAllPlats(PlatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Plats by criteria: {}", criteria);
        Page<Plat> page = platQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /plats/:id : get the "id" plat.
     *
     * @param id the id of the plat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plat, or with status 404 (Not Found)
     */
    @GetMapping("/plats/{id}")
    @Timed
    public ResponseEntity<Plat> getPlat(@PathVariable Long id) {
        log.debug("REST request to get Plat : {}", id);
        Plat plat = platService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plat));
    }

    /**
     * DELETE  /plats/:id : delete the "id" plat.
     *
     * @param id the id of the plat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plats/{id}")
    @Timed
    public ResponseEntity<Void> deletePlat(@PathVariable Long id) {
        log.debug("REST request to delete Plat : {}", id);
        platService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
