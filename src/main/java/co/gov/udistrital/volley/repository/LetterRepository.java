package co.gov.udistrital.volley.repository;

import co.gov.udistrital.volley.domain.Letter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Letter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

}
