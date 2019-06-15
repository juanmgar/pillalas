package com.juanmagarcia.pillalas.service.impl;

import com.juanmagarcia.pillalas.service.ObservatorioService;
import com.juanmagarcia.pillalas.domain.Observatorio;
import com.juanmagarcia.pillalas.repository.ObservatorioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Observatorio}.
 */
@Service
@Transactional
public class ObservatorioServiceImpl implements ObservatorioService {

    private final Logger log = LoggerFactory.getLogger(ObservatorioServiceImpl.class);

    private final ObservatorioRepository observatorioRepository;

    public ObservatorioServiceImpl(ObservatorioRepository observatorioRepository) {
        this.observatorioRepository = observatorioRepository;
    }

    /**
     * Save a observatorio.
     *
     * @param observatorio the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Observatorio save(Observatorio observatorio) {
        log.debug("Request to save Observatorio : {}", observatorio);
        return observatorioRepository.save(observatorio);
    }

    /**
     * Get all the observatorios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Observatorio> findAll(Pageable pageable) {
        log.debug("Request to get all Observatorios");
        return observatorioRepository.findAll(pageable);
    }

    /**
     * Get all the observatorios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Observatorio> findAllWithEagerRelationships(Pageable pageable) {
        return observatorioRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one observatorio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Observatorio> findOne(Long id) {
        log.debug("Request to get Observatorio : {}", id);
        return observatorioRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the observatorio by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Observatorio : {}", id);
        observatorioRepository.deleteById(id);
    }
}
