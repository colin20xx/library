package net.nco.sklad.repository;

import net.nco.sklad.entity.Documents;
import net.nco.sklad.entity.documents.DocumentsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentsRepository extends JpaRepository<Documents, Integer> {

    Documents findByNumber(String number);

//    @Query(value = "select count(*) from sklad.documents where doc_number= ?1 and id_document=?2", nativeQuery = true)
//    long countNativeByNumberAndCond(String doc_number, int id_document);

    @Query(value = "select * from sklad.?1 order by ?1.id desc fetch first 1 rows only;", nativeQuery = true)
    String getLastDocNumber();
}
