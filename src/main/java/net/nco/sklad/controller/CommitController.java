package net.nco.sklad.controller;

import jakarta.persistence.EntityManager;
import net.nco.sklad.entity.documents.DocumentsType;
import net.nco.sklad.repository.CommitRepository;
import net.nco.sklad.repository.DocumentTypeRepository;
import net.nco.sklad.services.CommitService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.DocumentType;

import java.text.DateFormat;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/commit")
public class CommitController {

    private final DocumentTypeRepository documentTypeRepository;
    private final CommitService commitService;

    public CommitController(DocumentTypeRepository documentTypeRepository, CommitService commitService) {
        this.documentTypeRepository = documentTypeRepository;
        this.commitService = commitService;
    }

    @PostMapping("/doc-type")
    public String patchDocType(@RequestParam int docType,
                               Long docId,
                               @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime commitDate,
                               String redirectDocPath,
                               @RequestParam(required = false) boolean unCommit)
    {
        DocumentsType documentType = documentTypeRepository.findById(docType); //.orElseThrow(() -> new RuntimeException("document eng_name not found"));

        if (!unCommit){ //провести документ
//            commitService.commit();
            commitService.setCommitedAtByDocIdNative(documentType.getEnglishTitle(), docId, commitDate );
        } else {
            commitService.setUnCommitedAtByIdNative(documentType.getEnglishTitle(), docId);
        }

        return "redirect:" + redirectDocPath + docId;
    }


}//end class
