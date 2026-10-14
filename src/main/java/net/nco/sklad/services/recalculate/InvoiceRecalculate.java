package net.nco.sklad.services.recalculate;

import net.nco.sklad.entity.documents.Invoice;
import net.nco.sklad.services.DocumentRecalculate;
import org.springframework.stereotype.Component;

@Component
public class InvoiceRecalculate implements DocumentRecalculate<Invoice> {
    @Override
    public Class<Invoice> getSupportedClass() {
        return Invoice.class;
    }

    @Override
    public void recalculate(Invoice document, boolean isCommittable) {
        System.out.println("Recalculating Invoice: " + document.getId());
    }
}
