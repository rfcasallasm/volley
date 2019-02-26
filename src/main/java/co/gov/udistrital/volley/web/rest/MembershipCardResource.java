package co.gov.udistrital.volley.web.rest;
import co.gov.udistrital.volley.domain.MembershipCard;
import co.gov.udistrital.volley.service.MembershipCardService;
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
 * REST controller for managing MembershipCard.
 */
@RestController
@RequestMapping("/api")
public class MembershipCardResource {

    private final Logger log = LoggerFactory.getLogger(MembershipCardResource.class);

    private static final String ENTITY_NAME = "membershipCard";

    private final MembershipCardService membershipCardService;

    public MembershipCardResource(MembershipCardService membershipCardService) {
        this.membershipCardService = membershipCardService;
    }

    /**
     * POST  /membership-cards : Create a new membershipCard.
     *
     * @param membershipCard the membershipCard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membershipCard, or with status 400 (Bad Request) if the membershipCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/membership-cards")
    public ResponseEntity<MembershipCard> createMembershipCard(@Valid @RequestBody MembershipCard membershipCard) throws URISyntaxException {
        log.debug("REST request to save MembershipCard : {}", membershipCard);
        if (membershipCard.getId() != null) {
            throw new BadRequestAlertException("A new membershipCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembershipCard result = membershipCardService.save(membershipCard);
        return ResponseEntity.created(new URI("/api/membership-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /membership-cards : Updates an existing membershipCard.
     *
     * @param membershipCard the membershipCard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membershipCard,
     * or with status 400 (Bad Request) if the membershipCard is not valid,
     * or with status 500 (Internal Server Error) if the membershipCard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/membership-cards")
    public ResponseEntity<MembershipCard> updateMembershipCard(@Valid @RequestBody MembershipCard membershipCard) throws URISyntaxException {
        log.debug("REST request to update MembershipCard : {}", membershipCard);
        if (membershipCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MembershipCard result = membershipCardService.save(membershipCard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, membershipCard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /membership-cards : get all the membershipCards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of membershipCards in body
     */
    @GetMapping("/membership-cards")
    public ResponseEntity<List<MembershipCard>> getAllMembershipCards(Pageable pageable) {
        log.debug("REST request to get a page of MembershipCards");
        Page<MembershipCard> page = membershipCardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/membership-cards");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /membership-cards/:id : get the "id" membershipCard.
     *
     * @param id the id of the membershipCard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membershipCard, or with status 404 (Not Found)
     */
    @GetMapping("/membership-cards/{id}")
    public ResponseEntity<MembershipCard> getMembershipCard(@PathVariable Long id) {
        log.debug("REST request to get MembershipCard : {}", id);
        Optional<MembershipCard> membershipCard = membershipCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membershipCard);
    }

    /**
     * DELETE  /membership-cards/:id : delete the "id" membershipCard.
     *
     * @param id the id of the membershipCard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/membership-cards/{id}")
    public ResponseEntity<Void> deleteMembershipCard(@PathVariable Long id) {
        log.debug("REST request to delete MembershipCard : {}", id);
        membershipCardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
