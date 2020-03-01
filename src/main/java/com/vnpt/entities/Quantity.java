package com.vnpt.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Quantity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String idUser;
    private Double sales;
    private String type;
    private String createdBy;

    @JsonProperty
    @Transient
    private String tenNV;
}
