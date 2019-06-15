package com.juanmagarcia.pillalas.repository;

import com.juanmagarcia.pillalas.domain.Ave;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ave entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AveRepository extends JpaRepository<Ave, Long> {

}
