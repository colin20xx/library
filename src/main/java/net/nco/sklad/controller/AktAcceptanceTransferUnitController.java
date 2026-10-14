package net.nco.sklad.controller;

import net.nco.sklad.entity.documents.Card;
import net.nco.sklad.repository.AktAcceptanceTransferUnitsRepository;
import net.nco.sklad.repository.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/aatu")
public class AktAcceptanceTransferUnitController {

    private final AktAcceptanceTransferUnitsRepository aatuRepository;
    private final CardRepository cardRepository;

    public AktAcceptanceTransferUnitController(AktAcceptanceTransferUnitsRepository aatuRepo, CardRepository cardRepository) {
        this.aatuRepository = aatuRepo;
        this.cardRepository = cardRepository;
    }

    @DeleteMapping("/ajax/delete/{id}")
    public ResponseEntity<String> deleteAktAcceptanceTransferUnits(@PathVariable Long id) {
//        Card card = cardRepository.getReferenceById(aatuRepository.getReferenceById(id).getId());

        aatuRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
