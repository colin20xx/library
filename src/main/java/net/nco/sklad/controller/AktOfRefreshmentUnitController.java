package net.nco.sklad.controller;

import net.nco.sklad.entity.documents.AktOfRefreshmentUnit;
import net.nco.sklad.repository.AktOfRefreshmentUnitRepository;
import net.nco.sklad.repository.DocumentTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aru")
public class AktOfRefreshmentUnitController {

    private final AktOfRefreshmentUnitRepository aruRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private AktOfRefreshmentUnit tempARU;

    public AktOfRefreshmentUnitController(AktOfRefreshmentUnitRepository aruRepo, DocumentTypeRepository documentTypeRepository) {
        this.aruRepository = aruRepo;
        this.documentTypeRepository = documentTypeRepository;
    }

    @GetMapping("/new-aru-form-popup")
    public String newAruFormPopup(Model model) {
        tempARU = new AktOfRefreshmentUnit();
        tempARU.setDocumentsType(documentTypeRepository.findById(15));
        model.addAttribute("aru", tempARU);
        return "";
    }

    @GetMapping("/list")
    public String aruList(Model model) {
        model.addAttribute("documentTypes", documentTypeRepository.findAll());
        model.addAttribute("aruList", aruRepository.findAll());
        return "aru/aru-list";
    }
}
