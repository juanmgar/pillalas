package com.juanmagarcia.pillalas.repository;

import com.juanmagarcia.pillalas.domain.Fotografia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fotografia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotografiaRepository extends JpaRepository<Fotografia, Long> {

}
