package co.gov.udistrital.volley.repository;

import co.gov.udistrital.volley.domain.LetterBook;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LetterBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LetterBookRepository extends JpaRepository<LetterBook, Long> {

}
