package net.nco.sklad.controller;

import net.nco.sklad.common.CardStatus;
import net.nco.sklad.entity.Department;
import net.nco.sklad.entity.documents.Card;
import net.nco.sklad.entity.documents.Invoice;
import net.nco.sklad.entity.documents.InvoiceMovement;
import net.nco.sklad.repository.*;
import net.nco.sklad.services.InvoiceMovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class InvoiceMovementsController {

    private final CardRepository cardRepository;
    private final DenominationRepository denominationRepository;
    private final NomenclatureRepository nomenclatureRepository;
    private final DepartmentRepository departmentRepository;
    private InvoiceMovementsRepo invoiceMovementsRepository;
    private InvoiceRepository invoiceRepository;
    private final InvoiceMovementService invoiceMovementService;
    private InvoiceMovement im;
    private Card card;
    private Invoice tempInvoice;
    private Department tempDepSender, tempDepReceiver;

    public InvoiceMovementsController (InvoiceMovementsRepo invoiceMovementsRepo, InvoiceRepository invoiceRepo, InvoiceMovementService invoiceMovementService, CardRepository cardRepos, DenominationRepository denominationRepo, NomenclatureRepository nomenclatureRepo, DepartmentRepository departmentRepository) {
        this.invoiceMovementsRepository = invoiceMovementsRepo;
        this.invoiceRepository = invoiceRepo;
        this.invoiceMovementService = invoiceMovementService;
        this.cardRepository = cardRepos;
        this.denominationRepository = denominationRepo;
        this.nomenclatureRepository = nomenclatureRepo;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping("/invmov/ajax/save_single_movement")
    public ResponseEntity<String> saveSingleMovement(@RequestBody InvoiceMovement invoiceMov)
//                                                        ,, @RequestParam("invoice_id") Long idInvoice
//                                                        @RequestParam("denom_id") int idDenom,
//                                                        @RequestParam("nomencl_id") Long idNomenclature)
    {
        tempInvoice = invoiceRepository.getReferenceById(invoiceMov.getInvoice().getId());

        if(invoiceMov.getIdCard() == null){ //new card create

            card = new Card();
            card.setCardNumber(invoiceMov.getCardNumber());
            card.setCreatedAt(LocalDateTime.now());
            card.setCardStatus(CardStatus.NEW.getValue());
            card.setPrice(invoiceMov.getPrice());
            card.setCategory(invoiceMov.getCategory());

            tempDepSender = departmentRepository.getReferenceById(tempInvoice.getSender().getIdDep());
            tempDepReceiver = departmentRepository.getReferenceById(tempInvoice.getReceiver().getIdDep());

            System.out.println(tempDepSender.getShortName() + ">" +tempDepReceiver.getShortName());

            if(tempDepSender.getShortName().equals("ПС") || tempDepReceiver.getShortName().equals("ПС")){ //PS
                card.setQtyPS(invoiceMov.getQuantity().doubleValue());
                card.setQtyBTS(0.00);
            }else if(tempDepSender.getShortName().equals("БТС") || tempDepReceiver.getShortName().equals("БТС")){//BTS
                card.setQtyBTS(invoiceMov.getQuantity().doubleValue());
                card.setQtyPS(0.00);
            }

            card.setTotalQty(invoiceMov.getQuantity().doubleValue());

            card.setTotalSum(BigDecimal.valueOf(card.getTotalQty() * card.getPrice().doubleValue()));

            card.setQtyDEPS(invoiceMov.getQuantity().doubleValue()*(-1));

            card.setCardDateTime(LocalDateTime.now());
            card.setDenomination(invoiceMov.getDenomination());
            card.setNomenclature(invoiceMov.getNomenclature());
            //cardRepository.save(card);

            //invoiceMov.addCard(card);
            //invoiceMov.setCreatedAt(LocalDateTime.now());

        }else { //card exists
            //TODO if card exist, why setting denomination and nomenclature?
//            invoiceMov.setDenomination(denominationRepository.getReferenceById(invoiceMov.getIdDemonination()));
//            System.out.println("-----------------------> " + invoiceMov.getIdNomenclature());
//            invoiceMov.setNomenclature(nomenclatureRepository.getReferenceById(invoiceMov.getIdNomenclature()));
            invoiceMov.setCreatedAt(LocalDateTime.now());

            card = cardRepository.getReferenceById(invoiceMov.getIdCard());

            if(tempInvoice.getSender().getIdDep() == 71 || tempInvoice.getReceiver().getIdDep() == 71){ //PS
                card.setQtyPS(card.getQtyPS() + invoiceMov.getQuantity().doubleValue());
            }else if(tempInvoice.getSender().getIdDep() == 72 || tempInvoice.getReceiver().getIdDep() == 72){//BTS
                card.setQtyBTS(card.getQtyBTS() + invoiceMov.getQuantity().doubleValue());
            }

            invoiceMov.setInvoice(tempInvoice);
            //invoiceMov.addCard(card);
        }
        invoiceMovementsRepository.save(invoiceMov);
        return ResponseEntity.ok("ok");
    }//end method


}//end class
