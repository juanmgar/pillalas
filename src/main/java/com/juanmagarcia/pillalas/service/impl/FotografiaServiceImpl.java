package com.juanmagarcia.pillalas.service.impl;

import com.juanmagarcia.pillalas.service.FotografiaService;
import com.juanmagarcia.pillalas.domain.Fotografia;
import com.juanmagarcia.pillalas.repository.FotografiaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fotografia}.
 */
@Service
@Transactional
public class FotografiaServiceImpl implements FotografiaService {

    private final Logger log = LoggerFactory.getLogger(FotografiaServiceImpl.class);

    private final FotografiaRepository fotografiaRepository;

    public FotografiaServiceImpl(FotografiaRepository fotografiaRepository) {
        this.fotografiaRepository = fotografiaRepository;
    }

    /**
     * Save a fotografia.
     *
     * @param fotografia the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Fotografia save(Fotografia fotografia) {
        log.debug("Request to save Fotografia : {}", fotografia);
        return fotografiaRepository.save(fotografia);
    }

    /**
     * Get all the fotografias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Fotografia> findAll(Pageable pageable) {
        log.debug("Request to get all Fotografias");
        return fotografiaRepository.findAll(pageable);
    }


    /**
     * Get one fotografia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Fotografia> findOne(Long id) {
        log.debug("Request to get Fotografia : {}", id);
        return fotografiaRepository.findById(id);
    }

    /**
     * Delete the fotografia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fotografia : {}", id);
        fotografiaRepository.deleteById(id);
    }
}
