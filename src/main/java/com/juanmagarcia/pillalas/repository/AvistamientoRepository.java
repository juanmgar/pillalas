package com.juanmagarcia.pillalas.repository;

import com.juanmagarcia.pillalas.domain.Avistamiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Avistamiento entity.
 */
@Repository
public interface AvistamientoRepository extends JpaRepository<Avistamiento, Long> {

    @Query("select avistamiento from Avistamiento avistamiento where avistamiento.autor.login = ?#{principal.username}")
    List<Avistamiento> findByAutorIsCurrentUser();

    @Query(value = "select distinct avistamiento from Avistamiento avistamiento left join fetch avistamiento.aves",
        countQuery = "select count(distinct avistamiento) from Avistamiento avistamiento")
    Page<Avistamiento> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct avistamiento from Avistamiento avistamiento left join fetch avistamiento.aves")
    List<Avistamiento> findAllWithEagerRelationships();

    @Query("select avistamiento from Avistamiento avistamiento left join fetch avistamiento.aves where avistamiento.id =:id")
    Optional<Avistamiento> findOneWithEagerRelationships(@Param("id") Long id);

}
