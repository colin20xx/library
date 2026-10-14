package net.nco.sklad.repository;

import net.nco.sklad.DenominationInfo;
import net.nco.sklad.dto.DenominationDTO;
import net.nco.sklad.dto.DenominationRecordDTO;
import net.nco.sklad.entity.Denomination;
import net.nco.sklad.entity.Nomenclature;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DenominationRepository extends JpaRepository<Denomination, Integer> {

    @Query(value = "select dn.id, dn.denomination_name, om.object_name, dn.object_military_id from sklad.denominations dn left join sklad.objects_military om on om.id = dn.object_military_id", nativeQuery = true)
    List<Object> findDenominationsNative(Sort sort);

    List<Denomination> findAllByOrderByDenominationNameAsc();

    @Query(value = "select d.denomination_name from sklad.denominations d where d.denomination_name ilike CONCAT('%', 'авт','%') group by d.denomination_name order by d.denomination_name", nativeQuery = true)
    List<Denomination> findAllByDenominationNameGroupedOrdered(String denominationName);

    @Query(value = "select d.id, d.denomination_name, om.id, om.object_name from sklad.denominations d left join sklad.objects_military om on om.id = d.object_military_id where d.denomination_name ilike CONCAT('%', ?1,'%') order by d.denomination_name", nativeQuery = true)
    List<DenominationDTO> findDenominationsByNameNative(String denominationName);

    @EntityGraph(attributePaths = "parentObjMil")
    List<Denomination> findByDenominationNameContainingIgnoreCase(String denominationName);

    @Query(value = "select d.id as denomination_id, d.denomination_name, count(n.id) as nomenclatures, om.id, om.object_name\n" +
            "from sklad.denominations d\n" +
            "    left join sklad.objects_military om on om.id = d.object_military_id\n" +
            "    left join sklad.nomenclatures n on d.id = n.denomination_id\n" +
            "where d.denomination_name ilike CONCAT('%', ?1,'%')\n" +
            "group by d.id, d.denomination_name, om.id, om.object_name\n" +
            "order by d.denomination_name, om.object_name;", nativeQuery = true)
    List<DenominationInfo> getDenominationsWithOMandNomenclaturesCount(String text);

}
