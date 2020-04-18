package com.msb.demo.repository;

import com.msb.demo.domain.IndexETF;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IndexETF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndexETFRepository extends JpaRepository<IndexETF, Long> {
}
