package com.juanmagarcia.pillalas.service;

import com.juanmagarcia.pillalas.domain.Fotografia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Fotografia}.
 */
public interface FotografiaService {

    /**
     * Save a fotografia.
     *
     * @param fotografia the entity to save.
     * @return the persisted entity.
     */
    Fotografia save(Fotografia fotografia);

    /**
     * Get all the fotografias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Fotografia> findAll(Pageable pageable);


    /**
     * Get the "id" fotografia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Fotografia> findOne(Long id);

    /**
     * Delete the "id" fotografia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
