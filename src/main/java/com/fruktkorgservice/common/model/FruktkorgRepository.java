package com.fruktkorgservice.common.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FruktkorgRepository extends JpaRepository<Fruktkorg, Long> {
    Optional<Fruktkorg> findFruktkorgByid(Long id);

    @Query(value = "SELECT fk.* FROM fruktkorg fk JOIN frukt f on fk.fruktkorg_id = f.fruktkorg_id WHERE f.type = :type", nativeQuery = true)
    List<Fruktkorg> findFruktkorgarByFruktType(@Param("type") String type);
}
