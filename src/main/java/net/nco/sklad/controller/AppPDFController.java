package net.nco.sklad.controller;

import net.nco.sklad.entity.AppPDF;
import net.nco.sklad.entity.documents.AktAcceptanceTransfer;
import net.nco.sklad.entity.documents.DocumentsType;
import net.nco.sklad.repository.AppPDFRepository;
import net.nco.sklad.repository.DocumentTypeRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/uploads")
public class AppPDFController {

    private final AppPDFRepository appPDFRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private AppPDF tempAppPDF;


    public AppPDFController(AppPDFRepository appPDFRepo, DocumentTypeRepository documentTypeRepository) {
        this.appPDFRepository = appPDFRepo;
        this.documentTypeRepository = documentTypeRepository;
    }

    @PostMapping("/ajax/uploadPDF")
    public ResponseEntity<String> uploadPDF(@ModelAttribute AppPDF app,
                                            @RequestParam("file-name01") MultipartFile file,
                                            @RequestParam("first_pdf") boolean firstPDF) throws IOException {
        String uploadDir = "uploads/app";

        Files.createDirectories(Paths.get(uploadDir));

        String fileName = UUID.randomUUID().toString();

        Path path = Paths.get(uploadDir, fileName);

        Files.copy(file.getInputStream(), path);

        app.setCreatedAt(LocalDateTime.now());
        app.setFileName(fileName);
        app.setFileType(file.getContentType());
        app.setFilePath(path.toString());
        app.setFirstPDF(firstPDF);

        appPDFRepository.save(app);
        return ResponseEntity.ok(app.getId().toString());
    }//end method

    @DeleteMapping("/ajax/{id}")
    public ResponseEntity<String> deletePDF(@PathVariable Long id) {
        tempAppPDF = appPDFRepository.getReferenceById(id);
        Path path = Paths.get(tempAppPDF.getFilePath());
        try{
            Files.deleteIfExists(path);
            appPDFRepository.deleteById(id);
            return ResponseEntity.ok("deleted");
        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("помилка видалення:" + e.getMessage());
        }
    }//end method

    @GetMapping("{id}/download")
    public ResponseEntity<Resource> downloadPDF(@PathVariable Long id) {
        tempAppPDF = appPDFRepository.getReferenceById(id);
        Path path = Paths.get(tempAppPDF.getFilePath());
        Resource resource = new FileSystemResource(path);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tempAppPDF.getFileName() + ".pdf" + "\"")
                .body(resource);
    }

    @GetMapping("/getFragmentPdfForm")
    public String getFragmentPdfForm(Model model, @RequestParam(name = "doc-id") Long docId, @RequestParam int documentType, @RequestParam boolean firstPdf, @RequestParam(required = false) Long firstPdfId) {
        model.addAttribute("appPdf", firstPdf ? appPDFRepository.getReferenceById(firstPdfId) : null);
        tempAppPDF = new AppPDF();
        tempAppPDF.setDocumentId(docId);
        tempAppPDF.setDocumentType(documentTypeRepository.findById(documentType));
        model.addAttribute("tempAppPDF", tempAppPDF);
        model.addAttribute("docId", docId);
        model.addAttribute("documentType", documentType);
        return "fragments/fr-modal-attach-files :: fragmentPdfForm";
    }

}//end class
