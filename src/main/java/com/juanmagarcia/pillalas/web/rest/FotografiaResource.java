package com.juanmagarcia.pillalas.web.rest;

import com.juanmagarcia.pillalas.domain.Fotografia;
import com.juanmagarcia.pillalas.service.FotografiaService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.juanmagarcia.pillalas.domain.Fotografia}.
 */
@RestController
@RequestMapping("/api")
public class FotografiaResource {

    private final Logger log = LoggerFactory.getLogger(FotografiaResource.class);

    private static final String ENTITY_NAME = "fotografia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotografiaService fotografiaService;

    public FotografiaResource(FotografiaService fotografiaService) {
        this.fotografiaService = fotografiaService;
    }

    /**
     * {@code POST  /fotografias} : Create a new fotografia.
     *
     * @param fotografia the fotografia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotografia, or with status {@code 400 (Bad Request)} if the fotografia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fotografias")
    public ResponseEntity<Fotografia> createFotografia(@RequestBody Fotografia fotografia) throws URISyntaxException {
        log.debug("REST request to save Fotografia : {}", fotografia);
        if (fotografia.getId() != null) {
            throw new BadRequestAlertException("A new fotografia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fotografia result = fotografiaService.save(fotografia);
        return ResponseEntity.created(new URI("/api/fotografias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fotografias} : Updates an existing fotografia.
     *
     * @param fotografia the fotografia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotografia,
     * or with status {@code 400 (Bad Request)} if the fotografia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotografia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fotografias")
    public ResponseEntity<Fotografia> updateFotografia(@RequestBody Fotografia fotografia) throws URISyntaxException {
        log.debug("REST request to update Fotografia : {}", fotografia);
        if (fotografia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fotografia result = fotografiaService.save(fotografia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotografia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fotografias} : get all the fotografias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotografias in body.
     */
    @GetMapping("/fotografias")
    public ResponseEntity<List<Fotografia>> getAllFotografias(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Fotografias");
        Page<Fotografia> page = fotografiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fotografias/:id} : get the "id" fotografia.
     *
     * @param id the id of the fotografia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotografia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fotografias/{id}")
    public ResponseEntity<Fotografia> getFotografia(@PathVariable Long id) {
        log.debug("REST request to get Fotografia : {}", id);
        Optional<Fotografia> fotografia = fotografiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotografia);
    }

    /**
     * {@code DELETE  /fotografias/:id} : delete the "id" fotografia.
     *
     * @param id the id of the fotografia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fotografias/{id}")
    public ResponseEntity<Void> deleteFotografia(@PathVariable Long id) {
        log.debug("REST request to delete Fotografia : {}", id);
        fotografiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
