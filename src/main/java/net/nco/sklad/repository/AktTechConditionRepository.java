package net.nco.sklad.repository;

import net.nco.sklad.dto.AktTechConditionDTO;
import net.nco.sklad.entity.documents.AktTechCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface AktTechConditionRepository extends JpaRepository<AktTechCondition, Long> {
    boolean existsByNumber(String number);

    @Query(value = """
                select atc1_0.id,
                atc1_0.number,
                s.full_dep,
                r.full_dep,
                responsible_person_sender,
                responsible_person_receiver,
                atc1_0.document_date,
                atc1_0.operation_date,
                atc1_0.updated_at,
                atc1_0.commited_at,
                atc1_0.comments 
                from sklad.document_akt_tech_condition atc1_0 
                left join sklad.department_names s ON s.id_department = atc1_0.sender_dep_id AND s.id_sub_department = coalesce(atc1_0.sender_subdep_id, 0) 
                LEFT JOIN sklad.department_names r ON r.id_department = atc1_0.receiver_dep_id AND r.id_sub_department = coalesce(atc1_0.receiver_subdep_id, 0) 
                order by atc1_0.document_date;
            """,
            countQuery = """
                            select count(*) from sklad.document_akt_tech_condition;
                        """,
            nativeQuery = true)
    Page<AktTechConditionDTO> findAllWithTitles(Pageable pageable);

}
