package net.nco.sklad.repository;

import net.nco.sklad.dto.UnitDTO;
import net.nco.sklad.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Query(value = """
       select u.id,
              u.serial_number,
              u.category,
              u.unit_year,
              u.denomination_id ,
              n.id,
              d.denomination_name ,
              n.nomenclature_name,
              u."comment",
              u.price,
              (select coalesce(full_dep,'-') from sklad.department_names where full_dep_ids = concat(u.current_department_id,'_',u.current_subdep_id)) as full_dep,
              coalesce(u.current_department_id,0) as current_department_id,
              u.current_subdep_id
        from sklad.units u
                left join sklad.denominations d on u.denomination_id = d.id
                left join sklad.nomenclatures n on u.nomenclature_id = n.id;
""" , nativeQuery = true)
    List<UnitDTO> findAllDTO();

    List<Unit> findAllBySerialNumberContainingIgnoreCase(String serialNumber);
}
