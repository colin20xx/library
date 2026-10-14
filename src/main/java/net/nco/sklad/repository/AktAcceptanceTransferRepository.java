package net.nco.sklad.repository;

import net.nco.sklad.dto.AktAcceptTransferDTO;
import net.nco.sklad.entity.documents.AktAcceptanceTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AktAcceptanceTransferRepository extends JpaRepository<AktAcceptanceTransfer, Long> {

    @Query(value = "select daat.id, daat.number, daat.document_date, concat(daat.sender_dep_id,'_',daat.sender_subdep_id), concat(daat.receiver_dep_id,'_',daat.receiver_subdep_id)," +
            "pa.id, pa.file_name, coalesce(pa.first_pdf, false) , daat.comments, " +
            "(select dn.full_dep from sklad.department_names dn where full_dep_ids = concat(daat.sender_dep_id,'_',daat.sender_subdep_id)) as sender, "+
            "(select dn.full_dep from sklad.department_names dn where full_dep_ids = concat(daat.receiver_dep_id,'_',daat.receiver_subdep_id)) as receiver, "+
            "daat.commited_at " +
            "from sklad.document_akt_acceptance_transfer daat " +
            "left join sklad.pdf_app pa on daat.doc_type_id = pa.document_type_id and daat.id = pa.document_id and pa.first_pdf = true;", nativeQuery = true)
    List<AktAcceptTransferDTO> findAllforList();

    @Query(value = """
        select
            daat.id,
            daat.number,
            daat.document_date,
            concat(daat.sender_dep_id,'_',daat.sender_subdep_id),
            concat(daat.receiver_dep_id,'_',daat.receiver_subdep_id),
            pa.id, pa.file_name, coalesce(pa.first_pdf, false) , daat.comments,
            (select dn.full_dep from sklad.department_names dn where full_dep_ids = concat(daat.sender_dep_id,'_',daat.sender_subdep_id)) as sender,
            (select dn.full_dep from sklad.department_names dn where full_dep_ids = concat(daat.receiver_dep_id,'_',daat.receiver_subdep_id)) as receiver,
            daat.commited_at
        from sklad.document_akt_acceptance_transfer daat
        left join sklad.pdf_app pa on daat.doc_type_id = pa.document_type_id
                                          and daat.id = pa.document_id
                                          and pa.first_pdf = true
        where (:number is null or cast(daat.number as TEXT) ilike concat('%', :number, '%'))
        ;
""", nativeQuery = true)
    List<AktAcceptTransferDTO> findAllBySearchParams(@Param("number") String number, @Param("sender") String sender, @Param("receiver") String receiver);



}
