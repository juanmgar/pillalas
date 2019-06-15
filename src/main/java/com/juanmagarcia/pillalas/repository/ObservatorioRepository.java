package com.juanmagarcia.pillalas.repository;

import com.juanmagarcia.pillalas.domain.Observatorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Observatorio entity.
 */
@Repository
public interface ObservatorioRepository extends JpaRepository<Observatorio, Long> {

    @Query(value = "select distinct observatorio from Observatorio observatorio left join fetch observatorio.observatorios left join fetch observatorio.aves",
        countQuery = "select count(distinct observatorio) from Observatorio observatorio")
    Page<Observatorio> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct observatorio from Observatorio observatorio left join fetch observatorio.observatorios left join fetch observatorio.aves")
    List<Observatorio> findAllWithEagerRelationships();

    @Query("select observatorio from Observatorio observatorio left join fetch observatorio.observatorios left join fetch observatorio.aves where observatorio.id =:id")
    Optional<Observatorio> findOneWithEagerRelationships(@Param("id") Long id);

}
