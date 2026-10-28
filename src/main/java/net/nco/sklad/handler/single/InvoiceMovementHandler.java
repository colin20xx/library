package net.nco.sklad.handler.single;

import net.nco.sklad.handler.DocumentUnitHandler;
import org.springframework.stereotype.Service;

@Service
public class InvoiceMovementHandler implements DocumentUnitHandler {


    public InvoiceMovementHandler() {
    }

    @Override
    public String getDocumentTitle() {
        return "invoice";
    }

    @Override
    public void addUnit(Long documentId, Long unitId) {
        System.out.println("[sklad]---> add unit to app units with app id:("+ documentId +") and unit id:" + unitId);
    }
}
