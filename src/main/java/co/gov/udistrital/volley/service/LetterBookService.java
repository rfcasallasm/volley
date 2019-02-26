package co.gov.udistrital.volley.service;

import co.gov.udistrital.volley.domain.LetterBook;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LetterBook.
 */
public interface LetterBookService {

    /**
     * Save a letterBook.
     *
     * @param letterBook the entity to save
     * @return the persisted entity
     */
    LetterBook save(LetterBook letterBook);

    /**
     * Get all the letterBooks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LetterBook> findAll(Pageable pageable);


    /**
     * Get the "id" letterBook.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LetterBook> findOne(Long id);

    /**
     * Delete the "id" letterBook.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
