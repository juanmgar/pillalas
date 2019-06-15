package com.juanmagarcia.pillalas.service;

import com.juanmagarcia.pillalas.domain.Observatorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Observatorio}.
 */
public interface ObservatorioService {

    /**
     * Save a observatorio.
     *
     * @param observatorio the entity to save.
     * @return the persisted entity.
     */
    Observatorio save(Observatorio observatorio);

    /**
     * Get all the observatorios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Observatorio> findAll(Pageable pageable);

    /**
     * Get all the observatorios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Observatorio> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" observatorio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Observatorio> findOne(Long id);

    /**
     * Delete the "id" observatorio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
