package co.gov.udistrital.volley.service;

import co.gov.udistrital.volley.domain.Letter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Letter.
 */
public interface LetterService {

    /**
     * Save a letter.
     *
     * @param letter the entity to save
     * @return the persisted entity
     */
    Letter save(Letter letter);

    /**
     * Get all the letters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Letter> findAll(Pageable pageable);


    /**
     * Get the "id" letter.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Letter> findOne(Long id);

    /**
     * Delete the "id" letter.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
