package com.vnpt.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String trungTam;
    private String goiCuoc;

}
