package net.nco.sklad.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.nco.sklad.dto.AATUnitDTO;
import net.nco.sklad.dto.AktAcceptTransferDTO;
import net.nco.sklad.dto.DepsWithSubDepsDTO;
import net.nco.sklad.entity.AppPDF;
import net.nco.sklad.entity.DepSubdepPair;
import net.nco.sklad.entity.Department;
import net.nco.sklad.entity.documents.AktAcceptanceTransfer;
import net.nco.sklad.entity.documents.AktAcceptanceTransferUnits;
import net.nco.sklad.entity.documents.Card;
import net.nco.sklad.repository.*;
import net.nco.sklad.services.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/akt-accept-transfer")
public class AktAcceptanceTransferController {

    private final AktAcceptanceTransferRepository aktAcceptanceTransferRepository;
    private final DepartmentRepository departmentRepository;
    private final AktAcceptanceTransferUnitsRepository aktAcceptanceTransferUnitsRepository;
    private final DenominationRepository denominationRepository;
    private final CardRepository cardRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final FileStorageService fileStorageService;
    private final NomenclatureRepository nomenclatureRepository;
    private AktAcceptanceTransferUnits aatu;
    private AktAcceptanceTransfer tempAPP;
    private DepsWithSubDepsDTO depsWithSubDepsDTO;
    private Card card;

    public AktAcceptanceTransferController(AktAcceptanceTransferRepository aktAcceptanceTransferRepo, DepartmentRepository departmentRepo, AktAcceptanceTransferUnitsRepository aktAcceptanceTransferUnitsRepo, DenominationRepository denominationRepository, CardRepository cardRepository, DocumentTypeRepository documentTypeRepository, FileStorageService fileStorageSrv, NomenclatureRepository nomenclatureRepository) {
        this.aktAcceptanceTransferRepository = aktAcceptanceTransferRepo;
        this.departmentRepository = departmentRepo;
        this.aktAcceptanceTransferUnitsRepository = aktAcceptanceTransferUnitsRepo;
        this.denominationRepository = denominationRepository;
        this.cardRepository = cardRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.fileStorageService = fileStorageSrv;
        this.nomenclatureRepository = nomenclatureRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        tempAPP = new AktAcceptanceTransfer();
        tempAPP.setDocumentsType(documentTypeRepository.findIdByTitle("Акт приймання-передачі"));

        model.addAttribute("new_app", tempAPP);
        model.addAttribute("dep_list", departmentRepository.findAllDepartmentsWithSubDepartments());
        model.addAttribute("app_list", aktAcceptanceTransferRepository.findAllforList());
        return "docs/act-pp-list";
    }

    @GetMapping("/new_app_form")
    public String newAPPForm(Model model) {
        model.addAttribute("new_akt_at", new AktAcceptanceTransfer());
        model.addAttribute("dep_list", departmentRepository.findAllDepartmentsWithSubDepartments());
        return "docs/new_app_form";
    }

    @PostMapping("/save_new")
    public String saveNew(AktAcceptanceTransfer aktAcceptanceTransfer,
                          @RequestParam(name = "sender_ids") String senderIDS,
                          @RequestParam(name = "receiver_ids") String receiverIDS,
                          Model model) {
        aktAcceptanceTransfer.setSender(new DepSubdepPair(Integer.parseInt(senderIDS.split("_")[0]),Integer.parseInt(senderIDS.split("_")[1])));
        aktAcceptanceTransfer.setReceiver(new DepSubdepPair(Integer.parseInt(receiverIDS.split("_")[0]),Integer.parseInt(receiverIDS.split("_")[1])));
        aktAcceptanceTransfer.setCreatedAt(LocalDateTime.now());
        aktAcceptanceTransfer.setExpiresAt(LocalDateTime.now());

        aktAcceptanceTransferRepository.save(aktAcceptanceTransfer);

        return "redirect:/akt-accept-transfer/"+ aktAcceptanceTransfer.getId();
    }

    @GetMapping("/{id}")
    public String viewAppForm(@PathVariable Long id, Model model) {
        tempAPP = aktAcceptanceTransferRepository.getReferenceById(id);

        if(tempAPP.getSender().getIdSubdep() > 0){
            depsWithSubDepsDTO = departmentRepository.findDepartmentWithSubDepartmentById(tempAPP.getSender().getIdDep(), tempAPP.getSender().getIdSubdep());
            model.addAttribute("sender_obj", depsWithSubDepsDTO.getDepsShortName() + " " + depsWithSubDepsDTO.getSubDepsShortName());
        } else{
            model.addAttribute("sender_obj", departmentRepository.findById(tempAPP.getSender().getIdDep()).map(Department :: getShortName).orElse(""));
        }

        if(tempAPP.getReceiver().getIdSubdep() > 0){
            depsWithSubDepsDTO = departmentRepository.findDepartmentWithSubDepartmentById(tempAPP.getReceiver().getIdDep(), tempAPP.getReceiver().getIdSubdep());
            model.addAttribute("receiver_obj", depsWithSubDepsDTO.getDepsShortName() + " " + depsWithSubDepsDTO.getSubDepsShortName());
        } else{
            model.addAttribute("receiver_obj", departmentRepository.findById(tempAPP.getReceiver().getIdDep()).map(Department :: getShortName).orElse(""));
        }


        model.addAttribute("app", tempAPP);
//        model.addAttribute("moves_list", aktAcceptanceTransferUnitsRepository.findByAktAcceptanceTransferIdOrderByIdDesc(id));
        model.addAttribute("moves_list", aktAcceptanceTransferUnitsRepository.findAllByAktAcceptanceTransferId(id));

        Optional<AppPDF> firstPDF = fileStorageService.getFirstPDF(tempAPP.getId(), tempAPP.getDocumentsType(), true);
        firstPDF.ifPresent(pdf -> model.addAttribute("firstPDF", pdf));

        return "docs/act_pp";
    }

    @PostMapping("/{id}/getUnitsList")
    public ResponseEntity<List<AATUnitDTO>> getUnitsList(@PathVariable Long id) {
        return ResponseEntity.ok(
                aktAcceptanceTransferUnitsRepository
                        .findAllByAktAcceptanceTransferId(id)
        );
    }

    @PostMapping("/ajax/addUnitToApp")
    public ResponseEntity<String> addUnitToAPP(@RequestBody AATUnitDTO unitDTO){
        System.out.println("--------------->" + getClass().toString());
        tempAPP = aktAcceptanceTransferRepository.getReferenceById(unitDTO.getIdApp());
        aatu = new AktAcceptanceTransferUnits();
        aatu.setAktAcceptanceTransfer(tempAPP);
        aatu.setCategory(unitDTO.getCategory());
        aatu.setDenomination(denominationRepository.getReferenceById(unitDTO.getIdDenomination().intValue()));
        aatu.setNomenclature( unitDTO.getIdNomenclature() != null ? nomenclatureRepository.getReferenceById(unitDTO.getIdNomenclature()) : null);
        //aatu.setQuantity(unitDTO.getQuantity());

        card = cardRepository.getReferenceById(unitDTO.getCardId());

        switch (tempAPP.getReceiver().getIdDep()){
            case 71: //PS
                aatu.setQuantity(unitDTO.getQuantity());
                break;
            case 72: //bts
                aatu.setQuantity(unitDTO.getQuantity());
                break;

            default: System.out.println("wrong warehouse id!");
        }
        switch (tempAPP.getSender().getIdDep()){
            case 71: //PS
                aatu.setQuantity(unitDTO.getQuantity().negate());
                break;
            case 72: //bts
                aatu.setQuantity(unitDTO.getQuantity().negate());
                break;

            default: System.out.println("wrong warehouse id!");
        }


        if(tempAPP.getSender().getIdDep() == 71 || tempAPP.getReceiver().getIdDep() == 71){                     //PS
            card.setQtyPS(card.getQtyPS() + aatu.getQuantity().doubleValue());

        }else if(tempAPP.getSender().getIdDep() == 72 || tempAPP.getReceiver().getIdDep() == 72){               //BTS
            card.setQtyBTS(card.getQtyBTS() + aatu.getQuantity().doubleValue());
        }

        aatu.addCard(card);
        aatu = aktAcceptanceTransferUnitsRepository.save(aatu);

        return ResponseEntity.ok("ok");
    }//end method

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!aktAcceptanceTransferRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        aktAcceptanceTransferRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @ResponseBody
    public List<AktAcceptTransferDTO> search(@RequestParam(name = "field_search_app_number", required = false) String number,
                                             @RequestParam(name = "field_search_app_sender",required = false) String sender,
                                             @RequestParam(name = "field_search_app_receiver", required = false) String receiver){
        return aktAcceptanceTransferRepository.findAllBySearchParams(emptyToNull(number),emptyToNull(sender),emptyToNull(receiver)) ;
    }

    private String emptyToNull(String str){
        return str == null || str.isBlank() ? null : str.trim();
    }

}//end class
