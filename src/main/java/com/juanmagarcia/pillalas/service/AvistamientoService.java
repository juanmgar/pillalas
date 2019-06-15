package com.juanmagarcia.pillalas.service;

import com.juanmagarcia.pillalas.domain.Avistamiento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Avistamiento}.
 */
public interface AvistamientoService {

    /**
     * Save a avistamiento.
     *
     * @param avistamiento the entity to save.
     * @return the persisted entity.
     */
    Avistamiento save(Avistamiento avistamiento);

    /**
     * Get all the avistamientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Avistamiento> findAll(Pageable pageable);

    /**
     * Get all the avistamientos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Avistamiento> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" avistamiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Avistamiento> findOne(Long id);

    /**
     * Delete the "id" avistamiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
