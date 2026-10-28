package net.nco.sklad.services;

import jakarta.transaction.Transactional;
import net.nco.sklad.entity.Unit;
import net.nco.sklad.entity.documents.Card;
import net.nco.sklad.entity.documents.Invoice;
import net.nco.sklad.entity.documents.InvoiceMovement;
import net.nco.sklad.repository.CardRepository;
import net.nco.sklad.repository.InvoiceMovementsRepo;
import net.nco.sklad.repository.InvoiceRepository;
import net.nco.sklad.repository.UnitRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceMovementService {
    private final InvoiceMovementsRepo invoiceMovementsRepo;
    private final InvoiceRepository invoiceRepo;
    private final CardRepository cardRepository;
    private final UnitRepository unitRepo;

    public InvoiceMovementService(InvoiceMovementsRepo invoiceMovementsRepo, CardRepository cardRepository, InvoiceRepository invoiceRepo, UnitRepository unitRepo) {
        this.invoiceMovementsRepo = invoiceMovementsRepo;
        this.cardRepository = cardRepository;
        this.invoiceRepo = invoiceRepo;
        this.unitRepo = unitRepo;
    }

    @Transactional
    public void link(Long idCard, Long idInvMov) {
        Card card = cardRepository.findById(idCard).orElse(null);

        InvoiceMovement im = invoiceMovementsRepo.findById(idInvMov).orElse(null);

//        im.getCards().add(card);
//        card.getInvoiceMovement().add(im);
        assert im != null;
        invoiceMovementsRepo.save(im);
    }

    @Transactional
    public void addNumericUnitToInvoiceMovement(Long unitId, Long invoiceId) {
        InvoiceMovement im = new InvoiceMovement();
        im.setInvoice(invoiceRepo.findById(invoiceId).orElse(null));
        im.setUnit(unitRepo.findById(unitId).orElse(null));
        invoiceMovementsRepo.save(im);
    }


}//end class
