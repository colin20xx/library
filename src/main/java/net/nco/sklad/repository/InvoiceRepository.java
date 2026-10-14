package net.nco.sklad.repository;

import net.nco.sklad.dto.InvoiceDTO;
import net.nco.sklad.entity.documents.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    boolean existsByNumber(String invoiceNumber);

    Optional<Invoice> findTopByOrderByIdDesc();

    @Query(value = """
            with my_deps as(
            select concat(dp.short_name,' ',sd.short_name) as full_dep,
                   concat(dp.id_department,'_',coalesce(sd.id_sub_department,0)) as full_dep_ids
            from sklad.departments dp left join sklad.sub_department sd on dp.id_department = sd.id_department
            )
            select inv.id, dt.title, inv.number, inv.responsible_person_sender, inv.responsible_person_receiver,
               inv.document_date, inv.operation_date, inv.expires_at, inv.commited_at,
               (select full_dep from my_deps where full_dep_ids = concat(inv.sender_dep_id,'_',coalesce(inv.sender_subdep_id,0))) as sender,
               (select full_dep from my_deps where full_dep_ids = concat(inv.receiver_dep_id,'_',coalesce(inv.receiver_subdep_id,0))) as receiver,
               inv.comments, inv.doc_type_id, coalesce(pa.first_pdf, false),pa.file_name, pa.file_path, pa.id
            from sklad.invoices inv
                left join sklad.documents_type dt on dt.id = inv.doc_type_id
                left join sklad.pdf_app pa on inv.doc_type_id = pa.document_type_id and inv.id = pa.document_id
               where inv.id=?1;
            """, nativeQuery = true)
    InvoiceDTO findInvoiceDTOById(Long id);

    @Query(value = """
            select
            i.id,
            '' as title,
            i.number,
            concat(i.sender_dep_id,'_',i.sender_subdep_id) as sender_ids,
            concat(i.receiver_dep_id,'_',i.receiver_subdep_id) as receiver_persone,
            i.document_date,
            i.operation_date,
            i.expires_at,
            i.commited_at,
            (select dn.full_dep from sklad.department_names dn where dn.full_dep_ids = concat(i.sender_dep_id,'_',i.sender_subdep_id) ) as sender,
            (select dn.full_dep from sklad.department_names dn where dn.full_dep_ids = concat(i.receiver_dep_id,'_',i.receiver_subdep_id) ) as receiver,
            i.comments,
            i.doc_type_id,
            coalesce(pa.first_pdf, false),
            pa.file_name,
            pa.file_path,
            pa.id
        from sklad.invoices i
                 left join sklad.pdf_app pa on i.doc_type_id = pa.document_type_id and i.id = pa.document_id
        where
            (:number is NULL or i.number ilike concat('%', :number, '%'))
            and (nullif(:sender, '') is null or exists( select 1 from sklad.department_names dn where dn.full_dep ilike concat('%', :sender, '%')))
            and (nullif(:receiver, '') is null or exists( select 1 from sklad.department_names dn where dn.full_dep ilike concat('%', :receiver, '%')))
        order by i.number desc;
        """, nativeQuery = true)
    List<InvoiceDTO> findAllInvoiceDTO(String number, String sender, String receiver);
}
