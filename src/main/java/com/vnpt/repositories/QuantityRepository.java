package com.vnpt.repositories;

import com.vnpt.entities.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {

    Long deleteByCreatedBy(String createdBy);

    List<Quantity> findByCreatedBy(String createdBy);
}
