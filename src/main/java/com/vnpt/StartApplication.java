package com.vnpt;

import com.vnpt.entities.NhanVien;
import com.vnpt.entities.Quantity;
import com.vnpt.entities.View;
import com.vnpt.entities.factor;
import com.vnpt.repositories.FactorRepository;
import com.vnpt.repositories.NhanVienRepository;
import com.vnpt.repositories.ViewRepository;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@SpringBootApplication
public class StartApplication  implements CommandLineRunner {
    public static final String uploadingDir = System.getProperty("user.dir") + "/uploadingDir/";
    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    @Autowired
    ViewRepository viewRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    FactorRepository factorRepository;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //updateNhanvien();
        //updateFactor();
        updateView();
    }

    private void updateView() {
        try {
            FileInputStream excelFile = new FileInputStream(new File(uploadingDir + "pt.xlsx"));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null || StringUtils.isEmpty(currentRow.getCell(0).getStringCellValue()))
                    continue;
                View view = new View();
                currentRow.getCell(0).setCellType(CellType.STRING);
                view.setMa_nvpt(currentRow.getCell(0).getStringCellValue());
                currentRow.getCell(1).setCellType(CellType.STRING);
                view.setDoanhthu(currentRow.getCell(1).getStringCellValue());
                currentRow.getCell(2).setCellType(CellType.STRING);
                view.setNgay_hm(currentRow.getCell(2).getStringCellValue());
                view.setDich_vu(currentRow.getCell(3) != null ? currentRow.getCell(3).getStringCellValue() : "");
                view.setGoi_cuoc(currentRow.getCell(4).getStringCellValue());
                currentRow.getCell(5).setCellType(CellType.STRING);
                view.setMa_tb(currentRow.getCell(5).getStringCellValue());
                view.setKhach_hang(currentRow.getCell(6).getStringCellValue());
                currentRow.getCell(7).setCellType(CellType.STRING);
                view.setMa_nvgt(currentRow.getCell(7).getStringCellValue());
                viewRepository.save(view);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateNhanvien() {
        try {
            FileInputStream excelFile = new FileInputStream(new File(uploadingDir + "tmp001.xlsx"));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null)
                    continue;
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMa_nv(currentRow.getCell(2).getStringCellValue());
                nhanVien.setTen_nv(currentRow.getCell(3).getStringCellValue());
                nhanVien.setTen_dv(currentRow.getCell(8).getStringCellValue());
                nhanVienRepository.save(nhanVien);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFactor() {
        try {
            FileInputStream excelFile = new FileInputStream(new File(uploadingDir + "factor.xlsx"));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null || currentRow.getCell(1) == null)
                    continue;
                factor factor = new factor();
                factor.setDich_vu(currentRow.getCell(0).getStringCellValue());
                currentRow.getCell(1).setCellType(CellType.STRING);
                factor.setHe_so(currentRow.getCell(1).getStringCellValue());
                factorRepository.save(factor);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}