package net.nco.sklad.services;

import net.nco.sklad.dto.InvoiceDTO;
import net.nco.sklad.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository repo;

    public InvoiceService(InvoiceRepository repo) {
        this.repo = repo;
    }

    /**
     *
     * @param number
     * @param sender
     * @param receiver
     * @return List<InvoiceDTO>
     */
    public List<InvoiceDTO> search(String number, String sender, String receiver) {
        return repo.findAllInvoiceDTO(number, sender, receiver);
    }

}//end class
