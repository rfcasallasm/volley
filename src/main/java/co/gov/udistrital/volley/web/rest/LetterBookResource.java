package co.gov.udistrital.volley.web.rest;
import co.gov.udistrital.volley.domain.LetterBook;
import co.gov.udistrital.volley.service.LetterBookService;
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
 * REST controller for managing LetterBook.
 */
@RestController
@RequestMapping("/api")
public class LetterBookResource {

    private final Logger log = LoggerFactory.getLogger(LetterBookResource.class);

    private static final String ENTITY_NAME = "letterBook";

    private final LetterBookService letterBookService;

    public LetterBookResource(LetterBookService letterBookService) {
        this.letterBookService = letterBookService;
    }

    /**
     * POST  /letter-books : Create a new letterBook.
     *
     * @param letterBook the letterBook to create
     * @return the ResponseEntity with status 201 (Created) and with body the new letterBook, or with status 400 (Bad Request) if the letterBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/letter-books")
    public ResponseEntity<LetterBook> createLetterBook(@Valid @RequestBody LetterBook letterBook) throws URISyntaxException {
        log.debug("REST request to save LetterBook : {}", letterBook);
        if (letterBook.getId() != null) {
            throw new BadRequestAlertException("A new letterBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LetterBook result = letterBookService.save(letterBook);
        return ResponseEntity.created(new URI("/api/letter-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /letter-books : Updates an existing letterBook.
     *
     * @param letterBook the letterBook to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated letterBook,
     * or with status 400 (Bad Request) if the letterBook is not valid,
     * or with status 500 (Internal Server Error) if the letterBook couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/letter-books")
    public ResponseEntity<LetterBook> updateLetterBook(@Valid @RequestBody LetterBook letterBook) throws URISyntaxException {
        log.debug("REST request to update LetterBook : {}", letterBook);
        if (letterBook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LetterBook result = letterBookService.save(letterBook);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, letterBook.getId().toString()))
            .body(result);
    }

    /**
     * GET  /letter-books : get all the letterBooks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of letterBooks in body
     */
    @GetMapping("/letter-books")
    public ResponseEntity<List<LetterBook>> getAllLetterBooks(Pageable pageable) {
        log.debug("REST request to get a page of LetterBooks");
        Page<LetterBook> page = letterBookService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/letter-books");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /letter-books/:id : get the "id" letterBook.
     *
     * @param id the id of the letterBook to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the letterBook, or with status 404 (Not Found)
     */
    @GetMapping("/letter-books/{id}")
    public ResponseEntity<LetterBook> getLetterBook(@PathVariable Long id) {
        log.debug("REST request to get LetterBook : {}", id);
        Optional<LetterBook> letterBook = letterBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(letterBook);
    }

    /**
     * DELETE  /letter-books/:id : delete the "id" letterBook.
     *
     * @param id the id of the letterBook to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/letter-books/{id}")
    public ResponseEntity<Void> deleteLetterBook(@PathVariable Long id) {
        log.debug("REST request to delete LetterBook : {}", id);
        letterBookService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
