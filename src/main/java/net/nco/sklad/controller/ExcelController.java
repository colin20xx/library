package net.nco.sklad.controller;

import net.nco.sklad.services.ExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService xlsService) {
        this.excelService = xlsService;
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export() throws IOException {
        byte[] excel = excelService.generateExcelWorkBookForDenominations("найменування");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=denominations_" + LocalDateTime.now() + ".xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

}
