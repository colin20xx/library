package net.nco.sklad.repository;

import net.nco.sklad.entity.documents.InvoiceMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceMovementsRepo extends JpaRepository<InvoiceMovement, Long> {

    List<InvoiceMovement> findInvoiceMovementsByInvoiceId(Long invoiceId);

}
