package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Plat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlatRepository extends JpaRepository<Plat, Long>, JpaSpecificationExecutor<Plat> {

    @Query("select plat from Plat plat where plat.createur.login = ?#{principal.username}")
    List<Plat> findByCreateurIsCurrentUser();
    @Query("select distinct plat from Plat plat left join fetch plat.tags")
    List<Plat> findAllWithEagerRelationships();

    @Query("select plat from Plat plat left join fetch plat.tags where plat.id =:id")
    Plat findOneWithEagerRelationships(@Param("id") Long id);

}
