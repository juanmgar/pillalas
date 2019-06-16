package com.juanmagarcia.pillalas.web.rest;

import com.juanmagarcia.pillalas.domain.Observatorio;
import com.juanmagarcia.pillalas.service.ObservatorioService;
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

import java.util.*;

/**
 * REST controller for managing {@link com.juanmagarcia.pavdaw.domain.Observatorio}.
 */
@RestController
@RequestMapping("/api")
public class ObservatorioResource {

    private final Logger log = LoggerFactory.getLogger(ObservatorioResource.class);

    private static final String ENTITY_NAME = "observatorio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObservatorioService observatorioService;

    public ObservatorioResource(ObservatorioService observatorioService) {
        this.observatorioService = observatorioService;
    }

    /**
     * {@code POST  /observatorios} : Create a new observatorio.
     *
     * @param observatorio the observatorio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new observatorio, or with status {@code 400 (Bad Request)} if the observatorio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/observatorios")
    public ResponseEntity<Observatorio> createObservatorio(@Valid @RequestBody Observatorio observatorio) throws URISyntaxException {
        log.debug("REST request to save Observatorio : {}", observatorio);
        if (observatorio.getId() != null) {
            throw new BadRequestAlertException("A new observatorio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Observatorio result = observatorioService.save(observatorio);
        return ResponseEntity.created(new URI("/api/observatorios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /observatorios} : Updates an existing observatorio.
     *
     * @param observatorio the observatorio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observatorio,
     * or with status {@code 400 (Bad Request)} if the observatorio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the observatorio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/observatorios")
    public ResponseEntity<Observatorio> updateObservatorio(@Valid @RequestBody Observatorio observatorio) throws URISyntaxException {
        log.debug("REST request to update Observatorio : {}", observatorio);
        if (observatorio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Observatorio result = observatorioService.save(observatorio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observatorio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /observatorios} : get all the observatorios.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of observatorios in body.
     */
    @GetMapping("/observatorios")
    public ResponseEntity<List<Observatorio>> getAllObservatorios(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Observatorios");
        Page<Observatorio> page;
        if (eagerload) {
            page = observatorioService.findAllWithEagerRelationships(pageable);
        } else {
            page = observatorioService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

        /**
     * {@code GET  /aves} : get all the aves.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aves in body.
     */
    @GetMapping("/observatoriosfiltro/{text}")
    public ResponseEntity<List<Observatorio>> getAllObservatoriosFiltro(@PathVariable String text, Pageable pageable) {
        log.debug("REST request to get a page of Observatorios"+text);
        Page<Observatorio> page = observatorioService.findAll(pageable);
        List<Observatorio> observatorioList = page.getContent();
        List<Observatorio> observatorioResult = new ArrayList<Observatorio>();
        text = text.toString().toLowerCase();

        for ( int i=0; i<observatorioList.size();i++){
            int intIndex = observatorioList.get(i).getNombre().toLowerCase().indexOf(text);
          if(intIndex != - 1){
            observatorioResult.add(observatorioList.get(i));
          }
        }
        return ResponseEntity.ok().body(observatorioResult);
    }

    /**
     * {@code GET  /observatorios/:id} : get the "id" observatorio.
     *
     * @param id the id of the observatorio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the observatorio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/observatorios/{id}")
    public ResponseEntity<Observatorio> getObservatorio(@PathVariable Long id) {
        log.debug("REST request to get Observatorio : {}", id);
        Optional<Observatorio> observatorio = observatorioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(observatorio);
    }

    /**
     * {@code DELETE  /observatorios/:id} : delete the "id" observatorio.
     *
     * @param id the id of the observatorio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/observatorios/{id}")
    public ResponseEntity<Void> deleteObservatorio(@PathVariable Long id) {
        log.debug("REST request to delete Observatorio : {}", id);
        observatorioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
