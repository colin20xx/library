package net.nco.sklad.services;

import jakarta.transaction.Transactional;
import net.nco.sklad.entity.documents.Card;
import net.nco.sklad.entity.documents.Invoice;
import net.nco.sklad.entity.documents.InvoiceMovement;
import net.nco.sklad.repository.CardRepository;
import net.nco.sklad.repository.InvoiceMovementsRepo;
import org.springframework.stereotype.Service;

@Service
public class InvoiceMovementService {
    private final InvoiceMovementsRepo invoiceMovementsRepo;
    private final CardRepository cardRepository;

    public InvoiceMovementService(InvoiceMovementsRepo invoiceMovementsRepo, CardRepository cardRepository) {
        this.invoiceMovementsRepo = invoiceMovementsRepo;
        this.cardRepository = cardRepository;
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


}//end class
