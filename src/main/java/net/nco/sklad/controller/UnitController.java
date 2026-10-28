package net.nco.sklad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.nco.sklad.entity.Nomenclature;
import net.nco.sklad.entity.Unit;
import net.nco.sklad.handler.DocumentUnitService;
import net.nco.sklad.repository.DenominationRepository;
import net.nco.sklad.repository.UnitRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/unit")
public class UnitController {

//    private final ObjectMapper objectMapper;
    private final UnitRepository unitRepository;
    private final DenominationRepository denominationRepository;
    private Unit newUnit;
    private DocumentUnitService docUnitService;

    public UnitController(UnitRepository unitRepo, DenominationRepository denominationRepository, DocumentUnitService documentUnitService) {
        this.unitRepository = unitRepo;
        this.denominationRepository = denominationRepository;
        this.docUnitService = documentUnitService;
//        this.objectMapper = objectMapper;
    }

    @PostMapping("/new-unit-form-popup")
    public String newAtcFormPopup(Model model) throws JsonProcessingException {
        newUnit = new Unit();
        model.addAttribute("new_unit", newUnit);

//        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(denominationRepository.getDenominationsWithOMandNomenclaturesCount(""));
//        model.addAttribute("denomination_list_json", json);

        model.addAttribute("denominations", denominationRepository.findDenominationsByNameNative(""));
        return "fragments/fr-modal-new-unit :: div-modal-new-unit";
    }

    @PostMapping("/save")
    public String save(Unit unit) {
        unit.setCreatedAt(LocalDateTime.now());
        unitRepository.save(unit);
        return "redirect:/unit/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        newUnit = new Unit();
        model.addAttribute("units_list", unitRepository.findAllDTO());
        model.addAttribute("new_unit", newUnit);
        return "unit/units-list";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!unitRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        unitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public String unitDetails(@PathVariable Long id, Model model) {
        Unit unit = unitRepository.findById(id).orElse(null);
        if(unit != null){
            model.addAttribute("unit", unit);
            return "unit/unit-details";
        }else {
            //TODO error.page
            return "redirect:/unit/list";
        }
    }

    @PostMapping("/form-add-numeric-unit")
    public String formAddNumericUnit(Model model) {
        model.addAttribute("units-list", unitRepository.findAll());
        return "fragments/fr-modal-add-numeric-unit-form :: modalDivAddNumericUnits";
    }

    @GetMapping("/search-by-serial-number")
    @ResponseBody
    public List<Unit> searchBySerialNumber(@RequestParam String serialNumber) {
        return unitRepository.findAllBySerialNumberContainingIgnoreCase(serialNumber);
    }

    @PostMapping("/add-unit-to-document")
    public ResponseEntity<String> addUnitToDocument(@RequestParam Long unitId){

        System.out.println("[sklad]--->" + getClass().toString() + ":addUnitToDocument() > " + unitId);
        docUnitService.addUnit("app", 1L ,unitId);
        return ResponseEntity.ok("");
    }

}//end class
