package co.gov.udistrital.volley.repository;

import co.gov.udistrital.volley.domain.MembershipCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MembershipCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipCardRepository extends JpaRepository<MembershipCard, Long> {

}
