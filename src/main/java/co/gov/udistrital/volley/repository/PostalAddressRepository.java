package co.gov.udistrital.volley.repository;

import co.gov.udistrital.volley.domain.PostalAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PostalAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostalAddressRepository extends JpaRepository<PostalAddress, Long> {

}
