package com.fruktkorgservice.common.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FruktRepository extends JpaRepository<Frukt, Long> {
    Optional<Frukt> findFruktByid(Long id);
}
