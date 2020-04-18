package com.msb.demo.repository;

import com.msb.demo.domain.Exchange;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Exchange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}
