package co.gov.udistrital.volley.service.impl;

import co.gov.udistrital.volley.service.MembershipService;
import co.gov.udistrital.volley.domain.Membership;
import co.gov.udistrital.volley.repository.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Membership.
 */
@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    private final Logger log = LoggerFactory.getLogger(MembershipServiceImpl.class);

    private final MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    /**
     * Save a membership.
     *
     * @param membership the entity to save
     * @return the persisted entity
     */
    @Override
    public Membership save(Membership membership) {
        log.debug("Request to save Membership : {}", membership);
        return membershipRepository.save(membership);
    }

    /**
     * Get all the memberships.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Membership> findAll(Pageable pageable) {
        log.debug("Request to get all Memberships");
        return membershipRepository.findAll(pageable);
    }


    /**
     * Get one membership by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Membership> findOne(Long id) {
        log.debug("Request to get Membership : {}", id);
        return membershipRepository.findById(id);
    }

    /**
     * Delete the membership by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Membership : {}", id);        membershipRepository.deleteById(id);
    }
}
