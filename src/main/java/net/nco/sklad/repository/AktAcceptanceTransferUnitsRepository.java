package net.nco.sklad.repository;

import net.nco.sklad.dto.AATUnitDTO;
import net.nco.sklad.entity.documents.AktAcceptanceTransferUnits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AktAcceptanceTransferUnitsRepository extends JpaRepository<AktAcceptanceTransferUnits, Long> {

    List<AktAcceptanceTransferUnits> findByAktAcceptanceTransferIdOrderByIdDesc(Long id);

    @Query(value = """
        select cast(:id as bigint), daatu.id, d.id, d.denomination_name, n.id, n.nomenclature_name, abs(daatu.quantity), daatu.category, c.id_card, c.price, c.card_number
        from sklad.document_akt_acceptance_transfer_units daatu
                    left join sklad.denominations d on d.id = daatu.denomination_id
                    left join sklad.nomenclatures n on daatu.nomenclature_id = n.id
                    left join sklad.aatu_cards ac on daatu.id = ac.aatu_id
                    left join sklad.cards c on ac.card_id = c.id_card
        where akt_acceptance_transfer_id = :id order by d.denomination_name;
""", nativeQuery = true)
    List<AATUnitDTO> findAllByAktAcceptanceTransferId(@Param("id") Long id);

}//end class
