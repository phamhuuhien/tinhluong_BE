package com.vnpt.repositories;

import com.vnpt.entities.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {

}
