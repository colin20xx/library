package net.nco.sklad.controller;

import net.nco.sklad.entity.documents.DocumentsType;
import net.nco.sklad.entity.Documents;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class IndexController {

    @GetMapping("/")
    public String indexPage() {
        return "/index";
    }

    @GetMapping("/settings")
    public String settingsPage() {
        return "/settings";
    }

//    @GetMapping("/add_goods_form")
//    public String addGoodsForm(Model model) {
//
//        Documents documents = new Documents();
//        documents.setId(7);
//        documents.setNumber("5555/25");
//        documents.setDocDateTime(LocalDateTime.now());
//        documents.setDocDateTimeOperation(LocalDateTime.now());
//        documents.setCreatedAt(LocalDateTime.now());
//        DocumentsType documentType = new DocumentsType();
//        documentType.setId(1);
//        documentType.setTitle("my doc");
//        documents.setDocument(documentType);
//
//        model.addAttribute("new_doc_created", documents);
//        return "/docs/add_goods_form";
//    }
}
