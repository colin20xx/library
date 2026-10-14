package net.nco.sklad.controller;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

@Controller
public class FESController {




    @GetMapping("/fes_common")
    public String fesCommon(){

//        final String SAMPLE_XLSX_FILE_PATH = "fes.xlsx";
//        Workbook wb;
//
//        try{
//            wb  = WorkbookFactory.create(new FileInputStream(SAMPLE_XLSX_FILE_PATH));
//            System.out.println("Workbook has " + wb.getNumberOfSheets() + " Sheets : ");
//
//            Sheet sheet = wb.getSheetAt(0);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return "/fes_common";
    }
}
