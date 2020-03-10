package com.vnpt.controllers;

import com.vnpt.entities.NhanVien;
import com.vnpt.entities.Quantity;
import com.vnpt.repositories.NhanVienRepository;
import com.vnpt.repositories.QuantityRepository;
import com.vnpt.services.QuantityService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/quantities")
public class QuantityController {

    public static final String uploadingDir = System.getProperty("user.dir") + "/uploadingDir/";

    @Autowired
    QuantityService quantityService;

    @Autowired
    QuantityRepository quantityRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @GetMapping("/{createdBy}")
    public List<Quantity> getAll(@PathVariable String createdBy) {
        Map<String, String> nhanviens = nhanVienRepository.findAll().stream().collect( Collectors.toMap(NhanVien::getMaNV, NhanVien::getTenNV, (value1, value2) -> value1));
        List<Quantity> result = quantityRepository.findByCreatedBy(createdBy);
        result.stream().forEach(it -> it.setTenNV(nhanviens.get(it.getIdUser())));
        return result;
    }

    @Transactional
    @PostMapping("/upload")
    public ResponseEntity uploadToLocalFileSystem(@RequestParam String createdBy, @RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(uploadingDir + fileName);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        readExcelFile(fileName, createdBy);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName)
                .toUriString();
        return ResponseEntity.ok(fileDownloadUri);
    }

    private void readExcelFile(String fileName, String createdBy) {
        quantityRepository.deleteByCreatedBy(createdBy);
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
                quantity.setCreatedBy(createdBy);
                quantityService.save(quantity);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
