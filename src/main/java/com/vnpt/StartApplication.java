package com.vnpt;

import com.vnpt.entities.NhanVien;
import com.vnpt.entities.Quantity;
import com.vnpt.entities.View;
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

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //updateNhanvien();
    }

    private void updateView() {
        try {
            FileInputStream excelFile = new FileInputStream(new File(uploadingDir + "pt.xlsx"));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();
            int i = 0;
            while (iterator.hasNext() && i<= 6) {

                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null)
                    continue;
                View view = new View();
                view.setTrungTam(currentRow.getCell(0).getStringCellValue());
                view.setGoiCuoc(currentRow.getCell(6).getStringCellValue());
                viewRepository.save(view);
                i++;
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
                currentRow.getCell(1).setCellType(CellType.STRING);
                nhanVien.setNhanvienId(currentRow.getCell(1).getStringCellValue());
                nhanVien.setMaNV(currentRow.getCell(2).getStringCellValue());
                nhanVien.setTenNV(currentRow.getCell(3).getStringCellValue());
                currentRow.getCell(4).setCellType(CellType.STRING);
                nhanVien.setDonviId(currentRow.getCell(4).getStringCellValue());
                nhanVien.setMaDV(currentRow.getCell(5).getStringCellValue());
                nhanVien.setTenDV(currentRow.getCell(6).getStringCellValue());
                currentRow.getCell(7).setCellType(CellType.STRING);
                nhanVien.setDonviChaID(currentRow.getCell(7).getStringCellValue());
                nhanVien.setTenDVCha(currentRow.getCell(8).getStringCellValue());
                nhanVien.setMaND(currentRow.getCell(9).getStringCellValue());
                nhanVienRepository.save(nhanVien);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}