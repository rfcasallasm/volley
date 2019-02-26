package co.gov.udistrital.volley.service.impl;

import co.gov.udistrital.volley.service.MembershipCardService;
import co.gov.udistrital.volley.domain.MembershipCard;
import co.gov.udistrital.volley.repository.MembershipCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MembershipCard.
 */
@Service
@Transactional
public class MembershipCardServiceImpl implements MembershipCardService {

    private final Logger log = LoggerFactory.getLogger(MembershipCardServiceImpl.class);

    private final MembershipCardRepository membershipCardRepository;

    public MembershipCardServiceImpl(MembershipCardRepository membershipCardRepository) {
        this.membershipCardRepository = membershipCardRepository;
    }

    /**
     * Save a membershipCard.
     *
     * @param membershipCard the entity to save
     * @return the persisted entity
     */
    @Override
    public MembershipCard save(MembershipCard membershipCard) {
        log.debug("Request to save MembershipCard : {}", membershipCard);
        return membershipCardRepository.save(membershipCard);
    }

    /**
     * Get all the membershipCards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MembershipCard> findAll(Pageable pageable) {
        log.debug("Request to get all MembershipCards");
        return membershipCardRepository.findAll(pageable);
    }


    /**
     * Get one membershipCard by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MembershipCard> findOne(Long id) {
        log.debug("Request to get MembershipCard : {}", id);
        return membershipCardRepository.findById(id);
    }

    /**
     * Delete the membershipCard by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MembershipCard : {}", id);        membershipCardRepository.deleteById(id);
    }
}
