package net.nco.sklad.controller;

import jakarta.persistence.EntityManager;
import net.nco.sklad.dto.ATCUnitDTO;
import net.nco.sklad.entity.Denomination;
import net.nco.sklad.entity.Nomenclature;
import net.nco.sklad.entity.documents.AktTechCondition;
import net.nco.sklad.entity.documents.AktTechConditionUnits;
import net.nco.sklad.entity.documents.Card;
import net.nco.sklad.repository.ATCUnitsRepository;
import net.nco.sklad.repository.AktTechConditionRepository;
import net.nco.sklad.repository.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atc/units")
public class AtcUnitsController {

    private final AktTechConditionRepository aktTechConditionRepository;
    private final ATCUnitsRepository atcUnitsRepository;
    private final CardRepository cardRepository;
    private AktTechCondition tempAtc;
    private AktTechConditionUnits tempAtcUnit;
    private final EntityManager em;
    private Card tempCard;

    public AtcUnitsController(ATCUnitsRepository atcUnitsRepo, AktTechConditionRepository aktTechConditionRepo, EntityManager emng, CardRepository cardRepo) {
        this.atcUnitsRepository = atcUnitsRepo;
        this.aktTechConditionRepository = aktTechConditionRepo;
        this.cardRepository = cardRepo;
        this.em = emng;
    }

    @PostMapping("/add-single-unit")
    public ResponseEntity<Long> addSingleUnit(@RequestBody ATCUnitDTO atcUnits) {
        tempAtcUnit = new AktTechConditionUnits();
        tempAtcUnit.setCategory(atcUnits.getCategory());
        tempAtcUnit.setQuantity(atcUnits.getQuantity());
        tempAtcUnit.setPrice(atcUnits.getPrice());
        tempAtcUnit.setDenomination(em.getReference(Denomination.class, atcUnits.getDenominationID()));
        if(atcUnits.getNomenclatureID() !=null) tempAtcUnit.setNomenclature(em.getReference(Nomenclature.class, atcUnits.getNomenclatureID()));

        tempAtcUnit.setAtc(aktTechConditionRepository.getReferenceById(atcUnits.getAtcID()));

        tempCard = cardRepository.getReferenceById(atcUnits.getCardID());
        tempAtcUnit.addCard(tempCard);

//        if(tempInvoice.getSender().getIdDep() == 71 || tempInvoice.getReceiver().getIdDep() == 71){ //PS
//            card.setQtyPS(card.getQtyPS() + invoiceMov.getQuantity().doubleValue());
//        }else if(tempInvoice.getSender().getIdDep() == 72 || tempInvoice.getReceiver().getIdDep() == 72){//BTS
//            card.setQtyBTS(card.getQtyBTS() + invoiceMov.getQuantity().doubleValue());
//        }
        atcUnitsRepository.save(tempAtcUnit);
        return ResponseEntity.ok(tempAtcUnit.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSingleUnit(@PathVariable Long id) {
        atcUnitsRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}//end class
