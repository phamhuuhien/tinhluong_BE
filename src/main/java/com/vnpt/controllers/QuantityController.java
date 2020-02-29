package com.vnpt.controllers;

import com.vnpt.entities.Quantity;
import com.vnpt.services.QuantityService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/books")
public class QuantityController {

    public static final String uploadingDir = System.getProperty("user.dir") + "/uploadingDir/";

    @Autowired
    QuantityService quantityService;

    @GetMapping("/")
    public List<Quantity> getAll() {
        return quantityService.findAll();
    }

    @PostMapping("/upload")
    public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(uploadingDir + fileName);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        readExcelFile(fileName);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName)
                .toUriString();
        return ResponseEntity.ok(fileDownloadUri);
    }

    private void readExcelFile(String fileName) {
        try {

            FileInputStream excelFile = new FileInputStream(new File(uploadingDir + fileName));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null)
                    continue;
                Quantity quantity = new Quantity();
                quantity.setIdUser(currentRow.getCell(0).getStringCellValue());
                quantity.setSales(currentRow.getCell(3).getNumericCellValue());
                quantity.setType(currentRow.getCell(4).getStringCellValue());
                quantityService.save(quantity);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
