package com.mycompany.myapp.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Plat;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.PlatRepository;
import com.mycompany.myapp.service.dto.PlatCriteria;

import com.mycompany.myapp.domain.enumeration.TypePlat;

/**
 * Service for executing complex queries for Plat entities in the database.
 * The main input is a {@link PlatCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Plat} or a {@link Page} of {@link Plat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlatQueryService extends QueryService<Plat> {

    private final Logger log = LoggerFactory.getLogger(PlatQueryService.class);


    private final PlatRepository platRepository;

    public PlatQueryService(PlatRepository platRepository) {
        this.platRepository = platRepository;
    }

    /**
     * Return a {@link List} of {@link Plat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Plat> findByCriteria(PlatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Plat> specification = createSpecification(criteria);
        return platRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Plat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Plat> findByCriteria(PlatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Plat> specification = createSpecification(criteria);
        return platRepository.findAll(specification, page);
    }

    /**
     * Function to convert PlatCriteria to a {@link Specifications}
     */
    private Specifications<Plat> createSpecification(PlatCriteria criteria) {
        Specifications<Plat> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Plat_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Plat_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Plat_.type));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Plat_.description));
            }
            if (criteria.getCreateurId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCreateurId(), Plat_.createur, User_.id));
            }
            if (criteria.getTagsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTagsId(), Plat_.tags, Tag_.id));
            }
        }
        return specification;
    }

}
