package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Plat;
import com.mycompany.myapp.repository.PlatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Plat.
 */
@Service
@Transactional
public class PlatService {

    private final Logger log = LoggerFactory.getLogger(PlatService.class);

    private final PlatRepository platRepository;

    public PlatService(PlatRepository platRepository) {
        this.platRepository = platRepository;
    }

    /**
     * Save a plat.
     *
     * @param plat the entity to save
     * @return the persisted entity
     */
    public Plat save(Plat plat) {
        log.debug("Request to save Plat : {}", plat);
        return platRepository.save(plat);
    }

    /**
     * Get all the plats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Plat> findAll(Pageable pageable) {
        log.debug("Request to get all Plats");
        return platRepository.findAll(pageable);
    }

    /**
     * Get one plat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Plat findOne(Long id) {
        log.debug("Request to get Plat : {}", id);
        return platRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the plat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Plat : {}", id);
        platRepository.delete(id);
    }
}
