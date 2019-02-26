package co.gov.udistrital.volley.service.impl;

import co.gov.udistrital.volley.service.LetterBookService;
import co.gov.udistrital.volley.domain.LetterBook;
import co.gov.udistrital.volley.repository.LetterBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing LetterBook.
 */
@Service
@Transactional
public class LetterBookServiceImpl implements LetterBookService {

    private final Logger log = LoggerFactory.getLogger(LetterBookServiceImpl.class);

    private final LetterBookRepository letterBookRepository;

    public LetterBookServiceImpl(LetterBookRepository letterBookRepository) {
        this.letterBookRepository = letterBookRepository;
    }

    /**
     * Save a letterBook.
     *
     * @param letterBook the entity to save
     * @return the persisted entity
     */
    @Override
    public LetterBook save(LetterBook letterBook) {
        log.debug("Request to save LetterBook : {}", letterBook);
        return letterBookRepository.save(letterBook);
    }

    /**
     * Get all the letterBooks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LetterBook> findAll(Pageable pageable) {
        log.debug("Request to get all LetterBooks");
        return letterBookRepository.findAll(pageable);
    }


    /**
     * Get one letterBook by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LetterBook> findOne(Long id) {
        log.debug("Request to get LetterBook : {}", id);
        return letterBookRepository.findById(id);
    }

    /**
     * Delete the letterBook by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LetterBook : {}", id);        letterBookRepository.deleteById(id);
    }
}
