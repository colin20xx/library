package net.nco.sklad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.nco.sklad.common.CommitAction;
import net.nco.sklad.dto.AktTechConditionDTO;
import net.nco.sklad.dto.DepsWithSubDepsDTO;
import net.nco.sklad.entity.AppPDF;
import net.nco.sklad.entity.DepSubdepPair;
import net.nco.sklad.entity.Department;
import net.nco.sklad.entity.documents.AktTechCondition;
import net.nco.sklad.entity.history.DocumentCommitHistory;
import net.nco.sklad.repository.*;
import net.nco.sklad.services.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/atc")
public class AktTechConditionController {

    private final FileStorageService fileStorageService;
    @Value("${app.pagination.test}")
    private int PAGINATION_TEXT;

    private final DocumentCommitHistoryRepository documentCommitHistoryRepository;
    private final ObjectMapper objectMapper;
    private final AktTechConditionRepository aktTechConditionRepository;
    private final DepartmentRepository departmentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final DenominationRepository denominationRepository;
    private final ATCUnitsRepository atcUnitsRepository;
    private AktTechCondition tempATC;
    private DepsWithSubDepsDTO depsWithSubDepsDTO;

    public AktTechConditionController(AktTechConditionRepository aktTechConditionRepo, DepartmentRepository departmentRepo, DocumentTypeRepository documentTypeRepository, ObjectMapper objMapper, DenominationRepository denominationRepository, ATCUnitsRepository atcUnitsRepo, DocumentCommitHistoryRepository documentCommitHistoryRepository, FileStorageService fileStorageService) {
        this.aktTechConditionRepository = aktTechConditionRepo;
        this.departmentRepository = departmentRepo;
        this.documentTypeRepository = documentTypeRepository;
        this.objectMapper = objMapper;
        this.denominationRepository = denominationRepository;
        this.atcUnitsRepository = atcUnitsRepo;
        this.documentCommitHistoryRepository = documentCommitHistoryRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/list")
    public String list(@PageableDefault(size = 10) Pageable pageable, Model model) {
//        model.addAttribute("atc_list", aktTechConditionRepository.findAll());
        Page<AktTechConditionDTO> atcPages = aktTechConditionRepository.findAllWithTitles(pageable);

        model.addAttribute("startPage", Math.max(0,atcPages.getNumber()-2));
        model.addAttribute("endPage", Math.min(atcPages.getTotalPages()-1, atcPages.getNumber()+2));

        model.addAttribute("atc_list", atcPages);
        model.addAttribute("new_atc", new AktTechCondition());

        model.addAttribute("totalPages", atcPages.getTotalPages());
        model.addAttribute("totalElements", atcPages.getTotalElements());
        return "atc/atc-list";
    }//end method

    @GetMapping("/list-from-fragment")
    public String listByFragment(Pageable pageable, Model model) {
        Page<AktTechConditionDTO> atcPages = aktTechConditionRepository.findAllWithTitles(pageable);

        model.addAttribute("atc_list", atcPages);
        model.addAttribute("startPage", Math.max(0,atcPages.getNumber()-2));
        model.addAttribute("endPage", Math.min(atcPages.getTotalPages()-1, atcPages.getNumber()+2));

        return "fragments/fragment-pagination :: atc-pagination";
    }//end method


    @PostMapping("/new-atc-form-popup")
    public String newAtcFormPopup(Model model) throws JsonProcessingException {
        tempATC = new AktTechCondition();
        tempATC.setDocumentsType(documentTypeRepository.findIdByTitle("Акт технічного стану"));
//        tempATC.setNumber("1/26");
        model.addAttribute("new_atc", tempATC);

        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(departmentRepository.findAllDepartmentsWithSubDepartments());
        model.addAttribute("dep_list_json", json);
        model.addAttribute("dep_list", departmentRepository.findAllDepartmentsWithSubDepartments());

        //model.addAttribute("id_doc_type", documentTypeRepository.findIdByTitle("Акт технічного стану").getId());
        return "fragments/fragment-modal :: div-modal-new-atc";
    }

    @PostMapping("/save")
    public String save(AktTechCondition newAtc,
                       @RequestParam(name = "sender_ids") String senderIDS,
                       @RequestParam(name = "receiver_ids") String receiverIDS,
                       Model model) {
        newAtc.setCreatedAt(LocalDateTime.now());
        newAtc.setSender(new DepSubdepPair(Integer.parseInt(senderIDS.split("_")[0]),Integer.parseInt(senderIDS.split("_")[1])));
        newAtc.setReceiver(new DepSubdepPair(Integer.parseInt(receiverIDS.split("_")[0]),Integer.parseInt(receiverIDS.split("_")[1])));
        
        tempATC = aktTechConditionRepository.save(newAtc);

        DocumentCommitHistory tempDocumentCommitHistory = new DocumentCommitHistory();
        tempDocumentCommitHistory.setDocumentType(tempATC.getDocumentsType());
        tempDocumentCommitHistory.setDocumentId(tempATC.getId());
        tempDocumentCommitHistory.setRecordDateTime(LocalDateTime.now());
        tempDocumentCommitHistory.setComment("документ створено");
        tempDocumentCommitHistory.setCommitAction(CommitAction.create);
        documentCommitHistoryRepository.save(tempDocumentCommitHistory);
        
        return "redirect:/atc/" + tempATC.getId();
    }

    @GetMapping("/{id}")
    public String atc(Model model, @PathVariable Long id) {
        tempATC = aktTechConditionRepository.findById(id).get();
        System.out.println(tempATC.getDocumentsType().getId());

        if(tempATC.getSender().getIdSubdep() > 0){
            depsWithSubDepsDTO = departmentRepository.findDepartmentWithSubDepartmentById(tempATC.getSender().getIdDep(), tempATC.getSender().getIdSubdep());
            model.addAttribute("sender_obj", depsWithSubDepsDTO.getDepsShortName() + " " + depsWithSubDepsDTO.getSubDepsShortName());
        } else{
            model.addAttribute("sender_obj", departmentRepository.findById(tempATC.getSender().getIdDep()).map(Department:: getShortName).orElse(""));
        }

        if(tempATC.getReceiver().getIdSubdep() > 0){
            depsWithSubDepsDTO = departmentRepository.findDepartmentWithSubDepartmentById(tempATC.getReceiver().getIdDep(), tempATC.getReceiver().getIdSubdep());
            model.addAttribute("receiver_obj", depsWithSubDepsDTO.getDepsShortName() + " " + depsWithSubDepsDTO.getSubDepsShortName());
        } else{
            model.addAttribute("receiver_obj", departmentRepository.findById(tempATC.getReceiver().getIdDep()).map(Department :: getShortName).orElse(""));
        }

        model.addAttribute("atc", tempATC);

        Optional<AppPDF> firstPDF = fileStorageService.getFirstPDF(tempATC.getId(), tempATC.getDocumentsType(), true);
        firstPDF.ifPresent(pdf -> model.addAttribute("firstPDF", pdf));

        model.addAttribute("denomination_list", denominationRepository.getDenominationsWithOMandNomenclaturesCount(""));
        model.addAttribute("moves_list", atcUnitsRepository.findAllByAtcID(id));
        model.addAttribute("totalSum", atcUnitsRepository.sumUnitsPrice(id));
        model.addAttribute("totalQuantity", atcUnitsRepository.sumUnitsQuantity(id));
        model.addAttribute("history_list", documentCommitHistoryRepository.findByDocumentIdAndDocumentTypeOrderByRecordDateTimeDesc(tempATC.getId(), tempATC.getDocumentsType()));
        model.addAttribute("commit_actions", CommitAction.values());
        return "atc/details-01";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!aktTechConditionRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        aktTechConditionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}//end class
