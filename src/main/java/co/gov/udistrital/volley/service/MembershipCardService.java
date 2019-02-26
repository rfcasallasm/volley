package co.gov.udistrital.volley.service;

import co.gov.udistrital.volley.domain.MembershipCard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MembershipCard.
 */
public interface MembershipCardService {

    /**
     * Save a membershipCard.
     *
     * @param membershipCard the entity to save
     * @return the persisted entity
     */
    MembershipCard save(MembershipCard membershipCard);

    /**
     * Get all the membershipCards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MembershipCard> findAll(Pageable pageable);


    /**
     * Get the "id" membershipCard.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MembershipCard> findOne(Long id);

    /**
     * Delete the "id" membershipCard.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
