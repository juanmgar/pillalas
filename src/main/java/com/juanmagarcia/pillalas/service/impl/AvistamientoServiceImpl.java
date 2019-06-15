package com.juanmagarcia.pillalas.service.impl;

import com.juanmagarcia.pillalas.service.AvistamientoService;
import com.juanmagarcia.pillalas.domain.Avistamiento;
import com.juanmagarcia.pillalas.repository.AvistamientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Avistamiento}.
 */
@Service
@Transactional
public class AvistamientoServiceImpl implements AvistamientoService {

    private final Logger log = LoggerFactory.getLogger(AvistamientoServiceImpl.class);

    private final AvistamientoRepository avistamientoRepository;

    public AvistamientoServiceImpl(AvistamientoRepository avistamientoRepository) {
        this.avistamientoRepository = avistamientoRepository;
    }

    /**
     * Save a avistamiento.
     *
     * @param avistamiento the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Avistamiento save(Avistamiento avistamiento) {
        log.debug("Request to save Avistamiento : {}", avistamiento);
        return avistamientoRepository.save(avistamiento);
    }

    /**
     * Get all the avistamientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Avistamiento> findAll(Pageable pageable) {
        log.debug("Request to get all Avistamientos");
        return avistamientoRepository.findAll(pageable);
    }

    /**
     * Get all the avistamientos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Avistamiento> findAllWithEagerRelationships(Pageable pageable) {
        return avistamientoRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one avistamiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Avistamiento> findOne(Long id) {
        log.debug("Request to get Avistamiento : {}", id);
        return avistamientoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the avistamiento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Avistamiento : {}", id);
        avistamientoRepository.deleteById(id);
    }
}
