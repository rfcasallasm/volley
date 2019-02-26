package co.gov.udistrital.volley.service.impl;

import co.gov.udistrital.volley.service.PostalAddressService;
import co.gov.udistrital.volley.domain.PostalAddress;
import co.gov.udistrital.volley.repository.PostalAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PostalAddress.
 */
@Service
@Transactional
public class PostalAddressServiceImpl implements PostalAddressService {

    private final Logger log = LoggerFactory.getLogger(PostalAddressServiceImpl.class);

    private final PostalAddressRepository postalAddressRepository;

    public PostalAddressServiceImpl(PostalAddressRepository postalAddressRepository) {
        this.postalAddressRepository = postalAddressRepository;
    }

    /**
     * Save a postalAddress.
     *
     * @param postalAddress the entity to save
     * @return the persisted entity
     */
    @Override
    public PostalAddress save(PostalAddress postalAddress) {
        log.debug("Request to save PostalAddress : {}", postalAddress);
        return postalAddressRepository.save(postalAddress);
    }

    /**
     * Get all the postalAddresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PostalAddress> findAll(Pageable pageable) {
        log.debug("Request to get all PostalAddresses");
        return postalAddressRepository.findAll(pageable);
    }


    /**
     * Get one postalAddress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PostalAddress> findOne(Long id) {
        log.debug("Request to get PostalAddress : {}", id);
        return postalAddressRepository.findById(id);
    }

    /**
     * Delete the postalAddress by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostalAddress : {}", id);        postalAddressRepository.deleteById(id);
    }
}
