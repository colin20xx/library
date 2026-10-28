package net.nco.sklad.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import net.nco.sklad.dto.CardDTO;
import net.nco.sklad.dto.NomenclatureDTO;
import net.nco.sklad.entity.Denomination;
import net.nco.sklad.entity.Nomenclature;
import net.nco.sklad.repository.CardRepository;
import net.nco.sklad.repository.DenominationRepository;
import net.nco.sklad.repository.NomenclatureRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NomenclatureController {
    private final CardRepository cardRepository;
    private NomenclatureRepository nomenclatureRepository;
    private DenominationRepository denominationRepository;

    public NomenclatureController(NomenclatureRepository nomenclRepository, DenominationRepository denomRepository, CardRepository cardRepository) {
        this.nomenclatureRepository = nomenclRepository;
        this.denominationRepository = denomRepository;
        this.cardRepository = cardRepository;
    }

    @GetMapping("/getNomenclatureListByIdDenomination")
    public ResponseEntity<ArrayList<NomenclatureDTO>> getNomenclatureListByIdDenomination(@RequestParam Long idDenom){
        //Denomination denomTemp = denominationRepository.getReferenceById(idDenom);
//        ArrayList<NomenclatureDTO> arrayList = nomenclatureRepository.findAllNomenclaturesByDenominationId(idDenom);
        return ResponseEntity.ok(nomenclatureRepository.findAllNomenclaturesByDenominationId(idDenom));
    }

    @GetMapping("nomenclatures_list")
    public ResponseEntity<List<NomenclatureDTO>> getNomenclatureList(@RequestParam Long idDenom){
        System.out.println("id denom: " + idDenom);
        return ResponseEntity.ok(nomenclatureRepository.findAllNomenclaturesByDenominationId(idDenom));

    }

    @GetMapping("/settings/nomenclatures")
    public String settingsNomenclatures(Model model) {

        model.addAttribute("denom_list", denominationRepository.findAll());
        model.addAttribute("new_nomenclature", new Nomenclature());
        model.addAttribute("nomencl_list", nomenclatureRepository.findAll());
        return "/settings_nomenclatures";
    }

    @PostMapping("/settings/nomenclatures/save_new_nomenclature")
    public String saveNewNomenclature(@ModelAttribute Nomenclature nomenclature, @RequestParam(name = "denom_id") int idDenom){
        System.out.println(nomenclature.getNomenclatureName());
        nomenclature.setDenomination(denominationRepository.findById(idDenom).get());
        nomenclatureRepository.save(nomenclature);
        return "redirect:/settings/nomenclatures";
    }
}//class end
