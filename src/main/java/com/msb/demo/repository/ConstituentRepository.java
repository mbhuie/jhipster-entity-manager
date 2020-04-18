package com.msb.demo.repository;

import com.msb.demo.domain.Constituent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Constituent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConstituentRepository extends JpaRepository<Constituent, Long> {
}
