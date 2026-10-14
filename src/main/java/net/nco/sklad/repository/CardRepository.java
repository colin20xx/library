package net.nco.sklad.repository;

import net.nco.sklad.dto.CardDTO;
import net.nco.sklad.dto.CardMovementDTO;
import net.nco.sklad.entity.documents.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {

    @Query(value = "select c.id_card, c.card_number, c.qty_ps, c.qty_bts, c.price, n.id, n.nomenclature_name, c.category  " +
            "from sklad.cards c " +
            "left join sklad.nomenclatures n on n.id = c.nomenclature_id " +
            "left join sklad.denominations d on c.denomination_id = d.id where c.denomination_id=?1 order by c.category, c.price asc", nativeQuery = true)
    List<CardDTO> findCardsByNomenclatureName(int denomination_id);

    Card findTopByOrderByCardNumberDesc();

    List<Card> findAllByOrderByCardDateTimeDesc();

    @Query(value = """
            with my_deps as(
                select concat(dp.short_name,' ',sd.short_name) as full_dep,
                       concat(dp.id_department,'_',coalesce(sd.id_sub_department,0)) as full_dep_ids
                       from sklad.departments dp left join sklad.sub_department sd on dp.id_department = sd.id_department
            )
    select
            inv.id,
            inv.created_at as date_of_record,
            dt.title as title,
            inv.number,
            inv.document_date,
            (select full_dep from my_deps where full_dep_ids = concat(inv.sender_dep_id,'_',coalesce(inv.sender_subdep_id,0))) as sender,
            (select full_dep from my_deps where full_dep_ids = concat(inv.receiver_dep_id,'_',coalesce(inv.receiver_subdep_id,0))) as receiver,
            im.category, im.qty_bts, im.qty_ps, im.quantity, inv.commited_at
    from sklad.invoices inv
    left join sklad.invoice_movements im on inv.id = im.invoice_id
    left join sklad.im_cards ic on im.id = ic.im_id
    left join sklad.cards c on c.id_card = ic.card_id
    left join sklad.documents_type dt on dt.id = inv.doc_type_id
            where im.invoice_id is not null and ic.card_id is not null and c.id_card = ?1
    union all
    select
            daat.id,
            daat.created_at as date_of_record,
            dt.title as title,
            daat.number,
            daat.document_date,
            (select full_dep from my_deps where full_dep_ids = concat(daat.sender_dep_id,'_',coalesce(daat.sender_subdep_id,0))) as sender,
            (select full_dep from my_deps where full_dep_ids = concat(daat.receiver_dep_id,'_',coalesce(daat.receiver_subdep_id,0))) as receiver,
    daatu.category, daatu.qty_bts, daatu.qty_ps, daatu.quantity, daat.commited_at
    from sklad.document_akt_acceptance_transfer daat
    left join sklad.document_akt_acceptance_transfer_units daatu on daat.id = daatu.akt_acceptance_transfer_id
    left join sklad.aatu_cards ic on daatu.id = ic.aatu_id
    left join sklad.cards c on c.id_card = ic.card_id
    left join sklad.documents_type dt on daat.document_type_id = dt.id
            where c.id_card=?1
    union all
            select datc.id,
            datc.created_at as date_of_record,
            dt.title as title,
            datc.number,
            datc.document_date,
                (select full_dep from my_deps where full_dep_ids = concat(datc.sender_dep_id,'_',coalesce(datc.sender_subdep_id,0))) as sender,
                (select full_dep from my_deps where full_dep_ids = concat(datc.receiver_dep_id,'_',coalesce(datc.receiver_subdep_id,0))) as receiver,
                datcu.category, datcu.qty_bts, datcu.qty_ps, datcu.quantity, datc.commited_at
            from sklad.document_akt_tech_condition datc
                     left join sklad.document_akt_tech_condition_units datcu on datc.id = datcu.atc_id
                     left join sklad.atcu_cards ic on datcu.id = ic.atcu_id
                     left join sklad.cards c on c.id_card = ic.card_id
                     left join sklad.documents_type dt on dt.id = datc.doc_type
            where c.id_card=?1 order by date_of_record asc;
    """, nativeQuery = true)
    List<CardMovementDTO> getCardMovements(Long card_id);

//    @Query(value = """
//
//""", nativeQuery = true)
//    List<CardDTO> findAllBySearchParams(@Param("number") Long number);


}//end interface
