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

    private String ma_nvpt;
    private String doanhthu;
    private String ngay_hm;
    private String dich_vu;
    private String goi_cuoc;
    private String ma_tb;
    private String khach_hang;
    private String ma_nvgt;

}
