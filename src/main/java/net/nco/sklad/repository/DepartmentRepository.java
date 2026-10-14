package net.nco.sklad.repository;

import net.nco.sklad.dto.DepsWithSubDepsDTO;
import net.nco.sklad.entity.Department;
import net.nco.sklad.entity.SubDepartment;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findByOrderBySortingNumberAsc();
    //SELECT * FROM sklad.departments WHERE active_dep=true ORDER BY id_department ASC
    //List<MyEntity> findByColumnTrue(Sort sort);
    List<Department> findByActiveDepTrue(Sort sort);

    Department findByShortName(String shortName);

    @Query(value = "select d.id_department, d.short_name," +
            "       case when sd.id_sub_department is not null then sd.id_sub_department else 0 end as id_sub_department," +
            "       sd.short_name," +
            "      case when sd.id_sub_department is not null then sd.responsible_person else d.responsable_persone end as responsable_persone " +
            "from sklad.departments d left join sklad.sub_department sd on d.id_department = sd.id_department order by sd.sorting_number, d.sorting_number;", nativeQuery = true)
    List<DepsWithSubDepsDTO> findAllDepartmentsWithSubDepartments();

    @Query(value = "select d.id_department, d.short_name, sd.id_sub_department, sd.short_name, sd.responsible_person from sklad.departments d " +
            "left join sklad.sub_department sd on d.id_department = sd.id_department where d.id_department = ?1 and sd.id_sub_department = ?2;", nativeQuery = true)
    DepsWithSubDepsDTO findDepartmentWithSubDepartmentById(int idDep, int idSubDep);


}
