package com.juanmagarcia.pillalas.service;

import com.juanmagarcia.pillalas.domain.Ave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Ave}.
 */
public interface AveService {

    /**
     * Save a ave.
     *
     * @param ave the entity to save.
     * @return the persisted entity.
     */
    Ave save(Ave ave);

    /**
     * Get all the aves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Ave> findAll(Pageable pageable);


    /**
     * Get the "id" ave.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ave> findOne(Long id);

    /**
     * Delete the "id" ave.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
