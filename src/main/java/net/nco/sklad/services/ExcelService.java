package net.nco.sklad.services;

import net.nco.sklad.dto.DenominationDTO;
import net.nco.sklad.repository.DenominationRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    private final DenominationRepository denominationRepo;

    public ExcelService(DenominationRepository denominationRepo) {
        this.denominationRepo = denominationRepo;
    }
    public byte[] generateExcelWorkBookForDenominations(String workSheetName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet ws = workbook.createSheet(workSheetName);
        Row row = ws.createRow(0);
            row.createCell(0).setCellValue("№");
            row.createCell(1).setCellValue("id");
            row.createCell(2).setCellValue("найменування");
            row.createCell(3).setCellValue("id");
            row.createCell(4).setCellValue("об'єкт");
        int rowNum = 1;
        for(DenominationDTO denomDTO : denominationRepo.findDenominationsByNameNative("")){
            row = ws.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(denomDTO.getId());
            row.createCell(2).setCellValue(denomDTO.getTitle());
            row.createCell(3).setCellValue(denomDTO.getOmID());
            row.createCell(4).setCellValue(denomDTO.getOmTitle());
        }
        ws.autoSizeColumn(0);
        ws.autoSizeColumn(1);
        ws.autoSizeColumn(2);
        ws.autoSizeColumn(3);
        ws.autoSizeColumn(4);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

}
