package com.juanmagarcia.pillalas.web.rest;

import com.juanmagarcia.pillalas.domain.Avistamiento;
import com.juanmagarcia.pillalas.service.AvistamientoService;
import com.juanmagarcia.pillalas.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.juanmagarcia.pillalas.domain.Avistamiento}.
 */
@RestController
@RequestMapping("/api")
public class AvistamientoResource {

    private final Logger log = LoggerFactory.getLogger(AvistamientoResource.class);

    private static final String ENTITY_NAME = "avistamiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvistamientoService avistamientoService;

    public AvistamientoResource(AvistamientoService avistamientoService) {
        this.avistamientoService = avistamientoService;
    }

    /**
     * {@code POST  /avistamientos} : Create a new avistamiento.
     *
     * @param avistamiento the avistamiento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avistamiento, or with status {@code 400 (Bad Request)} if the avistamiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avistamientos")
    public ResponseEntity<Avistamiento> createAvistamiento(@Valid @RequestBody Avistamiento avistamiento) throws URISyntaxException {
        log.debug("REST request to save Avistamiento : {}", avistamiento);
        if (avistamiento.getId() != null) {
            throw new BadRequestAlertException("A new avistamiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Avistamiento result = avistamientoService.save(avistamiento);
        return ResponseEntity.created(new URI("/api/avistamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avistamientos} : Updates an existing avistamiento.
     *
     * @param avistamiento the avistamiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avistamiento,
     * or with status {@code 400 (Bad Request)} if the avistamiento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avistamiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avistamientos")
    public ResponseEntity<Avistamiento> updateAvistamiento(@Valid @RequestBody Avistamiento avistamiento) throws URISyntaxException {
        log.debug("REST request to update Avistamiento : {}", avistamiento);
        if (avistamiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Avistamiento result = avistamientoService.save(avistamiento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avistamiento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avistamientos} : get all the avistamientos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avistamientos in body.
     */
    @GetMapping("/avistamientos")
    public ResponseEntity<List<Avistamiento>> getAllAvistamientos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Avistamientos");
        Page<Avistamiento> page;
        if (eagerload) {
            page = avistamientoService.findAllWithEagerRelationships(pageable);
        } else {
            page = avistamientoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avistamientos/:id} : get the "id" avistamiento.
     *
     * @param id the id of the avistamiento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avistamiento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avistamientos/{id}")
    public ResponseEntity<Avistamiento> getAvistamiento(@PathVariable Long id) {
        log.debug("REST request to get Avistamiento : {}", id);
        Optional<Avistamiento> avistamiento = avistamientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avistamiento);
    }

    /**
     * {@code DELETE  /avistamientos/:id} : delete the "id" avistamiento.
     *
     * @param id the id of the avistamiento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avistamientos/{id}")
    public ResponseEntity<Void> deleteAvistamiento(@PathVariable Long id) {
        log.debug("REST request to delete Avistamiento : {}", id);
        avistamientoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
