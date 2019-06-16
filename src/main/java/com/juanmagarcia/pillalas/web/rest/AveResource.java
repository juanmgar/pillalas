package com.juanmagarcia.pillalas.web.rest;

import com.juanmagarcia.pillalas.domain.Ave;
import com.juanmagarcia.pillalas.service.AveService;
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
 * REST controller for managing {@link com.juanmagarcia.pavdaw.domain.Ave}.
 */
@RestController
@RequestMapping("/api")
public class AveResource {

    private final Logger log = LoggerFactory.getLogger(AveResource.class);

    private static final String ENTITY_NAME = "ave";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AveService aveService;

    public AveResource(AveService aveService) {
        this.aveService = aveService;
    }

    /**
     * {@code POST  /aves} : Create a new ave.
     *
     * @param ave the ave to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ave, or with status {@code 400 (Bad Request)} if the ave has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aves")
    public ResponseEntity<Ave> createAve(@Valid @RequestBody Ave ave) throws URISyntaxException {
        log.debug("REST request to save Ave : {}", ave);
        if (ave.getId() != null) {
            throw new BadRequestAlertException("A new ave cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ave result = aveService.save(ave);
        return ResponseEntity.created(new URI("/api/aves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aves} : Updates an existing ave.
     *
     * @param ave the ave to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ave,
     * or with status {@code 400 (Bad Request)} if the ave is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ave couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aves")
    public ResponseEntity<Ave> updateAve(@Valid @RequestBody Ave ave) throws URISyntaxException {
        log.debug("REST request to update Ave : {}", ave);
        if (ave.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ave result = aveService.save(ave);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ave.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aves} : get all the aves.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aves in body.
     */
    @GetMapping("/aves")
    public ResponseEntity<List<Ave>> getAllAves(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Aves");
        Page<Ave> page = aveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aves} : get all the aves.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aves in body.
     */
    @GetMapping("/avesfiltro/{text}")
    public ResponseEntity<List<Ave>> getAllAvesFiltro(@PathVariable String text, Pageable pageable) {
        log.debug("REST request to get a page of Aves"+text);
        Page<Ave> page = aveService.findAll(pageable);
        List<Ave> aveList = page.getContent();
        List<Ave> aveListResult = new ArrayList<Ave>();
        text = text.toString().toLowerCase();
        int intIndex = -1;
        for ( int i=0; i<aveList.size();i++){
            intIndex = aveList.get(i).getNombreComun().toLowerCase().indexOf(text);
            if(intIndex == -1){
                intIndex = aveList.get(i).getNombreCientifico().toLowerCase().indexOf(text);
            }
          if(intIndex != - 1){
            aveListResult.add(aveList.get(i));
          }
        }
        return ResponseEntity.ok().body(aveListResult);
    }

    /**
     * {@code GET  /aves/:id} : get the "id" ave.
     *
     * @param id the id of the ave to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ave, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aves/{id}")
    public ResponseEntity<Ave> getAve(@PathVariable Long id) {
        log.debug("REST request to get Ave : {}", id);
        Optional<Ave> ave = aveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ave);
    }

    /**
     * {@code DELETE  /aves/:id} : delete the "id" ave.
     *
     * @param id the id of the ave to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aves/{id}")
    public ResponseEntity<Void> deleteAve(@PathVariable Long id) {
        log.debug("REST request to delete Ave : {}", id);
        aveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}