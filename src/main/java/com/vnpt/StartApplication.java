package com.vnpt;

import com.vnpt.entities.Quantity;
import com.vnpt.entities.View;
import com.vnpt.repositories.ViewRepository;
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

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
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
}