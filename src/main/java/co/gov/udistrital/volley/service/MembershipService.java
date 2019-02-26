package co.gov.udistrital.volley.service;

import co.gov.udistrital.volley.domain.Membership;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Membership.
 */
public interface MembershipService {

    /**
     * Save a membership.
     *
     * @param membership the entity to save
     * @return the persisted entity
     */
    Membership save(Membership membership);

    /**
     * Get all the memberships.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Membership> findAll(Pageable pageable);


    /**
     * Get the "id" membership.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Membership> findOne(Long id);

    /**
     * Delete the "id" membership.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
