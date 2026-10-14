package net.nco.sklad.controller;

import net.nco.sklad.dto.DenominationDTO;
import net.nco.sklad.dto.DenominationRecordDTO;
import net.nco.sklad.entity.Denomination;
import net.nco.sklad.repository.DenominationRepository;
import net.nco.sklad.repository.NomenclatureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class DenominationController {

    private NomenclatureRepository nomenclatureRepository;
    private DenominationRepository denominationRepository;

    public DenominationController(NomenclatureRepository nomenclatureRepo, DenominationRepository denominationRepo) {
        this.nomenclatureRepository = nomenclatureRepo;
        this.denominationRepository = denominationRepo;
    }

    @GetMapping("/denom-and-nomenclature-list")
    public ResponseEntity<?> denominationList(Model model, @RequestParam String denomTitle) {
        List<Object> myList = nomenclatureRepository.findAllNomenclaturesByDenomination(denomTitle);

        return ResponseEntity.ok(myList);
    }

    @GetMapping("/denomination-list")
    public ResponseEntity<List<Object>> denominationsList(Model model, @RequestParam String denomTitle) {
//            return ResponseEntity.ok(denominationRepository.findDenominationsByNameNative(denomTitle)) ;
            return ResponseEntity.ok(Collections.singletonList(denominationRepository.getDenominationsWithOMandNomenclaturesCount(denomTitle))) ;
    }

    @DeleteMapping("/denomination/delete/{id}")
    public ResponseEntity<String> deleteDenomination(@PathVariable("id") int id) {
        try {
            denominationRepository.deleteById(id);
            return ResponseEntity.ok("ok");
        }catch (Exception e) {
            System.out.println("--[" + e.getLocalizedMessage() + "]--");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("denomination delete error:" + e.getMessage());
        }

    }

    private DenominationRecordDTO getDenominationRecordDTO(Denomination denomination) {
        return new DenominationRecordDTO(
                denomination.getId(),
                denomination.getDenominationName(),
                denomination.getParentObjMil().getObjectName()
        );
    }

    //TODO make the method: control if the new denomination already exist
}
