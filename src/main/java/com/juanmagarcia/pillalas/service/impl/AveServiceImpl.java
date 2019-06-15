package com.juanmagarcia.pillalas.service.impl;

import com.juanmagarcia.pillalas.service.AveService;
import com.juanmagarcia.pillalas.domain.Ave;
import com.juanmagarcia.pillalas.repository.AveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ave}.
 */
@Service
@Transactional
public class AveServiceImpl implements AveService {

    private final Logger log = LoggerFactory.getLogger(AveServiceImpl.class);

    private final AveRepository aveRepository;

    public AveServiceImpl(AveRepository aveRepository) {
        this.aveRepository = aveRepository;
    }

    /**
     * Save a ave.
     *
     * @param ave the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Ave save(Ave ave) {
        log.debug("Request to save Ave : {}", ave);
        return aveRepository.save(ave);
    }

    /**
     * Get all the aves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ave> findAll(Pageable pageable) {
        log.debug("Request to get all Aves");
        return aveRepository.findAll(pageable);
    }


    /**
     * Get one ave by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Ave> findOne(Long id) {
        log.debug("Request to get Ave : {}", id);
        return aveRepository.findById(id);
    }

    /**
     * Delete the ave by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ave : {}", id);
        aveRepository.deleteById(id);
    }
}
