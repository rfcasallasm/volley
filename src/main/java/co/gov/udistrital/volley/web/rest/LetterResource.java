package co.gov.udistrital.volley.web.rest;
import co.gov.udistrital.volley.domain.Letter;
import co.gov.udistrital.volley.service.LetterService;
import co.gov.udistrital.volley.web.rest.errors.BadRequestAlertException;
import co.gov.udistrital.volley.web.rest.util.HeaderUtil;
import co.gov.udistrital.volley.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Letter.
 */
@RestController
@RequestMapping("/api")
public class LetterResource {

    private final Logger log = LoggerFactory.getLogger(LetterResource.class);

    private static final String ENTITY_NAME = "letter";

    private final LetterService letterService;

    public LetterResource(LetterService letterService) {
        this.letterService = letterService;
    }

    /**
     * POST  /letters : Create a new letter.
     *
     * @param letter the letter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new letter, or with status 400 (Bad Request) if the letter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/letters")
    public ResponseEntity<Letter> createLetter(@Valid @RequestBody Letter letter) throws URISyntaxException {
        log.debug("REST request to save Letter : {}", letter);
        if (letter.getId() != null) {
            throw new BadRequestAlertException("A new letter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Letter result = letterService.save(letter);
        return ResponseEntity.created(new URI("/api/letters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /letters : Updates an existing letter.
     *
     * @param letter the letter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated letter,
     * or with status 400 (Bad Request) if the letter is not valid,
     * or with status 500 (Internal Server Error) if the letter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/letters")
    public ResponseEntity<Letter> updateLetter(@Valid @RequestBody Letter letter) throws URISyntaxException {
        log.debug("REST request to update Letter : {}", letter);
        if (letter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Letter result = letterService.save(letter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, letter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /letters : get all the letters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of letters in body
     */
    @GetMapping("/letters")
    public ResponseEntity<List<Letter>> getAllLetters(Pageable pageable) {
        log.debug("REST request to get a page of Letters");
        Page<Letter> page = letterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/letters");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /letters/:id : get the "id" letter.
     *
     * @param id the id of the letter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the letter, or with status 404 (Not Found)
     */
    @GetMapping("/letters/{id}")
    public ResponseEntity<Letter> getLetter(@PathVariable Long id) {
        log.debug("REST request to get Letter : {}", id);
        Optional<Letter> letter = letterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(letter);
    }

    /**
     * DELETE  /letters/:id : delete the "id" letter.
     *
     * @param id the id of the letter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/letters/{id}")
    public ResponseEntity<Void> deleteLetter(@PathVariable Long id) {
        log.debug("REST request to delete Letter : {}", id);
        letterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
