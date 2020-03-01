package com.vnpt.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nhanvienId;
    private String maNV;
    private String tenNV;
    private String donviId;
    private String maDV;
    private String tenDV;
    private String donviChaID;
    private String tenDVCha;
    private String maND;
}
