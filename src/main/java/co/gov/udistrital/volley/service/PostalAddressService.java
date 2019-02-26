package co.gov.udistrital.volley.service;

import co.gov.udistrital.volley.domain.PostalAddress;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PostalAddress.
 */
public interface PostalAddressService {

    /**
     * Save a postalAddress.
     *
     * @param postalAddress the entity to save
     * @return the persisted entity
     */
    PostalAddress save(PostalAddress postalAddress);

    /**
     * Get all the postalAddresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PostalAddress> findAll(Pageable pageable);


    /**
     * Get the "id" postalAddress.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PostalAddress> findOne(Long id);

    /**
     * Delete the "id" postalAddress.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
