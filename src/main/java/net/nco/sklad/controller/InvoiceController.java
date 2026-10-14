package net.nco.sklad.controller;

import net.nco.sklad.dto.InvoiceDTO;
import net.nco.sklad.entity.documents.Invoice;
import net.nco.sklad.repository.InvoiceMovementsRepo;
import net.nco.sklad.repository.InvoiceRepository;
import net.nco.sklad.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InvoiceController {

    private final InvoiceMovementsRepo invoiceMovementsRepo;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceService invoiceService;
    private String num;

    public InvoiceController(InvoiceRepository invoiceRepo, InvoiceMovementsRepo invoiceMovementsRepo, InvoiceService invoiceSrv) {
        this.invoiceRepository = invoiceRepo;
        this.invoiceMovementsRepo = invoiceMovementsRepo;
        this.invoiceService = invoiceSrv;
    }

    @GetMapping("/invoice/ajax/last-num")
    public ResponseEntity<String> lastNum() {
        num =  invoiceRepository.findTopByOrderByIdDesc().map(Invoice::getNumber).orElse("");
        if(!num.equals("")){
            return ResponseEntity.ok(Long.parseLong(num.split("/")[0]) + 1 + "/" + Long.parseLong(num.split("/")[1]));
        }else return ResponseEntity.ok(num);
    }

    @GetMapping("/invoice/{id}")
    public String getInvoiceDetails(@PathVariable long id, Model model) {
        model.addAttribute("invoice", invoiceRepository.findInvoiceDTOById(id));
        model.addAttribute("im_list", invoiceMovementsRepo.findInvoiceMovementsByInvoiceId(id));

        return  "docs/invoice_details";
    }

    @DeleteMapping("/invoice/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!invoiceRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        invoiceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invoice/search")
    public ResponseEntity<List<InvoiceDTO>> search(@RequestParam(name = "field_search_doc_number") String number,
                                                   @RequestParam(name = "field_sender") String sender,
                                                   @RequestParam(name = "field_receiver") String receiver) {

        return ResponseEntity.ok(invoiceService.search(number, sender, receiver));
    }


}//end class
