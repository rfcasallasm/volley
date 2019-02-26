package co.gov.udistrital.volley.web.rest;
import co.gov.udistrital.volley.domain.PostalAddress;
import co.gov.udistrital.volley.service.PostalAddressService;
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
 * REST controller for managing PostalAddress.
 */
@RestController
@RequestMapping("/api")
public class PostalAddressResource {

    private final Logger log = LoggerFactory.getLogger(PostalAddressResource.class);

    private static final String ENTITY_NAME = "postalAddress";

    private final PostalAddressService postalAddressService;

    public PostalAddressResource(PostalAddressService postalAddressService) {
        this.postalAddressService = postalAddressService;
    }

    /**
     * POST  /postal-addresses : Create a new postalAddress.
     *
     * @param postalAddress the postalAddress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new postalAddress, or with status 400 (Bad Request) if the postalAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/postal-addresses")
    public ResponseEntity<PostalAddress> createPostalAddress(@Valid @RequestBody PostalAddress postalAddress) throws URISyntaxException {
        log.debug("REST request to save PostalAddress : {}", postalAddress);
        if (postalAddress.getId() != null) {
            throw new BadRequestAlertException("A new postalAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostalAddress result = postalAddressService.save(postalAddress);
        return ResponseEntity.created(new URI("/api/postal-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /postal-addresses : Updates an existing postalAddress.
     *
     * @param postalAddress the postalAddress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated postalAddress,
     * or with status 400 (Bad Request) if the postalAddress is not valid,
     * or with status 500 (Internal Server Error) if the postalAddress couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/postal-addresses")
    public ResponseEntity<PostalAddress> updatePostalAddress(@Valid @RequestBody PostalAddress postalAddress) throws URISyntaxException {
        log.debug("REST request to update PostalAddress : {}", postalAddress);
        if (postalAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostalAddress result = postalAddressService.save(postalAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, postalAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /postal-addresses : get all the postalAddresses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of postalAddresses in body
     */
    @GetMapping("/postal-addresses")
    public ResponseEntity<List<PostalAddress>> getAllPostalAddresses(Pageable pageable) {
        log.debug("REST request to get a page of PostalAddresses");
        Page<PostalAddress> page = postalAddressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/postal-addresses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /postal-addresses/:id : get the "id" postalAddress.
     *
     * @param id the id of the postalAddress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the postalAddress, or with status 404 (Not Found)
     */
    @GetMapping("/postal-addresses/{id}")
    public ResponseEntity<PostalAddress> getPostalAddress(@PathVariable Long id) {
        log.debug("REST request to get PostalAddress : {}", id);
        Optional<PostalAddress> postalAddress = postalAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postalAddress);
    }

    /**
     * DELETE  /postal-addresses/:id : delete the "id" postalAddress.
     *
     * @param id the id of the postalAddress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/postal-addresses/{id}")
    public ResponseEntity<Void> deletePostalAddress(@PathVariable Long id) {
        log.debug("REST request to delete PostalAddress : {}", id);
        postalAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
