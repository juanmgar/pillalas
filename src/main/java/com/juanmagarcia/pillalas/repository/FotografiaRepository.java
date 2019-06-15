package com.juanmagarcia.pillalas.repository;

import com.juanmagarcia.pillalas.domain.Fotografia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Fotografia entity.
 */
@Repository
public interface FotografiaRepository extends JpaRepository<Fotografia, Long> {

    @Query("select fotografia from Fotografia fotografia where fotografia.autor.login = ?#{principal.username}")
    List<Fotografia> findByAutorIsCurrentUser();

    @Query(value = "select distinct fotografia from Fotografia fotografia left join fetch fotografia.aves",
        countQuery = "select count(distinct fotografia) from Fotografia fotografia")
    Page<Fotografia> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct fotografia from Fotografia fotografia left join fetch fotografia.aves")
    List<Fotografia> findAllWithEagerRelationships();

    @Query("select fotografia from Fotografia fotografia left join fetch fotografia.aves where fotografia.id =:id")
    Optional<Fotografia> findOneWithEagerRelationships(@Param("id") Long id);

}
