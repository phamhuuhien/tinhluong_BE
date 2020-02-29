package com.vnpt.services;

import com.vnpt.entities.Quantity;
import com.vnpt.repositories.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityService {

    @Autowired
    QuantityRepository bookRepository;

    public List<Quantity> findAll() {
        return bookRepository.findAll();
    }

    public Quantity save(Quantity quantity) {
        return bookRepository.save(quantity);
    }
}
