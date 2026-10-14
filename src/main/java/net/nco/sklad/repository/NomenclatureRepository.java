package net.nco.sklad.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import net.nco.sklad.dto.NomenclatureDTO;
import net.nco.sklad.entity.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NomenclatureRepository extends JpaRepository<Nomenclature, Long> {

    @Query(value="select * from sklad.nomenclatures n " +
            "inner join sklad.denominations d on d.id = n.denomination_id " +
            "inner join sklad.objects_military om on om.id = d.object_military_id order by d.denomination_name;", nativeQuery = true)
    List<Object> findAllNomenclatures();

    @Query(value = "select * from sklad.denominations d " +
            "left outer join sklad.nomenclatures n on d.id = n.denomination_id " +
            "left join sklad.objects_military om on d.object_military_id = om.id " +
            "where d.denomination_name ilike  concat('%', :denominationTitle, '%') order by d.denomination_name;", nativeQuery = true)
    List<Object> findAllNomenclaturesByDenomination(String denominationTitle);

    @Query(value = "select n.id, n.nomenclature_name, n.denomination_id from sklad.nomenclatures n where denomination_id = :denominationId", nativeQuery = true)
    List<NomenclatureDTO> findAllNomenclaturesByDenominationId(Integer denominationId);

}
