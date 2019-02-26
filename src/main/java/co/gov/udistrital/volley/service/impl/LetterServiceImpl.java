package co.gov.udistrital.volley.service.impl;

import co.gov.udistrital.volley.service.LetterService;
import co.gov.udistrital.volley.domain.Letter;
import co.gov.udistrital.volley.repository.LetterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Letter.
 */
@Service
@Transactional
public class LetterServiceImpl implements LetterService {

    private final Logger log = LoggerFactory.getLogger(LetterServiceImpl.class);

    private final LetterRepository letterRepository;

    public LetterServiceImpl(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    /**
     * Save a letter.
     *
     * @param letter the entity to save
     * @return the persisted entity
     */
    @Override
    public Letter save(Letter letter) {
        log.debug("Request to save Letter : {}", letter);
        return letterRepository.save(letter);
    }

    /**
     * Get all the letters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Letter> findAll(Pageable pageable) {
        log.debug("Request to get all Letters");
        return letterRepository.findAll(pageable);
    }


    /**
     * Get one letter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Letter> findOne(Long id) {
        log.debug("Request to get Letter : {}", id);
        return letterRepository.findById(id);
    }

    /**
     * Delete the letter by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Letter : {}", id);        letterRepository.deleteById(id);
    }
}
