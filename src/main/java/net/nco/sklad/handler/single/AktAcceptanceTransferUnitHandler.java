package net.nco.sklad.handler.single;

import jakarta.transaction.Transactional;
import net.nco.sklad.handler.DocumentUnitHandler;
import net.nco.sklad.repository.AktAcceptanceTransferRepository;
import net.nco.sklad.repository.AktAcceptanceTransferUnitsRepository;
import org.springframework.stereotype.Service;

@Service
public class AktAcceptanceTransferUnitHandler implements DocumentUnitHandler {

    private final AktAcceptanceTransferRepository appRepo;
    private final AktAcceptanceTransferUnitsRepository appUnitsRepo;

    public AktAcceptanceTransferUnitHandler(AktAcceptanceTransferRepository appRepo, AktAcceptanceTransferUnitsRepository appUnitsRepo) {
        this.appRepo = appRepo;
        this.appUnitsRepo = appUnitsRepo;
    }
    @Override
    public String getDocumentTitle() {
        return "app";
    }

    @Override
    @Transactional
    public void addUnit(Long documentId, Long unitId) {
        System.out.println("------------------> add unit to app units with app id and unit id");
    }
}
