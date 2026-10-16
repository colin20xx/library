package net.nco.sklad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.nco.sklad.entity.documents.AktOfRefreshmentUnit;
import net.nco.sklad.repository.AktOfRefreshmentUnitRepository;
import net.nco.sklad.repository.DepartmentRepository;
import net.nco.sklad.repository.DocumentTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/aru")
public class AktOfRefreshmentUnitController {

    private final ObjectMapper objectMapper;
    private final AktOfRefreshmentUnitRepository aruRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final DepartmentRepository departmentRepository;
    private AktOfRefreshmentUnit tempARU;

    public AktOfRefreshmentUnitController(AktOfRefreshmentUnitRepository aruRepo, DocumentTypeRepository documentTypeRepository, ObjectMapper objM, DepartmentRepository departmentRepository) {
        this.aruRepository = aruRepo;
        this.documentTypeRepository = documentTypeRepository;
        this.objectMapper = objM;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping("/new-aru-form-popup")
    public String newAruFormPopup(Model model) throws JsonProcessingException {
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(departmentRepository.findAllDepartmentsWithSubDepartments());
        model.addAttribute("dep_list_json", json);
        model.addAttribute("dep_list", departmentRepository.findAllDepartmentsWithSubDepartments());

        tempARU = new AktOfRefreshmentUnit();
        tempARU.setDocumentsType(documentTypeRepository.findById(15));
        model.addAttribute("temp_aru", tempARU);
        return "fragments/fr-modal-new-aru :: div-modal-new-aru";
    }

    @GetMapping("/list")
    public String aruList(Model model) {
        model.addAttribute("documentTypes", documentTypeRepository.findAll());
        model.addAttribute("aruList", aruRepository.findAll());
        return "aru/aru-list";
    }

    @PostMapping("/save_new")
    public String saveNewAru(Model model, AktOfRefreshmentUnit aru) {
        aru.setCreatedAt(LocalDateTime.now());
        aruRepository.save(aru);
        return "redirect:/aru/list";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(!aruRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        aruRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}//end class
